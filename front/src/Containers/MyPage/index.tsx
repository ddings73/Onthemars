import React from 'react';
import styles from './index.module.scss';
import Info from './Info';

function MyPage() {
  return (
    <>
      <div className={styles.container}>
        <div className={styles.info}>
          <Info />
        </div>
      </div>
    </>
  );
}

export default MyPage;
