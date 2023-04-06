import React, { useRef, useState } from 'react';
import styles from './Signup.module.scss';
import noImg from 'assets/noimage.png';
import EditIcon from '@mui/icons-material/Edit';
import CheckIcon from '@mui/icons-material/Check';
import { ButtonDiv } from 'component/button/Button';
import { useNavigate } from 'react-router-dom';
import { api } from 'apis/api/ApiController';
import { web3, O2Contract, O2_CONTRACT_ADDRESS } from 'apis/ContractAddress';

function Signup() {
  const address = sessionStorage.getItem('address');
  const [nickname, setNickname] = useState('');
  const [msg, setMsg] = useState('');
  const navigate = useNavigate();

  const handleNicknameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(event.target.value);
    console.log(event.target.value);
  };

  const fileInput = useRef<any>();
  const [fileImage, setFileImage] = useState();
  const [imageUrl, setImageUrl] = useState(`${noImg}`);
  const handleProfileClick = () => {
    fileInput.current.click();
  };

  const handleChange = (e: any) => {
    setImageUrl(URL.createObjectURL(e.target.files[0]));
    setFileImage(e.target.files[0]);
  };

  const handleRegisterClick = (e: any) => {
    if (nickname.trim().length === 0) {
      setMsg('Please Input your nickname.');
    } else if (nickname.trim().length < 2) {
      setMsg('Please Input at least 2 characters');
    } else {
      setMsg('');

      console.log(nickname);
      const formData = new FormData();
      if (typeof address === 'string') {
        formData.append('address', address);
      }
      formData.append('nickname', nickname);
      if (typeof fileImage === 'object') {
        formData.append('profileImgFile', fileImage);
      }
      console.log('formData ', typeof fileImage);

      api
        .post('/auth/signup', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then(async () => {
          await (window as any).ethereum.request({
            method: 'wallet_watchAsset',
            params: {
              type: 'ERC20',
              options: {
                address: O2_CONTRACT_ADDRESS,
                symbol: 'O2',
                decimals: 2,
              },
            },
          });

          login();
        });
    }
  };

  const login = async () => {
    //로그인
    await api
      .post('/auth/login', {
        address: address,
      })
      .then((res) => {
        console.log(res.data);
        authUser(res.data.nonce);
      })
      .catch((err: Error) => {
        console.log(err);
      });
  };

  const authUser = async (nonce: string) => {
    if (typeof address === 'string') {
      const signature = await web3.eth.personal.sign(`I am signing my one-time nonce: ${nonce}`, address, '');
      await api
        .post('/auth', {
          address: address,
          signature: signature,
        })
        .then(async (res: any) => {
          O2Contract.methods.mintToMember(address, 10000).send({
            from: address,
            gasPrice: '0',
          });
          sessionStorage.setItem('accessToken', res.headers.get('accessToken'));
          sessionStorage.setItem('refreshToken', res.headers.get('refreshToken'));
          sessionStorage.setItem('received', 'false');

          await api.post(
            '/alarms',
            {},
            {
              headers: {
                Authorization: sessionStorage.getItem('accessToken'),
                fcmToken: sessionStorage.getItem('fcmToken'),
              },
            },
          );
          navigate(`/mypage/${address}`);
        });
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.title}>Profile details</div>
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
          {/* 중복이 아니고 2글자 이상이면 */}
        </div>
      </div>
      <div className={styles.msg}>{msg}</div>
      <div className={styles.inputBtn} onClick={handleRegisterClick}>
        <ButtonDiv text={'등록'} />
      </div>
    </div>
  );
}
export default Signup;
