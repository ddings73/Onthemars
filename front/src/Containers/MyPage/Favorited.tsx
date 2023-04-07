import React, { useCallback, useEffect, useState } from 'react';
import styles from './CardList.module.scss';
import Card from 'component/nftCard/card';
import { Link, useParams } from 'react-router-dom';
import { useInView } from 'react-intersection-observer';
import { api } from 'apis/api/ApiController';
import { imgBaseURL } from 'apis/baseApi';

function Favorited() {

  const [favoritedList, setFavoritedList] = useState([]);
  const address = useParams().address;

  const [loading, setLoading] = useState(false);
  const [ref, inView] = useInView();
  const [end, setEnd] = useState<boolean>(false);
  const [page, setPage] = useState(0);
  const size = 4;

  const getData = useCallback(async () => {
    setLoading(true);
    await api.get(`/nft/${address}/favorited?page=${page}&size=${size}`).then((res) => {
      if (res.data.length !== 0) {
        setFavoritedList((prevState): any => [...prevState, ...res.data]);
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
      {favoritedList.map((item: any, index: number) => (
        <React.Fragment key={index}>
          {favoritedList.length - 1 === index ? (
            <div key={index} className={styles.cardList} ref={ref}>
              <Link to={`/nft/search/${item.transactionId}`}>
                <Card size="bigg" img_address={imgBaseURL + item.imgUrl}></Card>
              </Link>
            </div>
          ) : (
            <div key={index} className={styles.cardList}>
              <Link to={`/nft/search/${item.transactionId}`}>
                <Card size="bigg" img_address={imgBaseURL + item.imgUrl}></Card>
              </Link>
            </div>
          )}
        </React.Fragment>
      ))}
    </div>
  );
}

export default Favorited;
