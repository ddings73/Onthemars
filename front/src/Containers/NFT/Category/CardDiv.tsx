import styles from './CardDiv.module.scss';
import Card from 'component/nftCard/card';
import { Link } from 'react-router-dom';
import { CategoryCropData } from 'Store/type/CategoryInfo';
import { imgBaseURL } from 'apis/baseApi';

export function NFTCategoryCard(cropData: any) {
  const crops = cropData.cropData

  return (
    <div className={styles.container}>
      <div className={styles.title}>items</div>
      <div className={styles.cardsDiv}>
        {Array.isArray(crops) && crops.map((data: CategoryCropData) =>
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