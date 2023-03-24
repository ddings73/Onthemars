import React, { useState } from 'react';
import Activity from './Activity';
import Collected from './Collected';
import Combination from './Combination';
import Favorited from './Favorited';
import Minted from './Minted';
import styles from './Tab.module.scss';

function Tab() {
  const data = [
    {
      id: 0,
      name: 'Collected',
      content: <Collected />,
    },
    {
      id: 1,
      name: 'Minted',
      content:<Minted/>
    },
    {
      id: 2,
      name: 'Favorited',
      content:<Favorited/>
    },
    {
      id: 3,
      name: 'Activity',
      content:<Activity/>
    },
    {
      id: 4,
      name: 'Combination',
      content:<Combination/>
    },
  ];
  const [index, setIndex] = useState(0);
  return (
    <div className={styles.container}>
      <div className={styles.tabMenu}>
        {data.map((item) => (
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
          <div className={styles.tabContent}>{item.content}</div>
        ))}
    </div>
  );
}

export default Tab;
