import Card from 'component/nftCard/card';
import React from 'react';
import styles from './NFTBodyContainer.module.scss';

export function NFTBodyContainer() {
  const testList = [1, 2, 3, 4, 5]
  const list = [1, 2]
  return (
    <div className={styles.container}>
      <div className={styles.topcontainer}>
        <div className={styles.topText}>
          Top
        </div>
        <div className={styles.topDiv}>
          {testList.map((test) =>
            <Card key={test} size='mid' />
          )
          }
        </div>
      </div>
      <div className={styles.topcontainer}>
        <div className={styles.topText}>
          Treding
        </div>
        <div className={styles.tredingDiv}>
          {list.map((rank) =>
            <div style={{ width: '50%' }}>
              <div className={styles.tredingSubDiv} style={{ color: 'gray' }}>
                <div className={styles.leftDiv}>COLLECTION</div>
                <div className={styles.midDiv}>FLLOR PRICE</div>
                <div className={styles.rightDiv}>VOLUME</div>
              </div>
              {testList.map((test) =>
                <div key={test} className={styles.tredingSubDiv}>
                  <div className={styles.leftDiv}>
                    <div>{test}</div>
                    <Card key={test} size='smo' />
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
    </div>
  );
}

export default NFTBodyContainer;
