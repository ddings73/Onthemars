import React from 'react';
import styles from './index.module.scss';

import FarmContainer from './FarmContainer';
import GameTab from 'component/gameTab/GameTab';

function LookFarm() {
  return (
    <div className={styles.container}>
      <GameTab active={false}/>
      <div className={styles.text}>* 행성을 선택해 온더마스 사용자들의 농장을 구경해보세요.</div>
      <FarmContainer />
    </div>
  );
}
export default LookFarm;
