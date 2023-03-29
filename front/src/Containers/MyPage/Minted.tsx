import React, { useEffect, useState } from 'react';
import styles from './CardList.module.scss';
import Card from 'component/nftCard/card';
import { Link, useParams } from 'react-router-dom';
import axios from 'axios';

function Minted() {
  const baseURL = 'https://j8e207.p.ssafy.io/api/v1';
  const imgBaseURL = 'https://onthemars-dev.s3.ap-northeast-2.amazonaws.com/';
  const [mintedList, setMintedList] = useState([]);
  const address = useParams().address;

  const getData = async () => {
    await axios({
      method: 'get',
      url: baseURL + `/nft/${address}/minted`,
    }).then((res) => {
      setMintedList(res.data);
    });
  };

  useEffect(() => {
    getData();
  }, []);

  return (
    <div className={styles.container}>
      {mintedList.map((item: any, index: number) => (
        <div key={index} className={styles.cardList}>
          <Link to={`${item}`}>
            <Card size="bigg" img_address={imgBaseURL + item.imgUrl}></Card>
          </Link>
        </div>
      ))}
    </div>
  );
}

export default Minted;
