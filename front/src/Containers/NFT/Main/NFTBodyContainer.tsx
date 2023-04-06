import Card from 'component/nftCard/card';
import styles from './NFTBodyContainer.module.scss';
import { useNavigate } from "react-router-dom";
import { baseURL, imgBaseURL } from 'apis/baseApi';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { MainCategoryRank, MainTopRank } from 'Store/type/CategoryInfo';
import { Loading } from 'component/layOut/Lotty';

export function NFTBodyContainer() {
  const navigate = useNavigate();
  const [categoryList, setCategoryList] = useState<MainCategoryRank>()
  const [categoryLoading, setCategoryLoading] = useState<boolean>(true)

  const [topList, setTopList] = useState<MainTopRank>()
  const [topLoading, setTopLoading] = useState<boolean>(true)

  useEffect(() => {
    axios({
      method: 'get',
      url: baseURL + `/nft/top`,
    }).then((res) => {
      console.log(res.data);
      setTopList(res.data);
      setTopLoading(false);
    });
  }, []);

  useEffect(() => {
    axios({
      method: 'get',
      url: baseURL + `/nft/trending`,
    }).then((res) => {
      console.log(res.data);
      setCategoryList(res.data);
      setCategoryLoading(false);
    });
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.topcontainer}>
        <div className={styles.topText}>
          Top
        </div>
        {topLoading ?
          <Loading /> :
          <div className={styles.topDiv}>
            {Array.isArray(topList) && topList.map((data: MainTopRank) =>
              <div key={data.rank} className={styles.topDivEl} onClick={() => { navigate(`search/${data.transactionId}`) }}>
                <p>{data.rank}</p>
                <Card size='big' img_address={imgBaseURL + data.imgUrl} />
              </div>
            )}
          </div>
        }
      </div>
      <div className={styles.topcontainer}>
        <div className={styles.topText}>
          Treding
        </div>
        {topLoading ?
          <Loading /> :
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
                      <div>{data.cropParent}</div>
                    </div>
                    {data.floorPrice === -1 ? <div className={styles.midDiv}> - </div>
                      :
                      <div className={styles.midDiv}>{data.floorPrice.toLocaleString()}&nbsp;O2</div>
                    }
                    <div className={styles.rightDiv}>{data.volume.toLocaleString()} &nbsp;O2</div>

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
                      <div>{data.cropParent}</div>
                    </div>
                    {data.floorPrice === -1 ? <div className={styles.midDiv}> - </div>
                      :
                      <div className={styles.midDiv}>{data.floorPrice.toLocaleString()}&nbsp;O2</div>
                    }
                    <div className={styles.rightDiv}>{data.volume.toLocaleString()} &nbsp;O2</div>

                  </div> : <></>)
              )
              }
            </div>
          </div>}
      </div>
    </div >
  );
}

