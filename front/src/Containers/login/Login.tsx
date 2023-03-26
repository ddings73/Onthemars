import React, { useState } from 'react';
import styles from './Login.module.scss';
import noImg from 'assets/noimage.png';
import EditIcon from '@mui/icons-material/Edit';
import CheckIcon from '@mui/icons-material/Check';
import { ButtonDiv } from 'component/button/Button';
import { useNavigate } from 'react-router-dom';

function Login() {
  const [nickname, setNickname] = useState('');
  const [msg, setMsg] = useState('');
  const navigate = useNavigate();

  const handleNicknameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(event.target.value);
  };

  const handleRegisterClick = () => {
    if (nickname.trim().length === 0) {
      setMsg('Please Input your nickname.');
    } else if (nickname.trim().length < 2) {
      setMsg('Please Input at least 2 characters');
    } else {
      setMsg('');
      // 마이페이지로 넘어가게?
      navigate('/mypage');
      // axios 연결
    }
  };
  return (
    <div className={styles.container}>
      <div className={styles.title}>Profile details</div>
      <div className={styles.profileImgContainer}>
        <img className={styles.profileImg} src={noImg} alt="" />
        <div className={styles.profileImgHover}>
          <EditIcon fontSize="large"></EditIcon>
        </div>
      </div>
      <div className={styles.nickname}>
        <div className={styles.nicknameTitle}>*</div>
        <div className={styles.editBox}>
          <input
            className={styles.editInput}
            type="text"
            name=""
            placeholder="Please Input Your Nickname"
            required
            value={nickname}
            onChange={handleNicknameChange}
          />
        </div>
        <div className={styles.checkBtn}>
          <CheckIcon fontSize="large"></CheckIcon>
        </div>
      </div>
      <div className={styles.msg}>
        {msg}
      </div>
      <div className={styles.inputBtn} onClick={handleRegisterClick}>
        <ButtonDiv text={'등록'}/>
      </div>
    </div>
  );
}
export default Login;
