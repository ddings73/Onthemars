import React, { useEffect, useRef, useState } from 'react';
import noImg from 'assets/noimage.png';
import styles from './Info.module.scss';
import EditIcon from '@mui/icons-material/Edit';
import WalletIcon from '@mui/icons-material/Wallet';
import CheckIcon from '@mui/icons-material/Check';
import moment from 'moment';
import { useNavigate, useParams } from 'react-router-dom';
import { ButtonDiv } from 'component/button/Button';
import { api } from 'apis/api/ApiController';

function Info() {
  const { address } = useParams();
  const [nickname, setNickname] = useState();
  const [input, setInput] = useState();
  const [editNickname, setEditNickname] = useState<boolean>(false);

  const fileInput = useRef<any>();
  // const [fileImage, setFileImage] = useState('');
  const [imageUrl, setImageUrl] = useState(`${noImg}`);

  const [regDt, setRegDt] = useState();

  useEffect(() => {
    api.get(`/user/${address}`).then((res) => {
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
    setNickname(input);

    api.put(
      `/user/nickname`,
      {
        nickname: input,
      },
      {
        headers: {
          Authorization: sessionStorage.getItem('accessToken'),
        },
      },
    );
  };

  const handleProfileClick = () => {
    fileInput.current.click();
  };

  const handleChange = (e: any) => {
    profileChange(e.target.files[0]);
  };

  const profileChange = async (fileImage: any) => {
    await api
      .post(
        '/user/profileimg',
        {
          profileImgFile: fileImage,
        },
        {
          headers: {
            'Content-Type': 'multipart/form-data',
            Authorization: sessionStorage.getItem('accessToken'),
          },
        },
      )
      .then(() => {
        setImageUrl(URL.createObjectURL(fileImage));
      });
  };

  const navigate = useNavigate();

  const logout = () => {
    const refreshToken = sessionStorage.getItem('refreshToken');
    api.delete('/auth/login', { headers: { refreshToken } }).then((res) => {
      sessionStorage.removeItem('address');
      sessionStorage.removeItem('accessToken');
      sessionStorage.removeItem('refreshToken');
      sessionStorage.removeItem('received');
      navigate('/');
    });
  };

  return (
    <>
      <div className={styles.container}>
        {address === sessionStorage.getItem('address') ? (
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
        ) : (
          <div className={styles.profileImgContainer}>
            <img className={styles.athprofileImg} src={imageUrl} alt="" />
          </div>
        )}
        <div className={styles.nicknameContainer}>
          <div className={styles.upContainer}>
            {address !== sessionStorage.getItem('address') ? (
              <div className={styles.nickname}>{nickname}</div>
            ) : editNickname ? (
              <>
                <div className={styles.editBox}>
                  <input
                    className={styles.editInput}
                    type="text"
                    onChange={(e: any) => {
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
        {address === sessionStorage.getItem('address') ? (
          <div className={styles.logout} onClick={logout}>
            <ButtonDiv color="" text={'Logout'} />
          </div>
        ) : (
          <></>
        )}
      </div>
    </>
  );
}

export default Info;
