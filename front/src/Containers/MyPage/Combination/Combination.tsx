import React, { useState } from 'react';
import styles from './Combination.module.scss';
import SortBy from './SortBy';
import again from 'assets/combi/again.png';
import plus from 'assets/combi/plus.png';
import arrow from 'assets/combi/arrow.png';
import tier1card1 from 'assets/combi/tier1_wheat_1.png';
import tier1card2 from 'assets/combi/tier1_wheat_2.png';
import cardImg from 'assets/combi/tier1_wheat_2.png';
import tier2card from 'assets/combi/tier2_wheat.png';
import { ButtonDiv } from 'component/button/Button';
import Card from 'component/nftCard/card';

function Combination() {
  const handleToRoll = () => {};
  const cardList = [1, 2, 3, 4, 5, 6];
  const [select, setSelect] = useState(false);
  const SelectCard = () => {
    setSelect(!select);
  };
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
      <div className={styles.combi}>
        <div className={styles.card1}>
          {select ? (
            <>
              <img className={styles.card} src={cardImg} alt="" />
            </>
          ) : (
            <>
              <img className={styles.card} src={cardImg} style={{ visibility: 'hidden' }} alt="" />
            </>
          )}
        </div>
        <img className={styles.plus} src={plus} alt="" />
        <div className={styles.card2}>
          {select ? (
            <>
              <img className={styles.card} src={cardImg} alt="" />
            </>
          ) : (
            <>
              <img className={styles.card} src={cardImg} style={{ visibility: 'hidden' }} alt="" />
            </>
          )}
        </div>
        <img className={styles.arrow} src={arrow} alt="" />
        <div className={styles.resultCard}>
          <img className={styles.card} src={tier2card} alt="" />
        </div>
      </div>
      <div className={styles.cardsList}>
        {cardList.map((item) => (
          <div key={item} className={styles.cardList} onClick={SelectCard}>
            <Card size="bigg" img_address={cardImg}></Card>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Combination;
