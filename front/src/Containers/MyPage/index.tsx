import React from 'react';
import styles from './index.module.scss';
import Info from './Info';
import Tab from './Tab';

function MyPage() {
  return (
    <>
      <div className={styles.container}>
        <div className={styles.info}>
          <Info />
        </div>
        <div className={styles.tab}>
          <Tab />
        </div>
      </div>
    </>
  );
}

export default MyPage;
