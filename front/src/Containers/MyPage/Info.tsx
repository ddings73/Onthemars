import React from 'react';
import styles from './Info.module.scss';
import catImg from 'assets/cat.jpg';
import EditIcon from '@mui/icons-material/Edit';
import WalletIcon from '@mui/icons-material/Wallet';

function Info() {
  return (
    <>
      <div className={styles.container}>
        <div className={styles.profileImgContainer}>
          <img className={styles.profileImg} src={catImg} alt="" />
          <div className={styles.profileImgHover}>
            <EditIcon></EditIcon>
          </div>
        </div>
        <div className={styles.nicknameContainer}>
          <div className={styles.nickname}>GoblinBatKiller</div>
          <div className={styles.underContainer}>
            <div className={styles.wallet}>
              <WalletIcon></WalletIcon>
              <div className={styles.address}>0x553aasdfaew124wfsaq3241231</div>
            </div>
            <div className={styles.regDt}>Joined March 3rd</div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Info;
