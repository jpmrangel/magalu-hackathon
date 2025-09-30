import { TaskList } from "./components/TaskList";
import { TaskDetailDrawer } from "./components/TaskDetailDrawer";
import { ConfirmationModal } from "../../components/ConfirmationModal";
import { Snackbar } from "../../components/Snackbar";

import styles from './Board.module.css'
import { BsFillPlusCircleFill } from "react-icons/bs";

import { useState, useRef, useEffect } from "react";

import { useTasks } from '../../contexts/TasksContext';
import type { List, Task } from '../../contexts/TasksContext';

export function Board() {
    const { lists, createList, deleteList, updateListTitle, saveTask, deleteTask, toggleFinishTask } = useTasks();
    
    const [selectedTask, setSelectedTask] = useState<Task | null>(null);
    const [listToDelete, setListToDelete] = useState<List | null>(null);
    const [taskToDelete, setTaskToDelete] = useState<Task | null>(null);
    
    const [isCreatingList, setIsCreatingList] = useState(false);
    const [newListName, setNewListName] = useState("");
    const [snackbar, setSnackbar] = useState({ visible: false, message: '' });
    const newListInputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        if (isCreatingList) {
            newListInputRef.current?.focus();
        }
    }, [isCreatingList]);
    
    useEffect(() => {
        if (snackbar.visible) {
            const timer = setTimeout(() => {
                setSnackbar({ visible: false, message: '' });
            }, 3000);

            return () => clearTimeout(timer);
        }
    }, [snackbar]);

    const handleSelectTask = (task: Task) => {
        setSelectedTask(task);
    };

    const handleOpenNewTaskDrawer = (listId: string) => {
        const newTaskTemplate: Task = {
            id: '',
            title: 'Nova Tarefa',
            description: '',
            priority: 'LOW',
            date: new Date().toISOString().split('T')[0],
            finishingDate: null,
            isNew: true,
            listId: listId,
        };

        setSelectedTask(newTaskTemplate);
    };

    const handleOpenDeleteTaskModal = (task: Task) => {
        setTaskToDelete(task);
        setSelectedTask(null);
    };

    const handleConfirmDeleteTask = async () => {
        if (taskToDelete) {
            await deleteTask(taskToDelete.id);
            setTaskToDelete(null);
            setSnackbar({ visible: true, message: 'Tarefa deletada com sucesso!' });
        }
    };

    const handleCreateList = async () => {
        if (newListName.trim() === "") {
            setIsCreatingList(false);
            return;
        }
        await createList(newListName);
        setNewListName("");
        setIsCreatingList(false);
    };

    const handleConfirmDeleteList = async () => {
        if (listToDelete) {
            await deleteList(listToDelete.id);
            setListToDelete(null);
            setSnackbar({ visible: true, message: 'Lista deletada com sucesso!' });
        }
    };

    const handleSaveTask = async (task: Task) => {
        try {
            await saveTask(task);
            setSelectedTask(null);
        } catch (error) {
            console.error('Erro ao salvar tarefa:', error);
        }
    };

    const handleToggleFinish = async (task: Task) => {
        await toggleFinishTask(task);

        if (selectedTask && selectedTask.id === task.id) {
            setSelectedTask((prev: any) => prev ? ({
                ...prev, 
                finishingDate: task.finishingDate ? null : new Date().toISOString()
            }) : null);
        }
    }

    const handleTaskUpdate = (updatedFields: Partial<Task>) => {
        if (!selectedTask) return;
        setSelectedTask((prevTask: any) => prevTask ? ({
            ...prevTask,
            ...updatedFields
        }) : null);
    };

    return (
        <>
            <main className={styles.home}>
                {lists.map(list => (
                    <TaskList 
                        key={list.id} 
                        list={list}
                        onSelectTask={handleSelectTask}
                        onNewTaskRequest={handleOpenNewTaskDrawer}
                        onDeleteRequest={setListToDelete}
                        onRenameList={updateListTitle}
                        onToggleFinishTask={handleToggleFinish}
                    />

                ))}

                {isCreatingList ? (
                    <div className={styles.newListInputContainer}>
                        <input
                            ref={newListInputRef}
                            type="text"
                            value={newListName}
                            onChange={(e) => setNewListName(e.target.value)}
                            onBlur={handleCreateList}
                            onKeyDown={(e) => e.key === 'Enter' && handleCreateList()}
                        />
                    </div>
                ) : (
                    <button className={styles.newListButton} onClick={() => setIsCreatingList(true)}>
                        <BsFillPlusCircleFill size="1.5rem" />
                        <h6>Nova lista</h6>
                    </button>
                )}     
            </main>

            <TaskDetailDrawer
                task={selectedTask}
                onClose={() => setSelectedTask(null)}
                onDeleteTaskRequest={handleOpenDeleteTaskModal}
                onToggleFinishTask={handleToggleFinish}
                onSave={handleSaveTask}
                onUpdateTask={handleTaskUpdate}
            />

            <ConfirmationModal
                isOpen={!!listToDelete}
                title={`Tem certeza que deseja deletar a lista ${listToDelete?.title}?`}
                message="Essa ação não é reversível."
                onConfirm={handleConfirmDeleteList}
                onCancel={() => setListToDelete(null)}
            />

            <ConfirmationModal
                isOpen={!!taskToDelete}
                title={`Tem certeza que deseja deletar a tarefa ${taskToDelete?.title}?`}
                message="Essa ação não é reversível."
                onConfirm={handleConfirmDeleteTask}
                onCancel={() => setTaskToDelete(null)}
            />

            {snackbar.visible && (
                <Snackbar 
                    message={snackbar.message} 
                    onClose={() => setSnackbar({ visible: false, message:''})}
                />
            )}
        </>
    )
}