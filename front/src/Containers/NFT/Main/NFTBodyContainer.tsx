import Card from 'component/nftCard/card';
import styles from './NFTBodyContainer.module.scss';
import { Link, useNavigate } from "react-router-dom";

export function NFTBodyContainer() {
  const navigate = useNavigate();

  const testList = ['nft_address_1', 'nft_address_2', 'nft_address_3', 'nft_address_4', 'nft_address_5']
  const list = [1, 2]
  const img_address = 'https://f1.tokenpost.kr/2021/12/p9f2wvlf7b.jpg'

  return (
    <div className={styles.container}>
      <div className={styles.topcontainer}>
        <div className={styles.topText}>
          Top
        </div>
        <div className={styles.topDiv}>
          {testList.map((id, i) =>
            <div key={i} className={styles.topDivEl} onClick={() => { navigate(`search/${id}`) }}>
              <p>{id}</p>
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
          {list.map((rank, i) =>
            <div key={i} style={{ width: '50%' }}>
              <div className={styles.tredingSubDiv} style={{ color: 'gray' }}>
                <div className={styles.leftDiv}>COLLECTION</div>
                <div className={styles.midDiv}>FLLOR PRICE</div>
                <div className={styles.rightDiv}>VOLUME</div>
              </div>
              {testList.map((test) =>
                <div key={test} className={styles.tredingSubDiv}>
                  <div className={styles.leftDiv}>
                    <div style={{ fontWeight: '700' }}>{test}</div>
                    <Card key={test} size='smo' img_address={img_address} />
                    <div>NFT 이름</div>
                  </div>
                  <div className={styles.midDiv}>0.55 O2</div>
                  <div className={styles.rightDiv}>1,234 O2</div>

                </div>
              )
              }
            </div>)}
        </div>
      </div>
    </div >
  );
}

