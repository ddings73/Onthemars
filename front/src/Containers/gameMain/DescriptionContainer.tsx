import React, { useEffect, useRef } from 'react';
import Lottie, { AnimationConfig } from 'lottie-web';
import animationData from '../../assets/lotties/lottieBack.json';
import maskImg from '../../assets/mask_frame.png';
import styles from './DescriptionContainer.module.scss';

export function DescriptionContainer() {
  const lottieBack = useRef<HTMLDivElement>(null);

  useEffect(() => {
    Lottie.loadAnimation({
      container: lottieBack.current!,
      renderer: 'svg',
      loop: true,
      autoplay: true,
      animationData: animationData,
    });
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.contentWrapper}>
        <div className={styles.contentLeft}>
          <h1>test Title</h1>
          <hr />
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
            labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
            nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit
            esse cillum dolore eu fugiat nulla pariatur.
          </p>
        </div>
        <div className={styles.contentRight}>
          {/* <img className={styles.lottieImgMask} src={maskImg} alt="" /> */}
          <img
            className={styles.lottieImg}
            src="https://modernfarmer.com/wp-content/uploads/2015/10/matt-damon-martian-1200x739.jpg"
            alt=""
          />
          <div className={styles.lottieImgBack} ref={lottieBack} />
        </div>
      </div>
    </div>
  );
}

export default DescriptionContainer;
