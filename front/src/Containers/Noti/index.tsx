import React from 'react';
import styles from './index.module.scss';
import NotiCard from './NotiCard';
import { api } from 'apis/api/ApiController';
import { useEffect, useState } from 'react';

function Notification() {
  const [notiList, setNotiList] = useState([]);
  const getData = () => {
    api.get('/alarms', { headers: { Authorization: sessionStorage.getItem('accessToken') } }).then((res) => {
      setNotiList(res.data.alarms);
      console.log(res.data.alarms);
    });
  };

  useEffect(() => {
    getData();
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.notiContainer}>
        {notiList.map((item: any, index: number) => (
          <div key={item.id} className={styles.item}>
            <NotiCard
              id={item.id}
              title={item.title}
              content={item.content}
              regDt={item.regDt}
              verified={item.verified}
            />
          </div>
        ))}
      </div>
    </div>
  );
}

export default Notification;
