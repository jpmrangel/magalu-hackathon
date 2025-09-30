import styles from './Header.module.css';
import logo from '../../assets/logo.svg';

import { BsBellFill } from 'react-icons/bs';
import { FaUserCircle } from 'react-icons/fa';

export function Header() {
    const hasNotification = true;
    const userName = "José";

    const notificationButtonClasses = `
        ${styles.headerButton}
        ${hasNotification ? styles.hasNotification : ''}
    `;

    return (
        <header className={styles.header}>
            <div className={styles.logoContainer}>
                <img src={logo} alt="Peugeot Tasks" />

                <div className={styles.titleContainer}>
                    <p>Peugeot</p>
                    <p>Tasks</p>
                </div>
            </div>
            
            <div className={styles.iconsContainer}>
                <button className={notificationButtonClasses}>
                    <BsBellFill size={20} />
                </button>

                <button className={styles.profileButton}>
                    <FaUserCircle size={24}/>
                    <span>{userName}</span>
                </button>
            </div>
        </header>
    );
}