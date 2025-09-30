import styles from './TaskDetailDrawer.module.css';
import { BsCheck2, BsArrowBarRight, BsFillCalendarWeekFill } from 'react-icons/bs';
import { AiFillDelete } from 'react-icons/ai'

import { useState, useEffect, useRef } from 'react';

import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';

import type { Task } from '../../../../contexts/TasksContext';

interface TaskDetailDrawerProps {
  task: Task | null;
  onClose: () => void;
  onDeleteTaskRequest: (task: Task) => void;
  onSave: (task: Task) => void;
  onToggleFinishTask: (task: Task) => void;
  onUpdateTask: (updateFields: Partial<Task>) => void;
};

const priorityOptions: { [key: string]: string } = {
    'LOW': 'Baixa',
    'MEDIUM': 'Média',
    'HIGH': 'Alta',
    'VERY_HIGH': 'Altíssima'
};

export function TaskDetailDrawer({ task, onClose, onDeleteTaskRequest, onSave, onToggleFinishTask, onUpdateTask }: TaskDetailDrawerProps) {
  const [isEditingTitle, setIsEditingTitle] = useState(false);
  const [isEditingDate, setIsEditingDate] = useState(false);
  const [currentTitle, setCurrentTitle] = useState(''); 
  const titleInputRef = useRef<HTMLInputElement>(null);
  const dateInputRef = useRef<HTMLInputElement>(null);

  const formattedDate = task ? (task.date && format(new Date(task.date), "dd MMM, yyyy", { locale: ptBR }).toUpperCase()) : '';
  const isFinished = !!task?.finishingDate;
  const overlayClasses = `${styles.overlay} ${task ? styles.isOpen : ''}`;
  const finalizeButtonClasses = `
    ${styles.finalizeButton}
    ${isFinished ? styles.finished : ''}
  `;

  useEffect(() => {
    if (task) {
      setCurrentTitle(task.title);
      if (task?.isNew) {
        setIsEditingTitle(true);
      } else {
        setIsEditingTitle(false);
      }
    }
  }, [task?.id]);

  useEffect(() => {
    if (isEditingTitle) {
      titleInputRef.current?.focus();
    }
  }, [isEditingTitle]);

  useEffect(() => {
    if (isEditingDate) {
      dateInputRef.current?.focus();
    }
  }, [isEditingDate]);
  
  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    if (name !== 'title') {
      onUpdateTask({ [name]: value });
    }
  };
  
  const handleTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentTitle(e.target.value);
  };
  
  const handleFinishEditingTitle = () => {
    onUpdateTask({ title: currentTitle }); 
    setIsEditingTitle(false);
  };

  return (
    <div className={overlayClasses} onClick={onClose}>
      <aside className={styles.drawer} onClick={(e) => e.stopPropagation()}>
        {task && (
        <>
          <button className={styles.closeTaskButton} onClick={onClose}>
            <BsArrowBarRight size={24}/>
          </button>

          <section className={styles.openedTask}>
            <header className={styles.drawerHeader}>
              {!task.isNew && (
                <button 
                  className={finalizeButtonClasses} 
                  onClick={() => onToggleFinishTask(task)}
                >
                  <span className={styles.checkIconContainer}>
                    <BsCheck2 size={16} />
                  </span>
                  {isFinished ? 'Finalizado' : 'Finalizar'}
                </button>
              )}
              {isEditingTitle ? (
                <input
                  ref={titleInputRef}
                  type="text"
                  name="title"
                  className={styles.titleInput}
                  value={currentTitle}
                  onChange={handleTitleChange}
                  onBlur={handleFinishEditingTitle}
                  onKeyDown={(e) => {
                    if (e.key === 'Enter' || e.key === 'Escape') {
                      handleFinishEditingTitle();
                    }
                  }}
                  placeholder="Digite o título da tarefa"
                  required
                />
              ) : (
                <h3 className={styles.titleText} onClick={() => setIsEditingTitle(true)}>
                  {currentTitle}
                </h3>
              )}
            </header>

            <hr />
            
            <div className={styles.drawerBody}>
              <div className={styles.dateField}>
                <label>Data de conclusão</label>
                
                {isEditingDate ? (
                  <input
                    ref={dateInputRef}
                    type="date"
                    name="date"
                    className={styles.dateInput}
                    value={task.date || ''}
                    onChange={handleChange}
                    onBlur={() => setIsEditingDate(false)}
                  />
                ) : (
                  <button
                    type="button"
                    className={styles.dateDisplay}
                    onClick={() => setIsEditingDate(true)}
                  >
                    <BsFillCalendarWeekFill size="1rem" />
                    <span>{formattedDate}</span>
                  </button>
                )}
              </div>
              
              <div className={styles.priorityField}>
                <label htmlFor="priority">Prioridade</label>
                <select
                  id="priority"
                  name="priority"
                  value={task.priority}
                  onChange={handleChange}
                >
                  {Object.entries(priorityOptions).map(([value, label]) => (
                      <option key={value} value={value}>
                          {label}
                      </option>
                  ))}
                </select>
              </div>
            </div>

            <hr />

            <div className={styles.drawerDesc}>
              <p>Descrição</p>
              <textarea 
                id="description"
                name="description"
                value={task.description || ''}
                onChange={handleChange}
                placeholder='Adicione mais detalhes sobre a tarefa aqui...'
              >
              </textarea>
            </div>

            <hr />

            <footer className={styles.drawerFooter}>
              <div className={styles.buttonGroup}>
                <button 
                  type="submit"
                  className={`${styles.footerButton} ${styles.saveButton}`}
                  onClick={() => onSave(task)}
                >
                  {task.isNew ? 'Criar Tarefa' : 'Salvar Alterações'}
                </button>

                {!task.isNew && (
                  <button 
                      type="button" 
                      className={`${styles.footerButton} ${styles.deleteTaskButton}`}
                      onClick={() => onDeleteTaskRequest(task)}
                  >
                      <AiFillDelete size="1rem"/>
                      Deletar
                  </button>
              )}
              </div>
                
            </footer>
          </section>
        </>
      )}
      </aside>
    </div>
  );
}