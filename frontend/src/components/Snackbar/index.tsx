import styles from './Snackbar.module.css';
import { BsCheckCircleFill, BsXLg } from 'react-icons/bs';

interface SnackbarProps {
  message: string;
  onClose: () => void; 
};

export function Snackbar({ message, onClose }: SnackbarProps) {
    return (
        <div className={styles.snackbar}>
          <div>
            <main className={styles.content}>
              <BsCheckCircleFill size="1.25rem" className={styles.icon} />
              <span>{message}</span>
            </main>
          
            <button onClick={onClose} className={styles.closeButton}>
                <BsXLg size="1rem" />
            </button>
          </div>
        </div>
    );
}