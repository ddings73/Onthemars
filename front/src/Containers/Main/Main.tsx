import { useRef } from 'react';
import { NavLink } from 'react-router-dom';
import styles from './Main.module.scss';
import Sprout from 'assets/sprout.png';
import Planet from 'assets/planet.png';
import BackSrc from 'assets/spaceback.jpg';
import { handleLogin } from 'component/button/login';

export function Main() {
  // 새싹 제어 텍스트
  const btnImg = useRef<HTMLImageElement>(null);
  const sproutImg = useRef<HTMLImageElement>(null);
  function textHoverHandler() {
    if (btnImg.current && sproutImg.current) {
      btnImg.current.style.transform = 'rotate(80deg)';
      sproutImg.current.style.top = '20%';
    }
  }
  function textLeaverHandler() {
    if (btnImg.current && sproutImg.current) {
      btnImg.current.style.transform = 'rotate(0deg)';
      sproutImg.current.style.top = '70%';
    }
  }
  const btnImg2 = useRef<HTMLImageElement>(null);
  const sproutImg2 = useRef<HTMLImageElement>(null);
  function textHoverHandler2() {
    if (btnImg2.current && sproutImg2.current) {
      btnImg2.current.style.transform = 'rotate(-80deg)';
      sproutImg2.current.style.top = '20%';
    }
  }
  function textLeaverHandler2() {
    if (btnImg2.current && sproutImg2.current) {
      btnImg2.current.style.transform = 'rotate(0deg)';
      sproutImg2.current.style.top = '70%';
    }
  }
  // 마우스 위치 업데이트
  const mousePosition = { x: 0, y: 0 }


  return (
    <div className={styles.container}>
      <img
        className={styles.backImg}
        src={BackSrc}
        alt="back"
        style={{
          transform: `translate(${mousePosition.x * 0.04}px, ${mousePosition.y * 0.04}px)`,
        }}
      />
      <div className={styles.buttonContainer}>
        <div className={styles.buttonDiv}>
          {sessionStorage.getItem('address') ?
            <NavLink to="/game/main">
              <div>
                <div
                  className={styles.buttonText}
                  onMouseOver={() => textHoverHandler()}
                  onMouseLeave={() => textLeaverHandler()}
                >
                  게임 플레이
                </div>
                <img className={styles.buttonImg} alt="" src={Planet} ref={btnImg} />
                <img className={styles.sproutImg} src={Sprout} alt="새싹" ref={sproutImg} />
              </div>
            </NavLink> :
            <div onClick={handleLogin}>
              <div>
                <div
                  className={styles.buttonText}
                  onMouseOver={() => textHoverHandler()}
                  onMouseLeave={() => textLeaverHandler()}
                >
                  게임 플레이
                </div>
                <img className={styles.buttonImg} alt="" src={Planet} ref={btnImg} />
                <img className={styles.sproutImg} src={Sprout} alt="새싹" ref={sproutImg} />
              </div>
            </div>}
        </div>
        <div className={styles.buttonDiv}>
          <div>
            <NavLink to="/nft">
              <p
                className={styles.buttonText}
                onMouseOver={() => textHoverHandler2()}
                onMouseLeave={() => textLeaverHandler2()}
              >
                NFT 둘러보기
              </p>
            </NavLink>
            <img className={styles.buttonImg} alt="" src={Planet} ref={btnImg2} />
            <img className={styles.sproutImg} src={Sprout} alt="새싹" ref={sproutImg2} />
          </div>
        </div>
      </div>
    </div >
  );
}

export default Main;
