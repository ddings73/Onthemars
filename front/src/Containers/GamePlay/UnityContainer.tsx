import React, { useCallback, useEffect, useRef, useState } from 'react';
import styles from './UnityContainer.module.scss';
import { Unity, useUnityContext } from 'react-unity-webgl';
import mergeImages from 'merge-images';
import { O2Contract, NFTContract, MARS_CONTRACT_ADDRESS, ADMIN_ADDRESS } from 'apis/ContractAddress';
import { api } from 'apis/api/ApiController';
import Swal from 'sweetalert2';

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
  }, [jsonFile, balance]);

  const addString = address + '|' + balance + '|' + true;

  // keyboardInput
  const canvasRef = useRef<HTMLCanvasElement>(null);
  useEffect(() => {
    const recaptureInputAndFocus = (): void => {
      const canvas = canvasRef.current;
      if (canvas) {
        canvas.setAttribute('tabindex', '1');
        canvas.focus();
      } else {
        setTimeout(recaptureInputAndFocus, 100);
      }
    };

    recaptureInputAndFocus();
  }, []);
  // REACT -> UNITY DATA POST
  function handleUserData() {
    sendMessage('GameManager', 'GetAddress', addString);
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
    isLoaded,
    loadingProgression,
  } = useUnityContext({
    loaderUrl: '/Build/Build.loader.js',
    dataUrl: '/Build/Build.data',
    frameworkUrl: '/Build/Build.framework.js',
    codeUrl: '/Build/Build.wasm',
  });

  const reactSetData = useCallback((data: any) => {
    setJsonFile(data);
  }, []);

  useEffect(() => {
    addEventListener('CallReact', reactSetData);
    return () => {
      removeEventListener('CallReact', reactSetData);
    };
  }, [reactSetData]);

  useEffect(() => {
    if (jsonFile !== '') {
      getData();
    }
  }, [jsonFile]);

  const [loadingMint, setLoadingMint] = useState<boolean>(false);
  const mintToast = Swal.mixin({
    toast: true,
    showConfirmButton: false,
    timerProgressBar: true,
    didOpen: (toast) => {
      Swal.showLoading();
      if (!loadingMint) Swal.stopTimer();
      toast.addEventListener('mouseenter', Swal.stopTimer);
      toast.addEventListener('mouseleave', Swal.resumeTimer);
    },
    willClose: () => {},
  });
  if (loadingMint) {
    mintToast.fire({
      title: 'DNA 생성중 ꒰ “̮ ꒱',
      text: '잠시만 기다려주세요!',
    });
  }

  const getData = async () => {
    const unityjson = JSON.parse(jsonFile);

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

    O2Contract.methods.transferFrom(address, ADMIN_ADDRESS, priceToO2).send({
      from: address,
      gasPrice: '0',
    });

    // 민팅
    // 이미지 합성 함수
    const createNFT = async (parts: any) => {
      const colorUrl = require(`assets/parts/background/${parts.colorUrl}.png`);
      const cropUrl = require(`assets/parts/crop/${parts.cropUrl}.png`);

      const create = await mergeImages([
        { src: colorUrl, x: 0, y: 0 },
        { src: cropUrl, x: 0, y: 0 },
      ]);
      const resultImg = dataURLtoFile(create, 'nft.png');

      return resultImg;
    };

    setLoadingMint(true);
    //수확된 작물 개수만큼 map돌리면서 민팅
    harvestCropList.map(async (item: any, index: number) => {
      const crop = item.type.substr(3, 2);
      const nonce = Math.floor(Math.random() * 100001);
      const formData = new FormData();

      //민팅
      NFTContract.methods
        .mint(Number(crop), nonce)
        .send({
          from: address,
          gasPrice: '0',
        })
        .then(async (result: any) => {
          const tokenId = result.events.Transfer.returnValues.tokenId;
          const dna = await NFTContract.methods.getNftDna(tokenId).call();
          //dna로 이미지받아서
          api
            .get(`farm/mint/${dna}`, {
              headers: { Authorization: sessionStorage.getItem('accessToken') },
            })
            .then((res) => {
              // 이미지 합성해서 바로 formData에 file추가
              createNFT(res.data).then((file) => {
                //player.harvests
                formData.append(`player.harvests[0].nftImgFile`, file);
                formData.append(`player.harvests[0].cropId`, unityjson.player.harvests[index].cropId);
                formData.append(`player.harvests[0].contractAddress`, MARS_CONTRACT_ADDRESS);
                formData.append(`player.harvests[0].dna`, dna);
                formData.append(`player.harvests[0].tokenId`, tokenId);
                formData.append(`player.harvests[0].type`, unityjson.player.harvests[index].type);

                //player
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
                  .then(() => {
                    setLoadingMint(false);
                    Swal.fire('민팅 완료', '', 'success');
                  });
              });
            });
        });
    });

    if (harvestCropList.length === 0) {
      const formData = new FormData();

      //player
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

      api.post('/farm/save', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
    }
  };

  return (
    <>
      <div className={styles.container}>
        <div className={styles.loading}>
          {!isLoaded && <p>온더마스 게임 불러오는 중... {Math.round(loadingProgression * 100)}%</p>}
        </div>
        <Unity
          className={styles.unityContainer}
          unityProvider={unityProvider}
          ref={canvasRef}
          style={{ marginTop: '2%', width: '110%', height: '110%' }}
        />
      </div>
    </>
  );
}

export default UnityContainer;
