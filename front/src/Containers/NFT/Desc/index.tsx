import styles from './index.module.scss';
import nftdesc from 'assets/nftdesc.png';

function NFTDesc() {
  return (
    <div className={styles.container}>
      <div className={styles.desc}>
        <img src={nftdesc} alt="" />
      </div>
    </div>
  );
}

export default NFTDesc;
