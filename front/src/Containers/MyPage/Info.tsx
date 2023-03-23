import React, { useState } from 'react';
import styles from './Info.module.scss';
import catImg from 'assets/cat.jpg';
import EditIcon from '@mui/icons-material/Edit';
import WalletIcon from '@mui/icons-material/Wallet';
import CheckIcon from '@mui/icons-material/Check';

function Info() {
  const [editNickname, setEditNickname] = useState<boolean>(false);
  const handleToEdit = () => {
    setEditNickname(!editNickname);
  };
  const handleToNickname = () => {
    setEditNickname(!editNickname);
  };
  return (
    <>
      <div className={styles.container}>
        <div className={styles.profileImgContainer}>
          <img className={styles.profileImg} src={catImg} alt="" />
          <div className={styles.profileImgHover}>
            <EditIcon fontSize="large"></EditIcon>
          </div>
        </div>
        <div className={styles.nicknameContainer}>
          <div className={styles.upContainer}>
            {editNickname ? (
              <>
                <div className={styles.editBox}>
                  <input className={styles.editInput} type="text" name="" />
                </div>
                <div className={styles.checkBtn} onClick={handleToNickname}>
                  <CheckIcon></CheckIcon>
                </div>
                {/* <button className={styles.checkBtn} onClick={handleToNickname}>확인</button> */}
              </>
            ) : (
              <>
                <div className={styles.nickname}>GoblinBatKiller</div>
                <EditIcon className={styles.edit} onClick={handleToEdit}></EditIcon>
              </>
            )}
          </div>
          <div className={styles.underContainer}>
            <div className={styles.wallet}>
              <WalletIcon></WalletIcon>
              <div className={styles.address}>0x553aasdfaew124wfsaq3241231</div>
            </div>
            <div className={styles.regDt}>Joined March 2023</div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Info;
