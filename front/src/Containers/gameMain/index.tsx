import React, { useState } from 'react';
import styles from './index.module.scss';
import BackSrc from 'assets/spaceback.jpg';
import GameContainer from './GameContainer';
import DescriptionContainer from './DescriptionContainer';

function GameMain() {
  // 마우스 위치 업데이트
  const [mousePosition, setMousePosition] = useState({ x: 0, y: 0 });

  return (
    <div
      className={styles.container}
      onMouseMove={(event) => setMousePosition({ x: event.clientX, y: event.clientY })}
    >
      <div>
        <GameContainer />
        <DescriptionContainer />
      </div>
      <img
        className={styles.backImg}
        src={BackSrc}
        alt="back"
        style={{
          transform: `translate(${mousePosition.x * 0.02}px, ${mousePosition.y * 0.02}px)`,
        }}
      />
    </div>
  );
}
export default GameMain;
