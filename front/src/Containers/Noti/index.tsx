import React from 'react';
import styles from './index.module.scss';
import NotiCard from './NotiCard';

function Notification() {
  const notiList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  return (
    <div className={styles.container}>
      <div className={styles.notiContainer}>
        {notiList.map((item: any, index: number) => (
          <div key={index} className={styles.item}>
            <NotiCard />
          </div>
        ))}
      </div>
    </div>
  );
}

export default Notification;
