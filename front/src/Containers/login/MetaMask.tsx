import { useState } from 'react';
import Web3 from 'web3';
import styles from './MetaMask.module.scss';

const SERVER_ACCOUNT = '0xbE4000931291238F0b30f3f7587731bb89e3330A';

function MetaMask(): JSX.Element {
  const [account, SetAccount] = useState<string>();

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
    }
    else if(result === '-32002'){
        alert('메타마스크가 켜져있는지 확인해주세요.');
    }
    else if (result === 'undefined') {
      window.open('https://metamask.io/download/');
    }
    else{
        SetAccount(result);
    }
  };

  const buyO2 = async (): Promise<void> => {
    await (window as any).ethereum
      .request({
        method: 'eth_sendTransaction',
        params: [
          {
            from: account,
            to: SERVER_ACCOUNT,
            gas: '0x76c0',
            gasPrice: '0x9184e72a000',
            value: '222',
            data: '0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f072445675058bb8eb970870f072445675',
          },
        ],
      })
      .then((txHash: string) => console.log(txHash))
      .catch((error: Error) => console.log(error));
  };

  return (
    <div className={styles.container}>
      <div className={styles.btns}>
        <div style={{ fontSize: '20px' }}>Your account is : {account}</div>
        <button onClick={load}>로그인</button> <button type="submit">회원가입</button>{' '}
        <button onClick={buyO2}>O2 구매</button>
      </div>
    </div>
  );
}

export default MetaMask;
