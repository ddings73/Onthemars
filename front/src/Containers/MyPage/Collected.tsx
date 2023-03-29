import React from 'react';
import styles from './CardList.module.scss';
import Card from 'component/nftCard/card';
import { Link } from 'react-router-dom';
import cardImg from 'assets/cucumber_card.png';

function Collected() {
  const collectedList = [1, 2, 3, 4, 5];
  return (
    <div className={styles.container}>
      {collectedList.map((item) => (
        <div key={item} className={styles.cardList}>
          <Link to={`${item}`}>
            <Card size="bigg" img_address={cardImg}></Card>
          </Link>
        </div>
      ))}
    </div>
  );
}

export default Collected;
