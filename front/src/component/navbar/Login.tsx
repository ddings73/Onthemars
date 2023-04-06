import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import styles from './Login.module.scss';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { api } from 'apis/api/ApiController';
import { O2Contract, web3 } from 'apis/ContractAddress';
import Swal from 'sweetalert2';

function Login() {
  const navigate = useNavigate();

  const getRequestAccounts = async (): Promise<string> => {
    const accounts = await (window as any).ethereum.request({
      method: 'eth_requestAccounts',
    });
    return accounts[0];
  };

  const init = async (): Promise<string | undefined> => {
    const targetChainId = 2731;
    try {
      await (window as any).ethereum.request({
        method: 'wallet_switchEthereumChain',
        params: [{ chainId: web3.utils.toHex(targetChainId) }],
      });
      return await getRequestAccounts();
    } catch (error: any) {
      const code: number = error.code;
      const str: string = `${code}`;
      return str;
    }
  };

  const load = async (): Promise<void> => {
    const result = await init();
    if (result === '4902') {
      const targetChainId = 2731;
      try {
        await (window as any).ethereum.request({
          method: 'wallet_addEthereumChain',
          params: [
            {
              chainId: web3.utils.toHex(targetChainId),
              chainName: 'Onthemars',
              rpcUrls: ['https://j8e207.p.ssafy.io/rpc'],
              nativeCurrency: {
                name: 'Ethereum',
                symbol: 'ETH',
                decimals: 18,
              },
            },
          ],
        });
      } catch (addError) {
        console.log(addError);
      }
    } else if (result === '-32002') {
      Swal.fire('메타마스크가 켜져있는지 확인해주세요.', '','error');
    } else if (result === 'undefined') {
      window.open('https://metamask.io/download/');
    } else {
      // setAccount(result);
      if (typeof result === 'string') {
        sessionStorage.setItem('address', result);
      }
      api
        .post('/auth/login', { address: result })
        .then((res) => {
          authUser(res.data.nonce);
        })
        .catch((err: Error) => {
          navigate('/signup');
        });
    }

    const authUser = async (nonce: string) => {
      if (typeof result === 'string') {
        const signature = await web3.eth.personal.sign(
          `I am signing my one-time nonce: ${nonce}`,
          result,
          '',
        );
        await api
          .post('/auth', {
            address: result,
            signature: signature,
          })
          .then(async (res: any) => {
            O2Contract.methods.mintToMember(result, 10000).send({
              from: result,
              gasPrice: '0',
            });

            sessionStorage.setItem('accessToken', res.headers.get('accessToken'));
            sessionStorage.setItem('refreshToken', res.headers.get('refreshToken'));
            sessionStorage.setItem('received', 'false');

            api.post(
              '/alarms',
              {},
              {
                headers: {
                  Authorization: sessionStorage.getItem('accessToken'),
                  fcmToken: sessionStorage.getItem('fcmToken'),
                },
              },
            );
            navigate(`/mypage/${result}`);
          });
      }
    };
  };

  const address = sessionStorage.getItem('address');
  const [myImg, setMyImg] = useState();

  useEffect(() => {
    if (address !== null) {
      api.get(`/user/${address}`).then((res) => {
        setMyImg(res.data.user.profileImg);
      });
    }
  });

  const mypage = () => {
    navigate(`/mypage/${address}`);
  };

  return (
    <>
      {address === null ? (
        <AccountCircleIcon
          sx={{
            color: 'white',
            fontSize: '3.5rem',
          }}
          className={styles.account}
          onClick={load}
        />
      ) : (
        <img className={styles.myimg} src={myImg} onClick={mypage} alt="" />
      )}
    </>
  );
}

export default Login;
