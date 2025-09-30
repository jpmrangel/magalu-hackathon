import styles from './TaskCard.module.css';
import { BsCalendar3, BsCheck2 } from 'react-icons/bs';

import { format, isPast, endOfDay, isAfter } from 'date-fns';
import { ptBR } from 'date-fns/locale';

import type { Task } from '../../../../contexts/TasksContext';

interface TaskCardProps {
    task: Task;
    onSelect: (task: Task) => void;
    onToggleFinish: (task: Task) => void;
};

const priorityLabels: { [key: string]: string } = {
    'LOW': 'Baixa Prioridade',
    'MEDIUM': 'Média Prioridade',
    'HIGH': 'Alta Prioridade',
    'VERY_HIGH': 'Altíssima Prioridade'
};

export function TaskCard( { task, onSelect, onToggleFinish }: TaskCardProps) {
    const getPriorityClass = (priority: Task['priority']) => {
        switch (priority) {
            case 'LOW': return styles.low;
            case 'MEDIUM': return styles.medium;
            case 'HIGH': return styles.high;
            case 'VERY_HIGH': return styles.superHigh;
            default: return '';
        }
    };

    const formattedDate = task.date ? format(new Date(task.date), "dd MMM, yyyy", { locale: ptBR }).toUpperCase() : '';
    const isFinished = !!task.finishingDate;
    const isOverdue = !isFinished && task.date && isPast(endOfDay(new Date(task.date)));
    const wasFinishedLate = isFinished && task.date && task.finishingDate && isAfter(new Date(task.finishingDate), endOfDay(new Date(task.date)));

    const finalizeButtonClasses = `
        ${styles.finalizeButton}
        ${isFinished ? styles.finished : ''}
    `;

    const footerClasses = `
        ${styles.cardFooter}
        ${isOverdue && !isFinished ? styles.overdue : ''}
        ${wasFinishedLate ? styles.finishedLate : ''}
    `;

    const taskCardClasses = `
        ${styles.taskCard}
        ${isFinished ? styles.finishedCard : ''}
        ${isOverdue ? styles.overdueCard : ''}
    `;

    const handleFinishTask = (event: React.MouseEvent) => {
        event.stopPropagation();
        onToggleFinish(task);
    };

    return (
        <div className={taskCardClasses} onClick={() => onSelect(task)}>
            <header className={styles.cardHeader}>
                <span className={`${styles.priorityTag} ${getPriorityClass(task.priority)}`}>
                    <small>
                        {priorityLabels[task.priority]}
                    </small>
                </span>

                <button className={finalizeButtonClasses} onClick={handleFinishTask}>
                    <span className={styles.checkIconContainer}>
                        <BsCheck2 size="1rem" />
                    </span>
                    <p>{isFinished ? 'Finalizado' : 'Finalizar'}</p>
                </button>
            </header>

            <section className={styles.cardBody}>
                <h6>{task.title}</h6>
                <p className={styles.taskDesc}>{task.description}</p>
            </section>

            <footer className={footerClasses}>
                <BsCalendar3 size="1rem" />
                <time>{formattedDate}</time>
            </footer>
        </div>
    )
}