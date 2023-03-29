import React from 'react';
import styles from './CardList.module.scss';
import Card from 'component/nftCard/card';
import { Link } from 'react-router-dom';
import cardImg from 'assets/tomato_card.png';

function Favorited() {
  const favoritedList = [1, 2, 3, 4];
  return (
    <div className={styles.container}>
      {favoritedList.map((item) => (
        <div key={item} className={styles.cardList}>
          <Link to={`${item}`}>
            <Card size="bigg" img_address={cardImg}></Card>
          </Link>
        </div>
      ))}
    </div>
  );
}

export default Favorited;
