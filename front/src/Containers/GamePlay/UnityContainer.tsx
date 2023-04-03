import React, { useCallback, useEffect, useState } from 'react';
import styles from './UnityContainer.module.scss';
import { Unity, useUnityContext } from 'react-unity-webgl';
import unityres from './unityres.json';
import { O2Contract, NFTContract, MARS_CONTRACT_ADDRESS, ADMIN_ADDRESS } from 'apis/ContractAddress';
import { api } from 'apis/api/ApiController';

function UnityContainer() {
  const [jsonFile, setJsonFile] = useState<string>('');
  const {
    unityProvider,
    sendMessage,
    addEventListener,
    removeEventListener,
    UNSAFE__detachAndUnloadImmediate: detachAndUnloadImmediate,
  } = useUnityContext({
    loaderUrl: '/Build/Build.loader.js',
    dataUrl: '/Build/Build.data',
    frameworkUrl: '/Build/Build.framework.js',
    codeUrl: '/Build/Build.wasm',
  });

  const reactSetData = useCallback((data: any) => {
    setJsonFile(data);
    console.log('리액트 콜백 데이터: ', jsonFile);
  }, []);

  useEffect(() => {
    addEventListener('CallReact', reactSetData);
    return () => {
      removeEventListener('CallReact', reactSetData);
    };
  }, [reactSetData]);

  useEffect(() => {
    console.log('리액트에서 받은 데이터: ', jsonFile);
  }, [jsonFile]);

  const address = sessionStorage.getItem('address');
  const [colorUrl, setColorUrl] = useState('');
  const [cropUrl, setCropUrl] = useState('');
  // console.log(unityres);
  const getData = () => {
    // 씨앗 구매
    const buySeedPrice = unityres.player.buySeedCnt * 1000;
    O2Contract.methods.transfer(ADMIN_ADDRESS, buySeedPrice).send({
      from: address,
      gasPrice: '0',
    });

    // 민팅
    const harvestCropList = unityres.player.harvestCropList;
    harvestCropList.map((item: any) => {
      const crop = item.type.substr(3, 2);
      const nonce = Math.floor(Math.random() * 100001);
      const tokenId = NFTContract.methods.mint(crop, nonce).send({
        from: address,
        gasPrice: '0',
      });
      // dna
      const dna = NFTContract.methods.getNftDna(tokenId).call();

      // 1단계 조회
      api
        .get(`farm/${dna}`, {
          headers: { Authorization: sessionStorage.getItem('accessToken') },
        })
        .then((res) => {
          setColorUrl(res.data.colorUrl);
          setCropUrl(res.data.cropUrl);
        });

      // 이미지 합성

      // 노션따라서 데이터 가공

      // 그걸 백으로 보내
    });
  };

  return (
    <>
      <div className={styles.container}>
        <Unity
          className={styles.unityContainer}
          unityProvider={unityProvider}
          style={{ marginTop: '2%', width: '110%', height: '110%' }}
        />
        <h1>{jsonFile}</h1>
      </div>
    </>
  );
}

export default UnityContainer;
