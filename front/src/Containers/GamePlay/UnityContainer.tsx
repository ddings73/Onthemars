import React, { useCallback, useEffect, useState } from 'react';
import styles from './UnityContainer.module.scss';
import { Unity, useUnityContext } from 'react-unity-webgl';
import mergeImages from 'merge-images';
import unityres from './unityres.json';
import {
  O2Contract,
  O2_CONTRACT_ADDRESS,
  NFTContract,
  MARS_CONTRACT_ADDRESS,
  ADMIN_ADDRESS,
} from 'apis/ContractAddress';
import { api } from 'apis/api/ApiController';
import { contentQuotesLinter } from '@ant-design/cssinjs/lib/linters';

function dataURLtoFile(dataurl: string, filename: string) {
  var arr: any = dataurl.split(','),
    mime = arr[0].match(/:(.*?);/)[1],
    bstr = atob(arr[1]),
    n = bstr.length,
    u8arr = new Uint8Array(n);
  while (n--) {
    u8arr[n] = bstr.charCodeAt(n);
  }
  return new File([u8arr], filename, { type: mime });
}

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
    if (jsonFile !== '') {
      getData();
    }
  }, [jsonFile]);

  const address = sessionStorage.getItem('address');
  // const [parts, setParts] = useState();

  const getData = async () => {
    // 씨앗 구매
    const unityjson = JSON.parse(jsonFile);
    console.log('json', unityjson);

    const buySeedPrice = 1000;
    //unityjson.player.buySeedPrice * 1000;
    console.log('buySeedPrice', buySeedPrice);

    O2Contract.methods.transferFrom(address, ADMIN_ADDRESS, buySeedPrice).send({
      from: address,
      gasPrice: '0',
    });

    // 민팅
    const harvestCropList = unityjson.player.harvests;
    console.log('crop', harvestCropList);
    // 이미지 합성
    const createNFT = async (parts: any) => {
      console.log(parts);

      const colorUrl = require(`assets/parts/background/${parts.colorUrl}.png`);
      const cropUrl = require(`assets/parts/crop/${parts.cropUrl}.png`);

      const create = await mergeImages([
        { src: colorUrl, x: 0, y: 0 },
        { src: cropUrl, x: 0, y: 0 },
      ]);
      const resultImg = dataURLtoFile(create, 'nft.png');

      return resultImg;
    };

    harvestCropList.map(async (item: any) => {
      const crop = item.type.substr(3, 2);
      const nonce = Math.floor(Math.random() * 100001);
      const formData = new FormData();

      NFTContract.methods
        .mint(Number(crop), nonce)
        .send({
          from: address,
          gasPrice: '0',
        })
        .then(async (result: any) => {
          const tokenId = result.events.Transfer.returnValues.tokenId;
          const dna = await NFTContract.methods.getNftDna(tokenId).call();
          alert(dna);
          api
            .get(`farm/mint/${dna}`, {
              headers: { Authorization: sessionStorage.getItem('accessToken') },
            })
            .then((res) => {
              createNFT(res.data).then((file) => {
                formData.append('player.harvests[0].nftImgFile', file);
                if (typeof address === 'string') {
                  formData.append('player.address', address);
                }
                formData.append('player.harvests[0].contractAddress', MARS_CONTRACT_ADDRESS);
                formData.append('player.harvests[0].cropId', '0');
                formData.append('player.harvests[0].dna', dna);
                formData.append('player.harvests[0].tokenId', tokenId);
                formData.append('player.harvests[0].type', '');
        
                api
                  .post('/farm/save', formData, {
                    headers: {
                      'Content-Type': 'multipart/form-data',
                    },
                  })
                  .then(() => console.log('성공'));
              });
            });
        });
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
