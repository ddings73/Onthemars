import React, { useCallback, useEffect, useRef, useState } from 'react';
import styles from './UnityContainer.module.scss';
import { Unity, useUnityContext } from 'react-unity-webgl';
import mergeImages from 'merge-images';
import { O2Contract, NFTContract, MARS_CONTRACT_ADDRESS, ADMIN_ADDRESS } from 'apis/ContractAddress';
import { api } from 'apis/api/ApiController';
import { resolve } from 'path';

// mergeimage 결과 file로 변환하는 함수
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
  //주소
  const address = sessionStorage.getItem('address');
  //잔액
  const [balance, setBalance] = useState();
  const getBalance = async () => {
    setBalance((await O2Contract.methods.balanceOf(address).call()).slice(0, -2));
  };
  useEffect(() => {
    getBalance();
    console.log(balance);
  }, [jsonFile, balance]);
  const addString = address + '|' + balance;

  // keyboardInput
  const canvasRef = useRef<HTMLCanvasElement>(null);
  useEffect(() => {
    const recaptureInputAndFocus = (): void => {
      const canvas = canvasRef.current;
      if (canvas) {
        canvas.setAttribute('tabindex', '1');
        canvas.focus();
      }
    };

    recaptureInputAndFocus();
  }, []);
  // REACT -> UNITY DATA POST
  function handleUserData() {
    sendMessage('GameManager', 'GetAddress', addString);
    //'0x2576db621b464675d3f4ea74b1eb955f56cfe1b4|1000'
    console.log(addString);
  }
  useEffect(() => {
    handleUserData();
  });
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

  const getData = async () => {
    const unityjson = JSON.parse(jsonFile);
    console.log('json', unityjson);

    // 수확한 작물 개수만큼 O2 코인 넣어주기
    const harvestCropList = unityjson.player.harvests;
    const harvestCnt = harvestCropList.length;
    O2Contract.methods.mintToMember(address, harvestCnt * 500).send({
      from: address,
      gasPrice: '0',
    });

    // 씨앗 구매
    // const buySeedPrice = 1000;
    const buySeedPrice = unityjson.player.buySeedCnt * 10;
    const priceToO2 = buySeedPrice * 100; //이값 보내기
    console.log('buySeedPrice', priceToO2);

    O2Contract.methods.transferFrom(address, ADMIN_ADDRESS, priceToO2).send({
      from: address,
      gasPrice: '0',
    });

    // 민팅
    console.log('crop', harvestCropList);

    // 이미지 합성 함수
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

    let formData = new FormData();

    // formData가 append 됐다가.. 안됐다가..
    const promiseArray = harvestCropList.map((item: any, index: number) => {
      const crop = item.type.substr(3, 2);
      const nonce = Math.floor(Math.random() * 100001);

      return new Promise((resolve, reject) => {
        NFTContract.methods
          .mint(Number(crop), nonce)
          .send({
            from: address,
            gasPrice: '0',
          })
          .then(async (result: any) => {
            const tokenId = result.events.Transfer.returnValues.tokenId;
            const dna = await NFTContract.methods.getNftDna(tokenId).call();
            console.log(dna);

            //dna로 이미지받아서
            api
              .get(`farm/mint/${dna}`, {
                headers: { Authorization: sessionStorage.getItem('accessToken') },
              })
              .then((res) => {
                // 이미지 합성해서 바로 formData에 file추가
                createNFT(res.data).then((file) => {
                  //player.harvests
                  formData.append(`player.harvests[${index}].nftImgFile`, file);
                  formData.append(
                    `player.harvests[${index}].cropId`,
                    unityjson.player.harvests[index].cropId,
                  );
                  formData.append(`player.harvests[${index}].contractAddress`, MARS_CONTRACT_ADDRESS);
                  formData.append(`player.harvests[${index}].dna`, dna);
                  formData.append(`player.harvests[${index}].tokenId`, tokenId);
                  formData.append(`player.harvests[${index}].type`, unityjson.player.harvests[index].type);
                });
                console.log(formData, '폼데이터 추가됨');
              })
              .then((res) => {
                console.log('하나성공');
                console.log(formData);
                resolve(res);
              });
          });
      });
    });

    console.log(promiseArray, 'promiesAll Array 💫💫💫💫💫💫');
    Promise.all(promiseArray).then(() => {
      if (typeof address === 'string') {
        formData.append('player.address', address);
      }
      formData.append('player.nickname', unityjson.player.nickname);
      formData.append('player.buySeedCnt', unityjson.player.buySeedCnt);
      formData.append('player.buySeedPrice', priceToO2 + '');
      formData.append('player.curSeedCnt', unityjson.player.curSeedCnt);

      //cropList
      unityjson.cropList.map((item: any, idx: number) => {
        formData.append(`cropList[${idx}].cropId`, unityjson.cropList[idx].cropId);
        formData.append(`cropList[${idx}].type`, unityjson.cropList[idx].type);
        formData.append(`cropList[${idx}].state`, unityjson.cropList[idx].state);
        formData.append(`cropList[${idx}].potNum`, unityjson.cropList[idx].potNum);
        formData.append(`cropList[${idx}].cooltime`, unityjson.cropList[idx].cooltime);
        formData.append(`cropList[${idx}].isWaterd`, unityjson.cropList[idx].isWaterd);
        formData.append(`cropList[${idx}].isPlanted`, unityjson.cropList[idx].isPlanted);
        formData.append(`cropList[${idx}].isTimeDone`, unityjson.cropList[idx].isTimeDone);
      });
      // formData 전송
      api
        .post('/farm/save', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then(() => console.log('성공'));
    });
    //player
  };

  return (
    <>
      <div className={styles.container}>
        <Unity
          className={styles.unityContainer}
          unityProvider={unityProvider}
          ref={canvasRef}
          style={{ marginTop: '2%', width: '110%', height: '110%' }}
        />
        <h1>{jsonFile}</h1>
      </div>
    </>
  );
}

export default UnityContainer;
