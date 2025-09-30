import styles from './TaskDetailDrawer.module.css';
import { BsCheck2, BsArrowBarRight, BsFillCalendarWeekFill, BsPaperclip, BsFillPlusCircleFill, BsX, BsFillXCircleFill } from 'react-icons/bs';
import { AiFillDelete, AiOutlinePaperClip } from 'react-icons/ai'

import { useState, useEffect, useRef } from 'react';

import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';

import type { Task, Attachment } from '../../../../contexts/TasksContext';

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

const notificationOptions: { [key: string]: string } = {
  'NONE': 'Não notificar',
  '1': '1 minuto antes',
  '5': '5 minutos antes',
  '15': '15 minutos antes',
  '30': '30 minutos antes',
  '60': '1 hora antes'
};

export function TaskDetailDrawer({ task, onClose, onDeleteTaskRequest, onSave, onToggleFinishTask, onUpdateTask }: TaskDetailDrawerProps) {
  const [isEditingTitle, setIsEditingTitle] = useState(false);
  const [isEditingDate, setIsEditingDate] = useState(false);
  const [currentTitle, setCurrentTitle] = useState(''); 
  const titleInputRef = useRef<HTMLInputElement>(null);
  const dateInputRef = useRef<HTMLInputElement>(null);
  const fileInputRef = useRef<HTMLInputElement>(null);

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

  const handleAddAttachmentClick = () => {
    fileInputRef.current?.click();
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
        const file = e.target.files[0];
        onUpdateTask({ attachment: file });
    }
};

const handleDeleteAttachment = () => {
    onUpdateTask({ attachment: undefined });
};

  const handleDownloadAttachment = (attachment: Attachment | File) => {
    const link = document.createElement('a');
    link.download = attachment.name;

    if (attachment instanceof File) {
      link.href = URL.createObjectURL(attachment);
    } else {
      link.href = attachment.url;
      link.target = '_blank';
      link.rel = 'noopener noreferrer';
    }

    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
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

              <div className={styles.priorityField}>
                <label htmlFor="notificationTime">Notificação</label>
                <select
                  id="notificationTime"
                  name="notificationTime"
                  value={task.notificationTime}
                  onChange={handleChange}
                >
                  {Object.entries(notificationOptions).map(([value, label]) => (
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
                  
            <div className={styles.drawerArchives}>
              <p>Arquivos</p>

              <div className={styles.attachmentsContainer}>
                {task.attachment && (
                  <div 
                    key={task.attachment.name}
                    className={styles.attachmentPill}
                    onDoubleClick={() => handleDownloadAttachment(task.attachment!)}
                    title="Clique duplo para baixar"
                  >
                    <AiOutlinePaperClip />
                    <span className={styles.attachmentName}>
                      {task.attachment.name.length > 20 ? `${task.attachment.name.substring(0, 18)}...` : task.attachment.name}
                    </span>
                    <button 
                      className={styles.deleteAttachmentButton} 
                      onClick={handleDeleteAttachment}
                    >
                      <BsFillXCircleFill size={16}/>
                    </button>
                  </div>
                )}

                <input
                  type="file"
                  multiple
                  ref={fileInputRef}
                  onChange={handleFileChange}
                  style={{ display: 'none' }}
                />
                <button 
                  className={styles.addAttachmentButton} 
                  onClick={handleAddAttachmentClick}
                >
                  <BsFillPlusCircleFill size={20} />
                  Adicionar arquivo
                </button>
              </div>

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