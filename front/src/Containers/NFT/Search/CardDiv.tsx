import styles from './CardDiv.module.scss';
import Card from 'component/nftCard/card';
import { Link } from 'react-router-dom';
import { imgBaseURL } from 'apis/baseApi';
import { CategoryCropData } from 'Store/type/CategoryInfo';

export function NFTCard(searchData: any) {
  const data = searchData.searchData

  return (
    <div className={styles.container}>
      <div className={styles.title}>items</div>
      <div className={styles.cardsDiv}>
        {Array.isArray(data) && data.map((data: CategoryCropData) =>
          <div key={data.transactionId} className={styles.cardDiv} >
            <Link to={`/nft/search/${data.transactionId}`}>
              <Card size='bigg' img_address={imgBaseURL + data.imgUrl} />
            </Link>
          </div>
        )}
      </div>
    </div>
  );
}