import React, { useEffect, useState } from 'react';
import styles from './CardList.module.scss';
import Card from 'component/nftCard/card';
import { Link, useParams } from 'react-router-dom';
import axios from 'axios';

function Collected() {
  const baseURL = 'https://j8e207.p.ssafy.io/api/v1';
  const imgBaseURL = 'https://onthemars-dev.s3.ap-northeast-2.amazonaws.com/';
  const [collectedList, setCollectedList] = useState([]);
  const address = useParams().address;

  const getData = async () => {
    await axios({
      method: 'get',
      url: baseURL + `/nft/${address}/collected`,
    }).then((res) => {
      setCollectedList(res.data);
    });
  };
  
  useEffect(() => {
    getData();
  }, []);

  return (
    <div className={styles.container}>
      {collectedList.map((item:any, index:number) => (
        <div key={index} className={styles.cardList}>
          <Link to={`${item}`}>
            <Card size="bigg" img_address={imgBaseURL+item.imgUrl}></Card>
          </Link>
        </div>
      ))}
    </div>
  );
}

export default Collected;
