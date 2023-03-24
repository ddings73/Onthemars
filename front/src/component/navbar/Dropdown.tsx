import React from 'react';
import styles from './Dropdown.module.scss';

function Dropdown() {
  return (
    <div className={styles.menu}>
      <li className={styles.linkwrapper}>로그인</li>
    </div>
  );
}

export default Dropdown;
