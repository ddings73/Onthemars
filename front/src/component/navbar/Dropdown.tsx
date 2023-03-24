import axios from 'axios';
import React from 'react';
import { useNavigate } from 'react-router';
import Web3 from 'web3';
import styles from './Dropdown.module.scss';

function Dropdown() {
  const baseURL = 'https://j8e207.p.ssafy.io/api/v1';
  const navigate = useNavigate();
  // const [account, setAccount] = useState<string>();

  const web3 = new Web3((window as any).ethereum);

  const getRequestAccounts = async (): Promise<string> => {
    const accounts = await (window as any).ethereum.request({
      method: 'eth_requestAccounts',
    });
    return accounts[0];
  };

  const init = async (): Promise<string | undefined> => {
    const targetChainId = 1337;
    try {
      await (window as any).ethereum.request({
        method: 'wallet_switchEthereumChain',
        params: [{ chainId: web3.utils.toHex(targetChainId) }],
      });
      console.log('wallet_switchEthereumChain - call');
      console.log(await getRequestAccounts());
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
      const targetChainId = 1337;
      try {
        await (window as any).ethereum.request({
          method: 'wallet_addEthereumChain',
          params: [
            {
              chainId: web3.utils.toHex(targetChainId),
              chainName: 'ganache',
              rpcUrls: ['http://127.0.0.1:8545'],
              nativeCurrency: {
                name: 'Ethereum',
                symbol: 'ETH',
                decimals: 18,
              },
            },
          ],
        });
        console.log('wallet_addEthereumChain - call');
      } catch (addError) {
        console.log(addError);
      }
    } else if (result === '-32002') {
      alert('메타마스크가 켜져있는지 확인해주세요.');
    } else if (result === 'undefined') {
      window.open('https://metamask.io/download/');
    } else {
      // setAccount(result);
      axios({
        method: 'post',
        url: baseURL + '/user/login',
        data: {
          address: result,
        },
      })
        .then((res) => {
          console.log(res.data);
        })
        .catch((err: any) => {
          console.log(err);
          navigate('/login');
        });
    }
  };

  return (
    <div className={styles.menu}>
      <li className={styles.linkwrapper} onClick={load}>
        로그인
      </li>
    </div>
  );
}

export default Dropdown;
