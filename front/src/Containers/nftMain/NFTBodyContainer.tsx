import React from "react";
import styles from "./NFTBodyContainer.module.scss";

export function NFTBodyContainer() {
  return (
    <div className={styles.container}>
      <div className={styles.gameContainer}>
        <div className={styles.contentWrapper}>
          <div className={styles.contentLeft}>
            {" "}
            <h1>NFT VEGETs</h1>
            <p>
              {" "}
              메타버스 세상에서 직접 재배한 농작물로 <br />
              나만의 NFT 아이템을 수집하고, <br />
              친구들과 함께 소통해보세요!
            </p>
            <button type="button" className={styles.playButton}>
              PLAY
            </button>
          </div>
          <div className={styles.contentRight}>123</div>
        </div>
      </div>
    </div>
  );
}

export default NFTBodyContainer;
