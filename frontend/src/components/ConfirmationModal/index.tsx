import { BsFillXCircleFill } from 'react-icons/bs';
import { AiFillDelete } from 'react-icons/ai';
import styles from './ConfirmationModal.module.css';

interface ConfirmationModalProps {
  isOpen: boolean; 
  title: string;
  message: string; 
  onConfirm: () => void;
  onCancel: () => void; 
};

export function ConfirmationModal({ isOpen, title, message, onConfirm, onCancel }: ConfirmationModalProps) {
    if (!isOpen) {
        return null;
    }

    return (
        <div className={styles.overlay}>
            <div className={styles.modal}>
              <div className={styles.header}>
                <button onClick={onCancel} className={styles.cancelButton}>
                  <BsFillXCircleFill size="1rem" />
                </button>
              </div>

              <div className={styles.body}>
                <h6>{title}</h6>
                <p>{message}</p>
              </div>
              
              <div className={styles.buttonGroup}>
                <button onClick={onConfirm} className={styles.confirmButton}>
                  <AiFillDelete size="1rem"/>
                  <span>Deletar</span>
                </button>
              </div>
            </div>
        </div>
    );
}