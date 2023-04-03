import styles from './CardDiv.module.scss';
import Card from 'component/nftCard/card';
import { Link } from 'react-router-dom';

export function NFTCard() {

  const testList = ['nft_address_1', 'nft_address_2']
  const img_address = 'https://f1.tokenpost.kr/2021/12/p9f2wvlf7b.jpg'

  return (
    <div className={styles.container}>
      <div className={styles.title}>items</div>
      <div className={styles.cardsDiv}>
        {testList.map((nftAdress) =>
          <div key={nftAdress} className={styles.cardDiv} >
            <Link to={`${nftAdress}`}>
              <Card size='bigg' img_address={img_address} />
            </Link>
          </div>
        )}
      </div>
    </div>
  );
}