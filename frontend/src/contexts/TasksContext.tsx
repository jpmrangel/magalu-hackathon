import { createContext, useState, useEffect, useContext, type ReactNode } from 'react';
import { api } from '../../src/lib/axios';

export interface Attachment {
    id: string;
    name: string;
    url: string;
}

export interface Task {
    id: string;
    title: string;
    description: string;
    priority: 'LOW' | 'MEDIUM' | 'HIGH' | 'VERY_HIGH';
    date: string | null;
    finishingDate: string | null;
    listId: string;
    isNew?: boolean;
    notificationTime?: 'NONE' | '1' | '5' | '15' | '30' | '60';
    attachment?: Attachment | File
};

export interface List {
    id: string;
    title: string;
    tasks: Task[];
}

interface TasksContextType {
    lists: List[];
    createList: (name: string) => Promise<void>;
    deleteList: (listId: string) => Promise<void>;
    updateListTitle: (listId: string, newTitle: string) => Promise<void>;
    saveTask: (taskData: Task) => Promise<void>;
    deleteTask: (taskId: string) => Promise<void>;
    toggleFinishTask: (taskToToggle: Task) => Promise<void>;
}

export const TasksContext = createContext({} as TasksContextType);

interface TasksProviderProps {
    children: ReactNode;
}

export function TasksProvider({ children }: TasksProviderProps) {
    const [lists, setLists] = useState<List[]>([]);

    async function fetchLists() {
        try {
            const response = await api.get('/lists');
            const listsFromApi = response.data;

            const listsWithTasks = await Promise.all(
                listsFromApi.map(async (list: any) => {
                    const tasksResponse = await api.get(`/tasks/list/${list.id}`);
                    const formattedTasks = (tasksResponse.data || []).map((task: any) => ({
                        ...task,
                        title: task.name,
                        date: task.expectedFinishingDate ? task.expectedFinishingDate.split('T')[0] : null,
                        finishingDate: task.finishingDate,
                        attachments: task.attachment || []
                    }));
                    return { ...list, title: list.name, tasks: formattedTasks };
                })
            );
            setLists(listsWithTasks);
        } catch (error) {
            console.error("Falha ao buscar dados da API", error);
        }
    }

    useEffect(() => {
        fetchLists();
    }, []);

    const createList = async (name: string) => {
        try {
            const response = await api.post('/lists', { name });
            const newListApi = response.data;
            const newList = { ...newListApi, title: newListApi.name, tasks: [] };
            setLists(prevLists => [...prevLists, newList]);
        } catch (error) {
            console.error("Falha ao criar lista", error);
        }
    };

    const deleteList = async (listId: string) => {
        try {
            await api.delete(`/lists/${listId}`);
            setLists(prevLists => prevLists.filter(list => list.id !== listId));
        } catch (error) {
            console.error("Falha ao deletar lista", error);
        }
    };

    const updateListTitle = async (listId: string, newTitle: string) => {
        try {
            await api.put(`/lists/${listId}`, { name: newTitle });
            setLists(prevLists => prevLists.map(list => 
                list.id === listId ? { ...list, title: newTitle, name: newTitle } : list
            ));
        } catch (error) {
            console.error("Falha ao renomear lista", error);
        }
    };

    const saveTask = async (taskData: Task) => {
        try {
            const dto = {
                name: taskData.title,
                description: taskData.description || '',
                priority: taskData.priority,
                listId: taskData.listId,
                notificationTime: taskData.notificationTime,
                expectedFinishingDate: taskData.date ? new Date(taskData.date).toISOString() : null,
                finishingDate: taskData.finishingDate,
            };
            
            let savedTask;

            if (taskData.isNew) {
                const response = await api.post('/tasks', dto);
                savedTask = response.data;
            } else {
                const response = await api.put(`/tasks/${taskData.id}`, dto);
                savedTask = response.data;
            }
            
            const newFile = taskData.attachment instanceof File ? taskData.attachment : null;
        
            if (newFile) {
                const fileFormData = new FormData();
                fileFormData.append('attachment', newFile);

                await api.post(`/api/files/upload`, fileFormData, {
                    params: {
                        taskId: savedTask.id
                    }
                });
            }
            await fetchLists();
        } catch (error) {
            console.error("Falha ao salvar tarefa", error);
        }
    };

    const deleteTask = async (taskId: string) => {
        try {
            await api.delete(`/tasks/${taskId}`);
            await fetchLists();
        } catch (error) {
            console.error("Falha ao deletar tarefa", error);
        }
    };
    
    const toggleFinishTask = async (taskToToggle: Task) => {
        try {
            const newFinishingDate = taskToToggle.finishingDate ? null : new Date().toISOString();
            
            const dto = {
                finishingDate: newFinishingDate
            };
            
            await api.patch(`/tasks/${taskToToggle.id}`, dto);
            await fetchLists();
        } catch (error) {
            console.error("Falha ao finalizar tarefa", error);
        }
    };

    return (
        <TasksContext.Provider value={{
            lists,
            createList,
            deleteList,
            updateListTitle,
            saveTask,
            deleteTask,
            toggleFinishTask
        }}>
            {children}
        </TasksContext.Provider>
    );
}

export const useTasks = () => {
    return useContext(TasksContext);
}