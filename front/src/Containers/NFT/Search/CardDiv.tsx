import styles from './CardDiv.module.scss';
import Card from 'component/nftCard/card';

export function NFTCard() {

  const testList = [1, 2, 3, 4]
  const img_address = 'https://f1.tokenpost.kr/2021/12/p9f2wvlf7b.jpg'

  return (
    <div className={styles.container}>
      <div className={styles.title}>items</div>
      <div className={styles.cardsDiv}>
        {testList.map((test) =>
          <div key={test} className={styles.cardDiv} >
            <Card key={test} size='big' img_address={img_address} />
          </div>
        )}
      </div>
    </div>
  );
}