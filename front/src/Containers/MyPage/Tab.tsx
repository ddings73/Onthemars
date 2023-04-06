import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import Activity from './Activity';
import Collected from './Collected';
import Combination from './Combination/Combination';
import Favorited from './Favorited';
import Minted from './Minted';
import styles from './Tab.module.scss';

function Tab() {
  const myaddress = sessionStorage.getItem('address');
  const address = useParams().address;
  
  const data = [
    {
      id: 0,
      name: 'Collected',
      content: <Collected />,
    },
    {
      id: 1,
      name: 'Minted',
      content: <Minted />,
    },
    {
      id: 2,
      name: 'Favorited',
      content: <Favorited />,
    },
    {
      id: 3,
      name: 'Activity',
      content: <Activity />,
    },
    {
      id: 4,
      name: 'Combination',
      content: <Combination />,
    },
  ];
  const [index, setIndex] = useState(0);
  return (
    <div className={styles.container}>
      <div className={styles.tabMenu}>
        {myaddress !== address
          ? data
              .filter((item) => item.id !== 4)
              .map((item) => (
                <div
                  key={item.id}
                  className={index === item.id ? styles.active : styles.inactive}
                  onClick={() => setIndex(item.id)}
                >
                  {item.name}
                </div>
              ))
          : data.map((item) => (
              <div
                key={item.id}
                className={index === item.id ? styles.active : styles.inactive}
                onClick={() => setIndex(item.id)}
              >
                {item.name}
              </div>
            ))}
      </div>
      {data
        .filter((item) => index === item.id)
        .map((item) => (
          <div key={item.id} className={styles.tabContent}>
            {item.content}
          </div>
        ))}
    </div>
  );
}

export default Tab;
