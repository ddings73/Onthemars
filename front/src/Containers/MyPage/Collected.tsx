import React from 'react';
import styles from './CardList.module.scss';
import Card from 'component/nftCard/card';
import cardImg from 'assets/cucumber_card.png';

function Collected() {
  const collectedList = [1, 2, 3, 4];
  return (
    <div className={styles.container}>
      {collectedList.map((item) => (
        <div className={styles.cardList}>
          <Card size="big" img_address={cardImg}></Card>
        </div>
      ))}
    </div>
  );
}

export default Collected;
