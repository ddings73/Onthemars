import Card from 'component/nftCard/card';
import styles from './NFTBodyContainer.module.scss';
import { Link, useNavigate } from "react-router-dom";
import { imgBaseURL } from 'apis/baseApi';

export function NFTBodyContainer() {
  const navigate = useNavigate();
  const topList = [1, 2, 3, 4, 5]

  const testList = [
    {
      rank: 1,
      cropType: 'CRS01',   // CRS01
      imgUrl: 'https://f1.tokenpost.kr/2021/12/p9f2wvlf7b.jpg',
      cropParent: 'Carrots', // Carrots (NFT 이름)
      floorPrice: 0.55,
      volume: 1234
    },
    {
      rank: 2,
      cropType: 'CRS01',   // CRS01
      imgUrl: 'https://f1.tokenpost.kr/2021/12/p9f2wvlf7b.jpg',
      cropParent: 'Carrots', // Carrots (NFT 이름)
      floorPrice: 0.55,
      volume: 1234
    },
    {
      rank: 3,
      cropType: 'CRS01',   // CRS01
      imgUrl: 'https://f1.tokenpost.kr/2021/12/p9f2wvlf7b.jpg',
      cropParent: 'Carrots', // Carrots (NFT 이름)
      floorPrice: 0.55,
      volume: 1234
    },
  ]

  const img_address = 'https://f1.tokenpost.kr/2021/12/p9f2wvlf7b.jpg'

  return (
    <div className={styles.container}>
      <div className={styles.topcontainer}>
        <div className={styles.topText}>
          Top
        </div>
        <div className={styles.topDiv}>
          {topList.map((id, i) =>
            <div key={i} className={styles.topDivEl} onClick={() => { navigate(`search/${id}`) }}>
              <p>{id}</p>
              {/* <Card size='big' img_address={imgBaseURL + data.imgUrl} /> */}
              <Card size='big' img_address={img_address} />
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
            {testList.map((data) => (
              data.rank <= 5 ?
                <div key={data.rank} className={styles.tredingSubDiv} onClick={() => { navigate(`category/${data.cropType}`) }}>
                  <div className={styles.leftDiv}>
                    <div style={{ fontWeight: '700', width: '30px' }}>{data.rank}</div>
                    {/* <Card key={rank} size='smo' img_address={imgBaseURL + data.imgUrl} /> */}
                    <Card size='smo' img_address={img_address} />
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
            {testList.map((data) => (
              data.rank > 5 ?
                <div key={data.rank} className={styles.tredingSubDiv} onClick={() => { navigate(`category/${data.cropType}`) }}>
                  <div className={styles.leftDiv}>
                    <div style={{ fontWeight: '700', width: '30px' }}>{data.rank}</div>
                    {/* <Card key={rank} size='smo' img_address={imgBaseURL + data.imgUrl} /> */}
                    <Card size='smo' img_address={img_address} />
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

