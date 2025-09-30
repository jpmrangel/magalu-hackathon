import styles from './Header.module.css';
import logo from '../../assets/logo.png';

import { BsBellFill } from 'react-icons/bs';
import { FaUserCircle } from 'react-icons/fa';

export function Header() {
    const hasNotification = true;
    const userName = "Vitor";

    const notificationButtonClasses = `
        ${styles.headerButton}
        ${hasNotification ? styles.hasNotification : ''}
    `;

    return (
        <header className={styles.header}>
            <div className={styles.logoContainer}>
                <img src={logo} alt="Luiza Tasks" />

                <div className={styles.titleContainer}>
                    <p style={{color: '#0292FB', fontWeight: 'bold'}}>Luiza</p>
                    <p style={{color: '#8B8F92'}}>Tasks</p>
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