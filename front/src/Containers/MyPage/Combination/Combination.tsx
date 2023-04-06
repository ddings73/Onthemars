import React, { useEffect, useState } from 'react';
import styles from './Combination.module.scss';
import SortBy from './SortBy';
import again from 'assets/combi/again.png';
import plus from 'assets/combi/plus.png';
import arrow from 'assets/combi/arrow.png';
import cardImg from 'assets/combi/tier1_wheat_2.png';
import { ButtonDiv } from 'component/button/Button';
import Card from 'component/nftCard/card';
import { api } from 'apis/api/ApiController';
import { NFTContract } from 'apis/ContractAddress';
import mergeImages from 'merge-images';
import Swal from 'sweetalert2';
import { imgBaseURL } from 'apis/baseApi';

export type list = {
  imgUrl: string;
  tokenId: number;
  transactionId: number;
  //transactionId, tokenId, contractAddress
};

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

function Combination() {
  const [nftList, setNftList] = useState<list[]>([]);
  const address = sessionStorage.getItem('address');
  const [opentier2, setOpenTier2] = useState('');
  const [select, setSelect] = useState(false);
  const [isBlank, setIsBlank] = useState<boolean[]>([true, true]);
  const [card1, setCard1] = useState('');
  const [card2, setCard2] = useState('');
  const [card1Info, setCard1Info] = useState<list>();
  const [card2Info, setCard2Info] = useState<list>();
  const SelectCard = (index: number) => {
    setSelect(!select);
    if (isBlank[0]) {
      isBlank[0] = !isBlank[0];
      setIsBlank([...isBlank]);
      setCard1(imgBaseURL + nftList[index].imgUrl);
      setCard1Info(nftList[index]);
    } else if (isBlank[1]) {
      isBlank[1] = !isBlank[1];
      setIsBlank([...isBlank]);
      setCard2(imgBaseURL + nftList[index].imgUrl);
      setCard2Info(nftList[index]);
    } else if (!isBlank[0] && !isBlank[1]) {
      Swal.fire('선택 불가', '선택된 카드를 누르고 취소한 뒤, 다시 선택해주세요.', 'error');
    }
  };
  const SelectCard1 = () => {
    setSelect(!select);
    isBlank[0] = !isBlank[0];
    setIsBlank([...isBlank]);
  };
  const SelectCard2 = () => {
    setSelect(!select);
    isBlank[1] = !isBlank[1];
    setIsBlank([...isBlank]);
  };

  const createNFT = async (parts: any) => {
    const colorUrl = require(`assets/parts/background/${parts.bgUrl}.png`);
    const cropUrl = require(`assets/parts/crop/${parts.cropTypeUrl}.png`);
    const headgearUrl = require(`assets/parts/headgear/${parts.headGearUrl}.png`);
    const eyesUrl = require(`assets/parts/eye/${parts.eyesUrl}.png`);
    const mouthUrl = require(`assets/parts/mouth/${parts.mouthUrl}.png`);

    const create = await mergeImages([
      { src: colorUrl, x: 0, y: 0 },
      { src: cropUrl, x: 0, y: 0 },
      { src: headgearUrl, x: 0, y: 0 },
      { src: eyesUrl, x: 0, y: 0 },
      { src: mouthUrl, x: 0, y: 0 },
    ]);
    setOpenTier2(create);
    const resultImg = dataURLtoFile(create, 'nft.png');

    return resultImg;
  };

  const [loadingCombi, setLoadingCombi] = useState<boolean>(false);
  const combiToast = Swal.mixin({
    toast: true,
    showConfirmButton: false,
    timerProgressBar: true,
    didOpen: (toast) => {
      Swal.showLoading();
      if (!loadingCombi) Swal.stopTimer();
      toast.addEventListener('mouseenter', Swal.stopTimer);
      toast.addEventListener('mouseleave', Swal.resumeTimer);
    },
    willClose: () => { },
  });
  if (loadingCombi) {
    combiToast.fire({
      title: 'DNA 생성중 ꒰ “̮ ꒱',
      text: '잠시만 기다려주세요!',
    });
  }
  const handleToRoll = () => {
    if (isBlank[0] || isBlank[1] || (isBlank[0] && isBlank[1])) {
      Swal.fire('카드를 먼저 선택해주세요.', '', 'warning');
    } else {
      setLoadingCombi(true);
      const nonce = Math.floor(Math.random() * 100001);
      const formData = new FormData();
      NFTContract.methods
        .combNFT(card1Info?.tokenId, card2Info?.tokenId, nonce)
        .send({
          from: address,
          gasPrice: '0',
        })
        .then(async (result: any) => {
          const tokenId = parseInt(result.events.Transfer[2].returnValues.tokenId);
          const dna = await NFTContract.methods.getNftDna(tokenId).call();
          api
            .post(
              '/nft/history/fusion',
              {
                newNft: {
                  dna,
                  tokenId,
                },
                transactionId1: card1Info?.transactionId,
                transactionId2: card2Info?.transactionId,
              },
              {
                headers: {
                  Authorization: sessionStorage.getItem('accessToken'),
                },
              },
            )
            .then((res) => {
              setLoadingCombi(false);
              if (res.data.isDuplicated) {
                Swal.fire('민팅 실패', '이미 민팅된 카드입니다! ( ᵕ̩̩ㅅᵕ̩̩ )', 'error');
              } else {
                createNFT(res.data).then((file) => {
                  formData.append('nftImgFile', file);

                  api
                    .post(`/nft/history/fusion/${res.data.transactionId}`, formData, {
                      headers: {
                        Authorization: sessionStorage.getItem('accessToken'),
                        'Content-Type': 'multipart/form-data',
                      },
                    })
                    .then(() => {
                      Swal.fire('조합 성공', '꒰⑅◡̎ ꒱𓈒𓏸', 'success');
                    });
                });
              }
            });
        });
    }
  };

  const [value, setValue] = useState('');
  useEffect(() => {
    if (value !== '') {
      api
        .get(`/nft/combination?cropType=${value}`, {
          headers: {
            Authorization: sessionStorage.getItem('accessToken'),
          },
        })
        .then((res) => setNftList(res.data));
    }
  }, [value]);

  const handleToReset = () => {
    setIsBlank([true, true]);
    setOpenTier2('');
  };

  return (
    <div className={styles.container}>
      <div className={styles.top}>
        <div className={styles.text}>* 같은 작물의 Tier 1 카드 두 장을 선택해주세요.</div>
        <div className={styles.again} onClick={handleToReset}>
          <img className={styles.icon} src={again} alt="" />
          다른 카드도 합성하기
        </div>
      </div>
      <div className={styles.middletop}>
        <div className={styles.select}>
          <SortBy setValue={setValue} value={value} />
        </div>
        <div className={styles.rollBtn} onClick={handleToRoll}>
          <ButtonDiv text={"Let's Roll!"} />
        </div>
      </div>
      <div className={styles.combi}>
        <div className={styles.card1}>
          {!isBlank[0] ? (
            <>
              <img className={styles.card} src={card1} alt="" onClick={SelectCard1} />
            </>
          ) : (
            <>
              <img className={styles.card} src={cardImg} style={{ visibility: 'hidden' }} alt="" />
            </>
          )}
        </div>
        <img className={styles.plus} src={plus} alt="" />
        <div className={styles.card2}>
          {!isBlank[1] ? (
            <>
              <img className={styles.card} src={card2} alt="" onClick={SelectCard2} />
            </>
          ) : (
            <>
              <img className={styles.card} src={cardImg} style={{ visibility: 'hidden' }} alt="" />
            </>
          )}
        </div>
        <img className={styles.arrow} src={arrow} alt="" />
        <div className={styles.resultCard}>
          {opentier2 !== '' ? (
            <>
              <img className={styles.card} src={opentier2} alt="" />
            </>
          ) : (
            <>
              <img className={styles.card} src={opentier2} style={{ visibility: 'hidden' }} alt="" />
            </>
          )}
        </div>
      </div>
      <div className={styles.cardsList}>
        {nftList.map((item: any, index: number) => (
          <div
            key={index}
            className={styles.cardList}
            onClick={() => {
              SelectCard(index);
            }}
          >
            {/* <Link to={`${item}`}> */}
            <Card size="bigg" img_address={imgBaseURL + item.imgUrl}></Card>
            {/* </Link> */}
          </div>
        ))}
      </div>
    </div>
  );
}

export default Combination;
