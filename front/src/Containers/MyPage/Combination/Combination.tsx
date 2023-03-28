import React from 'react';
import styles from './Combination.module.scss';
import SortBy from './SortBy';
import again from 'assets/again.png';
import { ButtonDiv } from 'component/button/Button';

function Combination() {
  const handleToRoll = () => {};
  return (
    <div className={styles.container}>
      <div className={styles.top}>
        <div className={styles.text}>* 같은 작물의 Tier 1 카드 두 장을 선택해주세요.</div>
        <div className={styles.again}>
          <img className={styles.icon} src={again} alt="" />
          다른 카드도 합성하기
        </div>
      </div>
      <div className={styles.middletop}>
        <div className={styles.select}>
          <SortBy />
        </div>
        <div className={styles.rollBtn} onClick={handleToRoll}>
          <ButtonDiv text={"Let's Roll!"} />
        </div>
      </div>
    </div>
  );
}

export default Combination;
