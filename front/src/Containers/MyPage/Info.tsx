import React, { useEffect, useState } from 'react';
import styles from './Info.module.scss';
import catImg from 'assets/cat.jpg';
import EditIcon from '@mui/icons-material/Edit';
import WalletIcon from '@mui/icons-material/Wallet';
import CheckIcon from '@mui/icons-material/Check';
import axios from 'axios';

function Info() {
  const baseURL = 'https://j8e207.p.ssafy.io/api/v1';
  const address = 'user_address_1';
  useEffect(() => {
    axios({
      method: 'get',
      url: baseURL + `/user/${address}`,
    }).then((res) => {
      console.log(res.data);
      setNickname(res.data.nickname);
    });
  });
  const [nickname, setNickname] = useState();
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
                  <input className={styles.editInput} type="text" name="" placeholder={nickname} />
                </div>
                <div className={styles.checkBtn} onClick={handleToNickname}>
                  <CheckIcon></CheckIcon>
                </div>
                {/* <button className={styles.checkBtn} onClick={handleToNickname}>확인</button> */}
              </>
            ) : (
              <>
                <div className={styles.nickname}>{nickname}</div>
                <EditIcon className={styles.edit} onClick={handleToEdit}></EditIcon>
              </>
            )}
          </div>
          <div className={styles.underContainer}>
            <div className={styles.wallet}>
              <WalletIcon></WalletIcon>
              <div className={styles.address}>{address}</div>
            </div>
            <div className={styles.regDt}>Joined March 2023</div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Info;
