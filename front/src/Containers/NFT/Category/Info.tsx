import { baseURL, imgBaseURL } from 'apis/baseApi';
import axios from 'axios';
import { useEffect, useState } from 'react';
import styles from './Info.module.scss';
import { CategoryInfoType } from 'Store/type/CategoryInfo';
import Back from 'assets/parts/banner/1.png'
import Card from 'component/nftCard/card';

export function Categoryinfo(props: any) {
  const crops = props.props
  const [data, setData] = useState<CategoryInfoType>();

  useEffect(() => {
    axios({
      method: 'get',
      url: baseURL + `/nft/${crops}/detail`,
      headers: {
        Authorization: sessionStorage.getItem('accessToken'),
      },
    }).then((res) => {
      setData(res.data);
    });
  }, [crops]);

  return (
    <div className={styles.container}>
      <img className={styles.backImg} src={imgBaseURL + data?.backImg} alt="" />
      <div className={styles.infoContainer}>
        <img className={styles.cardImg} src={imgBaseURL + data?.cardImg} alt="" />
        {/* <img className={styles.cardImg} src={img_address} alt="" /> */}
        <div className={styles.cropMainTitle}>
          {data?.cropParent}
        </div>
        <div className={styles.cropInfo}>
          {data?.cropBio}
        </div>
        <div className={styles.detailDiv}>
          <div className={styles.oneInfo}>
            <div className={styles.cropTitle}>
              {data?.totalVolume.toLocaleString()}O₂
            </div>
            <div className={styles.detailTitle}>
              total volume
            </div>
          </div>
          <div className={styles.oneInfo}>
            {data?.floorPrice === -1 ?
              <div className={styles.cropTitle}>-</div> :
              <div className={styles.cropTitle}>
                {data?.floorPrice.toLocaleString()}O₂
              </div>
            }
            <div className={styles.detailTitle}>
              floor price
            </div>
          </div>
          <div className={styles.oneInfo}>
            <div className={styles.cropTitle}>
              {data?.listed}%
            </div>
            <div className={styles.detailTitle}>
              listed
            </div>
          </div>
        </div>
      </div>

    </div>
  )
}