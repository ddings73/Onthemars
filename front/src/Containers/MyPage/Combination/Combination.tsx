import React, { useEffect, useState } from 'react';
import styles from './Combination.module.scss';
import SortBy from './SortBy';
import again from 'assets/combi/again.png';
import plus from 'assets/combi/plus.png';
import arrow from 'assets/combi/arrow.png';
import cardImg from 'assets/combi/tier1_wheat_2.png';
import tier2card from 'assets/combi/tier2_wheat.png';
import { ButtonDiv } from 'component/button/Button';
import Card from 'component/nftCard/card';
import { api } from 'apis/api/ApiController';

function Combination() {
  const imgBaseURL = 'https://onthemars-dev.s3.ap-northeast-2.amazonaws.com';
  const [opentier2, setOpenTier2] = useState(false);
  const [select, setSelect] = useState(false);
  const [isBlank, setIsBlank] = useState<boolean[]>([true, true]);
  const SelectCard = (index: number) => {
    setSelect(!select);
    if (isBlank[0]) {
      isBlank[0] = !isBlank[0];
      setIsBlank([...isBlank]);
    } else if (isBlank[1]) {
      isBlank[1] = !isBlank[1];
      setIsBlank([...isBlank]);
    } else if (!isBlank[0] && !isBlank[1]) {
      console.log('선택 완');
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
  const handleToRoll = () => {
    if (isBlank[0] || isBlank[1] || (isBlank[0] && isBlank[1])) {
      alert('카드를 선택해주세요');
    } else {
      setOpenTier2(!opentier2);
    }
  };

  const [nftList, setNftList] = useState([]);

  const [value, setValue] = useState('');
  useEffect(() => {
    api
      .get(`/nft/combination?cropType=${value}`, {
        headers: {
          Authorization: sessionStorage.getItem('accessToken'),
        },
      })
      .then((res) => setNftList(res.data));
  }, [value]);

  return (
    <div className={styles.container}>
      <div className={styles.top}>
        <div className={styles.text}>* 같은 작물의 Tier 1 카드 두 장을 선택해주세요.</div>
        <div className={styles.again}>
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
              <img className={styles.card} src={cardImg} alt="" onClick={SelectCard1} />
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
              <img className={styles.card} src={cardImg} alt="" onClick={SelectCard2} />
            </>
          ) : (
            <>
              <img className={styles.card} src={cardImg} style={{ visibility: 'hidden' }} alt="" />
            </>
          )}
        </div>
        <img className={styles.arrow} src={arrow} alt="" />
        <div className={styles.resultCard}>
          {opentier2 ? (
            <>
              <img className={styles.card} src={tier2card} alt="" />
            </>
          ) : (
            <>
              <img className={styles.card} src={tier2card} style={{ visibility: 'hidden' }} alt="" />
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
