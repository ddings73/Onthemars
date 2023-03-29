import React from 'react';
import styles from './CardList.module.scss';
import Card from 'component/nftCard/card';
import { Link } from 'react-router-dom';
import cardImg from 'assets/pineapple_card.png';

function Minted() {
  const mintedList = [1, 2, 3];
  return (
    <div className={styles.container}>
      {mintedList.map((item) => (
        <div key={item} className={styles.cardList}>
          <Link to={`${item}`}>
            <Card size="bigg" img_address={cardImg}></Card>
          </Link>
        </div>
      ))}
    </div>
  );
}

export default Minted;
