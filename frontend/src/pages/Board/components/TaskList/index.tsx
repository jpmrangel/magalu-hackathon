import { TaskCard } from '../TaskCard';

import styles from './TaskList.module.css';
import { BsThreeDots, BsFillPlusCircleFill, BsFillPencilFill } from 'react-icons/bs';
import { AiFillDelete } from 'react-icons/ai';

import { useState, useRef, useEffect } from 'react';

import type { List, Task } from '../../../../contexts/TasksContext';

interface TaskListProps {
    list: List;
    onSelectTask: (task: Task) => void;
    onNewTaskRequest: (listId: string) => void;
    onDeleteRequest: (list: List) => void;
    onRenameList: (listId: string, newTitle: string) => void;
    onToggleFinishTask: (task: Task) => void;
};

export function TaskList({ list, onSelectTask, onNewTaskRequest, onDeleteRequest, onRenameList, onToggleFinishTask }: TaskListProps) {
    const [isOptionsOpen, setIsOptionsOpen] = useState(false); 
    const [isEditingTitle, setIsEditingTitle] = useState(false);
    const [currentTitle, setCurrentTitle] = useState(list.title);

    const optionsMenuRef = useRef<HTMLDivElement>(null); 
    const titleInputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        if (isEditingTitle) {
            titleInputRef.current?.focus();
        }
    }, [isEditingTitle]);

    useEffect(() => {
        function handleClickOutside(event: MouseEvent) {
            if (optionsMenuRef.current && !optionsMenuRef.current.contains(event.target as Node)) {
                setIsOptionsOpen(false);
            }
        }
        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [optionsMenuRef]);

    const handleStartEditing = () => {
        setIsEditingTitle(true);
        setIsOptionsOpen(false);
    };

    const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setCurrentTitle(event.target.value);
    };

    const handleFinishEditing = () => {
        if (currentTitle.trim() !== "" && currentTitle !== list.title) {
            onRenameList(list.id, currentTitle);
        }
        setIsEditingTitle(false);
    };

    const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            handleFinishEditing();
        }
        if (event.key === 'Escape') {
            setCurrentTitle(list.title);
            setIsEditingTitle(false);
        }
    };

    return (
        <section className={styles.taskList}>
            <header className={styles.header}>
                {isEditingTitle ? (
                    <input
                        ref={titleInputRef}
                        type="text"
                        value={currentTitle}
                        onChange={handleTitleChange}
                        onBlur={handleFinishEditing} 
                        onKeyDown={handleKeyDown} 
                        className={styles.titleInput}
                    />
                ) : (
                    <h6 onClick={handleStartEditing}>{currentTitle}</h6>
                )}
                {!isEditingTitle && (
                    <div className={styles.optionsContainer} ref={optionsMenuRef}>
                        <button 
                            className={styles.optionsButton} 
                            onClick={() => setIsOptionsOpen(!isOptionsOpen)}>
                            <BsThreeDots size="1.5rem"/>
                        </button>

                        {isOptionsOpen && (
                            <div className={styles.optionsMenu}>
                                <button className={styles.optionsMenuItem} onClick={handleStartEditing}>
                                    <BsFillPencilFill size="1rem"/>
                                    <span>Renomear</span>
                                </button>
                                <button 
                                    className={`${styles.optionsMenuItem} ${styles.deleteOption}`}
                                    onClick={() => onDeleteRequest(list)}
                                >
                                    <AiFillDelete size="1rem"/>
                                    <span>Deletar</span>
                                </button>
                            </div>
                        )}
                    </div>
                )}
            </header>

            <div className={styles.cardsContainer}>
                {list.tasks.map(task => (
                    <TaskCard 
                        key={task.id}
                        task={task} 
                        onSelect={onSelectTask}
                        onToggleFinish={() => onToggleFinishTask(task)}
                    />
                ))}
            </div>
            
            <button className={styles.newTaskButton} onClick={() => onNewTaskRequest(list.id)}>
                <span>
                    <span className={styles.iconWrapper}><BsFillPlusCircleFill size="1.25rem" /></span>
                    <span>Nova tarefa</span>
                </span>
            </button>
            
        </section>
    )
}