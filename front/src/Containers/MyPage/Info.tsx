import React, { useEffect, useRef, useState } from 'react';
import noImg from 'assets/noimage.png';
import styles from './Info.module.scss';
import EditIcon from '@mui/icons-material/Edit';
import WalletIcon from '@mui/icons-material/Wallet';
import CheckIcon from '@mui/icons-material/Check';
import axios from 'axios';
import moment from 'moment';
import { useNavigate, useParams } from 'react-router-dom';
import { ButtonDiv } from 'component/button/Button';

function Info() {
  const baseURL = 'https://j8e207.p.ssafy.io/api/v1';
  const { address } = useParams();
  const [nickname, setNickname] = useState();
  const [input, setInput] = useState();
  const [editNickname, setEditNickname] = useState<boolean>(false);

  const fileInput = useRef<any>();
  // const [fileImage, setFileImage] = useState('');
  const [imageUrl, setImageUrl] = useState(`${noImg}`);

  const [regDt, setRegDt] = useState();

  useEffect(() => {
    axios({
      method: 'get',
      url: baseURL + `/user/${address}`,
    }).then((res) => {
      console.log(res.data.user);
      setNickname(res.data.user.nickname);
      setRegDt(res.data.user.regDt);
      setImageUrl(res.data.user.profileImg);
    });
  }, [nickname, imageUrl, address]);

  const handleToEdit = () => {
    setEditNickname(!editNickname);
  };
  const handleToNickname = () => {
    setEditNickname(!editNickname);
    console.log(input);
    setNickname(input);

    axios({
      method: 'put',
      url: baseURL + `/user/nickname`,
      data: {
        nickname: input,
      },
      headers: {
        Authorization: sessionStorage.getItem('accessToken'),
      },
    });
  };

  const handleProfileClick = () => {
    fileInput.current.click();
  };

  const handleChange = (e: any) => {
    profileChange(e.target.files[0]);
  };

  const profileChange = async (fileImage: any) => {
    await axios({
      method: 'post',
      url: baseURL + '/user/profileimg',
      headers: {
        'Content-Type': 'multipart/form-data',
        Authorization: sessionStorage.getItem('accessToken'),
      },
      data: {
        profileImgFile: fileImage,
      },
    }).then(() => {
      setImageUrl(URL.createObjectURL(fileImage));
    });
  };

  const navigate = useNavigate();

  const logout = () => {
    axios({
      method: 'delete',
      url: baseURL + '/auth/login',
      headers: {
        Authorization: sessionStorage.getItem('accessToken'),
      },
    }).then((res) => {
      sessionStorage.removeItem('address');
      sessionStorage.removeItem('accessToken');
      sessionStorage.removeItem('refreshToken');
      navigate('/');
    });
  };

  return (
    <>
      <div className={styles.container}>
        <div className={styles.profileImgContainer} onClick={handleProfileClick}>
          <img className={styles.profileImg} src={imageUrl} alt="" />
          <div className={styles.profileImgHover}>
            <EditIcon fontSize="large"></EditIcon>
          </div>
          <input
            type="file"
            ref={fileInput}
            accept="image/*"
            onChange={handleChange}
            style={{ display: 'none' }}
          />
        </div>
        <div className={styles.nicknameContainer}>
          <div className={styles.upContainer}>
            {editNickname ? (
              <>
                <div className={styles.editBox}>
                  <input
                    className={styles.editInput}
                    type="text"
                    onChange={(e: any) => {
                      console.log(e.target.value);
                      setInput(e.target.value);
                    }}
                    placeholder={nickname}
                  />
                </div>
                <div className={styles.checkBtn} onClick={handleToNickname}>
                  <CheckIcon></CheckIcon>
                </div>
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
            <div className={styles.regDt}>Joined {moment(`${regDt}`).format('MMMM YYYY')}</div>
          </div>
        </div>
        <div className={styles.logout} onClick={logout}>
          <ButtonDiv color='' text={'Logout'}/>
        </div>
      </div>
    </>
  );
}

export default Info;
