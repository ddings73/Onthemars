import Card from 'component/nftCard/card';
import styles from './NFTBodyContainer.module.scss';
import { useNavigate } from "react-router-dom";
import { baseURL, imgBaseURL } from 'apis/baseApi';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { MainCategoryRank, MainTopRank } from 'Store/type/CategoryInfo';

export function NFTBodyContainer() {
  const navigate = useNavigate();
  const [categoryList, setCategoryList] = useState<MainCategoryRank>()

  const [topList, setTopList] = useState<MainTopRank>()

  useEffect(() => {
    axios({
      method: 'get',
      url: baseURL + `/nft/top`,
    }).then((res) => {
      console.log(res.data);
      setTopList(res.data);
    });
  }, []);

  useEffect(() => {
    axios({
      method: 'get',
      url: baseURL + `/nft/trending`,
    }).then((res) => {
      console.log(res.data);
      setCategoryList(res.data);
    });
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.topcontainer}>
        <div className={styles.topText}>
          Top
        </div>
        <div className={styles.topDiv}>
          {Array.isArray(topList) && topList.map((data: MainTopRank) =>
            <div key={data.rank} className={styles.topDivEl} onClick={() => { navigate(`search/${data.transactionId}`) }}>
              <p>{data.rank}</p>
              <Card size='big' img_address={imgBaseURL + data.imgUrl} />
            </div>
          )}
        </div>
      </div>
      <div className={styles.topcontainer}>
        <div className={styles.topText}>
          Treding
        </div>
        <div className={styles.tredingDiv}>
          <div style={{ width: '50%' }}>
            <div className={styles.tredingSubDiv} style={{ color: 'gray' }}>
              <div className={styles.leftDiv}>COLLECTION</div>
              <div className={styles.midDiv}>FLLOR PRICE</div>
              <div className={styles.rightDiv}>VOLUME</div>
            </div>
            {Array.isArray(categoryList) && categoryList.map((data: MainCategoryRank) => (
              data.rank <= 5 ?
                <div key={data.rank} className={styles.tredingSubDiv} onClick={() => { navigate(`category/${data.cropType}`) }}>
                  <div className={styles.leftDiv}>
                    <div style={{ fontWeight: '700', width: '30px' }}>{data.rank}</div>
                    <Card size='smo' img_address={imgBaseURL + data.imgUrl} />
                    <div>NFT 이름</div>
                  </div>
                  <div className={styles.midDiv}>{data.floorPrice}O2</div>
                  <div className={styles.rightDiv}>{data.volume.toLocaleString()} O2</div>

                </div> : <></>)
            )
            }
          </div>
          <div style={{ width: '50%' }}>
            <div className={styles.tredingSubDiv} style={{ color: 'gray' }}>
              <div className={styles.leftDiv}>COLLECTION</div>
              <div className={styles.midDiv}>FLLOR PRICE</div>
              <div className={styles.rightDiv}>VOLUME</div>
            </div>
            {Array.isArray(categoryList) && categoryList.map((data: MainCategoryRank) => (
              data.rank > 5 ?
                <div key={data.rank} className={styles.tredingSubDiv} onClick={() => { navigate(`category/${data.cropType}`) }}>
                  <div className={styles.leftDiv}>
                    <div style={{ fontWeight: '700', width: '30px' }}>{data.rank}</div>
                    <Card size='smo' img_address={imgBaseURL + data.imgUrl} />
                    <div>NFT 이름</div>
                  </div>
                  <div className={styles.midDiv}>{data.floorPrice}O2</div>
                  <div className={styles.rightDiv}>{data.volume.toLocaleString()} O2</div>

                </div> : <></>)
            )
            }
          </div>
        </div>
      </div>
    </div >
  );
}

