import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './GameTab.module.scss';

interface TabProps{
  active: boolean;
}

function GameTab(props: TabProps) {
  const [active, setActive] = useState(props.active);
  const navigate = useNavigate();
  return (
    <div className={styles.container}>
      <div
        className={active ? styles.actab : styles.inactab}
        onClick={() => {
          setActive(!props.active);
          navigate('/game/play');
        }}
      >
        GAME PLAY
      </div>
      <div
        className={active ? styles.inactab : styles.actab}
        onClick={() => {
          setActive(!props.active);
          navigate('/game/lookfarm');
        }}
      >
        농장 구경하기
      </div>
    </div>
  );
}
export default GameTab;
