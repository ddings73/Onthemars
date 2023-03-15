import React, { useEffect, useRef, useState } from 'react';
import styles from './ErrorPage.module.scss';
import lottie from 'lottie-web';
import errorBackImg from 'assets/404/404back.json';
import Planet from 'assets/404/404Planet.png';
import Arm from 'assets/404/arm.png';
import Soil from 'assets/404/soil.png';
import Spaceman from 'assets/404/spaceman.png';
import Rocket from 'assets/404/404Rocket.png';
import Spinner from 'component/utils/Spinner';
import { NavLink, useNavigate } from 'react-router-dom';

export function ErrorPage() {
  // lottie 사용
  const errorBack = useRef<HTMLDivElement>(null);
  useEffect(() => {
    if (errorBack.current) {
      lottie.loadAnimation({
        container: errorBack.current,
        renderer: 'svg',
        loop: true,
        autoplay: true,
        animationData: errorBackImg,
      });
    }
  }, []);

  // delay Navigate
  const navigate = useNavigate();
  const rocket = useRef<HTMLImageElement>(null);
  function delayAndGo(e: React.MouseEvent<HTMLElement>, path: string) {
    e.preventDefault();
    if (rocket.current) {
      rocket.current.style.transform = 'translateX(100em)';
      rocket.current.style.transition = 'transform 0.9s ease-in';
    }

    setTimeout(() => navigate(path), 1000);
  }

  // 로딩 스피너
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    setLoading(false);
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.contentContainer}>
        {loading ? (
          <Spinner />
        ) : (
          <div className={styles.contentBox}>
            <div className={styles.bannerImg}>
              <div>
                <h1>4</h1>
              </div>
              <div className={styles.planetImg}>
                <div className={styles.spaceman}>
                  {' '}
                  <img className={styles.arm} src={Arm} alt="" />
                  <img src={Soil} alt="" />
                  <img src={Spaceman} alt="" />
                  <div className={styles.shadow} />
                </div>
                <img className={styles.planet} src={Planet} alt="" />
              </div>
              <div>
                <h1>4</h1>
              </div>
            </div>
            <div className={styles.bannerTitle}>
              <p>Page Not Found</p>
            </div>
            <NavLink to="/" style={{ display: 'block', marginTop: '3%' }} onClick={(e) => delayAndGo(e, '/')}>
              <img className={styles.homeBtn} src={Rocket} alt="" ref={rocket} />
            </NavLink>
          </div>
        )}
        <div className={styles.errorBack} ref={errorBack} />
      </div>
    </div>
  );
}

export default ErrorPage;
