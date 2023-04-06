import React, { useState } from 'react';
import styles from './index.module.scss';

import FarmContainer from './FarmContainer';
import GameTab from 'component/gameTab/GameTab';
import again from 'assets/combi/again.png';

function LookFarm() {
  const [isMyFarm, setIsMyFarm] = useState<boolean>();
  const [farmAddress, setFarmAddress] = useState<string>('');
  const handleToAnother = () => {
    setFarmAddress('');
  };
  return (
    <div className={styles.container}>
      <GameTab active={false} />
      <div className={styles.another} onClick={handleToAnother}>
        <img className={styles.icon} src={again} alt="" />
        다른 농장가기
      </div>
      <div className={styles.text}>* 행성을 선택해 온더마스 사용자들의 농장을 구경해보세요.</div>
      {farmAddress !== '' ? (
        <div className={styles.farm}>{farmAddress}</div> //여기 유니티 심기
      ) : (
        <FarmContainer
          setIsMyFarm={setIsMyFarm}
          isMyFarm={isMyFarm}
          setFarmAddress={setFarmAddress}
          farmAddress={farmAddress}
        />
      )}
    </div>
  );
}
export default LookFarm;
