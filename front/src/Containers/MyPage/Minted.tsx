import React, { useCallback, useEffect, useState } from 'react';
import styles from './CardList.module.scss';
import Card from 'component/nftCard/card';
import { Link, useParams } from 'react-router-dom';
import { useInView } from 'react-intersection-observer';
import axios from 'axios';

function Minted() {
  const baseURL = 'https://j8e207.p.ssafy.io/api/v1';
  const imgBaseURL = 'https://onthemars-dev.s3.ap-northeast-2.amazonaws.com/';

  const [mintedList, setMintedList] = useState([]);
  const address = useParams().address;

  const [loading, setLoading] = useState(false);
  const [ref, inView] = useInView();
  const [end, setEnd] = useState<boolean>(false);
  const [page, setPage] = useState(0);
  const size = 4;

  const getData = useCallback(async () => {
    setLoading(true);
    await axios({
      method: 'get',
      url: baseURL + `/nft/${address}/minted?page=${page}&size=${size}`,
    }).then((res) => {
      if (res.data.length !== 0) {
        setMintedList((prevState): any => [...prevState, ...res.data]);
      } else {
        setEnd(true);
      }
    });
    setLoading(false);
  }, [page, address]);

  useEffect(() => {
    if (!end) {
      getData();
    }
  }, [getData, end]);
  
  useEffect(() => {
    if (inView && !loading) {
      setPage((prevState) => prevState + 1);
    }
  }, [inView, loading]);

  return (
    <div className={styles.container}>
      {mintedList.map((item: any, index: number) => (
        <React.Fragment key={index}>
        {mintedList.length - 1 === index ? (
          <div key={index} className={styles.cardList} ref={ref}>
            <Link to={`${item}`}>
              <Card size="bigg" img_address={imgBaseURL + item.imgUrl}></Card>
            </Link>
          </div>
        ) : (
          <div key={index} className={styles.cardList}>
            <Link to={`${item}`}>
              <Card size="bigg" img_address={imgBaseURL + item.imgUrl}></Card>
            </Link>
          </div>
        )}
      </React.Fragment>
      ))}
    </div>
  );
}

export default Minted;
