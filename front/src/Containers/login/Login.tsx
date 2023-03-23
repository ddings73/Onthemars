import React from 'react';
import styles from './Login.module.scss';
import noImg from 'assets/noimage.png';
import EditIcon from '@mui/icons-material/Edit';
import CheckIcon from '@mui/icons-material/Check';

function Login() {
  return (
    <div className={styles.container}>
      <div className={styles.title}>Profile details</div>
      <div className={styles.profileImgContainer}>
        <img className={styles.profileImg} src={noImg} alt="" />
        <div className={styles.profileImgHover}>
          <EditIcon fontSize='large'></EditIcon>
        </div>
      </div>
      <div className={styles.nickname}>
        <div className={styles.nicknameTitle}>*</div>
        <div className={styles.editBox}>
          <input
            className={styles.editInput}
            type="text"
            name=""
            placeholder="Please Enter Your Nickname"
            required
          />
        </div>
        <div className={styles.checkBtn}>
          <CheckIcon fontSize="large"></CheckIcon>
        </div>
      </div>
    </div>
  );
}
export default Login;
