import axios from 'axios';
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
    const targetChainId = 5443;
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
      const targetChainId = 5443;
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
      if (typeof result === 'string') {
        sessionStorage.setItem('address', result);
      }
      axios({
        method: 'post',
        url: baseURL + '/auth/login',
        data: {
          address: result,
        },
      })
        .then((res) => {
          console.log(res.data);
          authUser(res.data.nonce);
        })
        .catch((err: any) => {
          console.log(err);
          //회원가입
          navigate('/signup');
        });
    }

    const authUser = async (nonce: string) => {
      if (typeof result === 'string') {
        const signature = await web3!.eth.personal.sign(
          `I am signing my one-time nonce: ${nonce}`,
          result,
          '',
        );
        await axios({
          method: 'post',
          url: baseURL + '/auth/auth',
          data: {
            address: result,
            signature: signature,
          },
        }).then((res: any) => {
          sessionStorage.setItem('accessToken', res.headers.get('accessToken'));
          sessionStorage.setItem('refreshToken', res.headers.get('refreshToken'));
          navigate('/mypage');
        });
      }
    };
  };

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

  const address = sessionStorage.getItem('address');

  return (
    <div className={styles.menu}>
      {address === null ? (
        <li className={styles.linkwrapper} onClick={load}>
          로그인
        </li>
      ) : (
        <li className={styles.linkwrapper} onClick={logout}>
          로그아웃
        </li>
      )}
    </div>
  );
}

export default Dropdown;
