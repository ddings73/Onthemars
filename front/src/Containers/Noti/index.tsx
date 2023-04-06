import React from 'react';
import styles from './index.module.scss';
import NotiCard from './NotiCard';
import { api } from 'apis/api/ApiController';
import { useCallback, useEffect, useState } from 'react';
import { useInView } from 'react-intersection-observer';

function Notification() {
  const [notiList, setNotiList] = useState([]);

  const [loading, setLoading] = useState(false);
  const [ref, inView] = useInView();
  const [end, setEnd] = useState<boolean>(false);
  const [page, setPage] = useState(0);
  const size = 4;
  const getData = useCallback(async () => {
    setLoading(true);
    api
      .get(`/alarms?page=${page}&size=${size}`, {
        headers: { Authorization: sessionStorage.getItem('accessToken') },
      })
      .then((res) => {
        if (res.data.length !== 0) {
          setNotiList(res.data.alarms);
        } else {
          setEnd(true);
        }
      });
    setLoading(false);
  }, [page]);

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
      <div className={styles.notiContainer}>
        {notiList.length === 0 ? (
          <div className={styles.noAlarm}>알림없음</div>
        ) : (
          notiList.map((item: any, index: number) => (
            <React.Fragment key={item.id}>
              {notiList.length - 1 === item.id ? (
                <div key={item.id} className={styles.item} ref={ref}>
                  <NotiCard
                    id={item.id}
                    title={item.title}
                    content={item.content}
                    regDt={item.regDt}
                    verified={item.verified}
                  />
                </div>
              ) : (
                <div key={item.id} className={styles.item}>
                  <NotiCard
                    id={item.id}
                    title={item.title}
                    content={item.content}
                    regDt={item.regDt}
                    verified={item.verified}
                  />
                </div>
              )}
            </React.Fragment>
          ))
        )}
      </div>
    </div>
  );
}

export default Notification;
