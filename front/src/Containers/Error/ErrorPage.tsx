import React, { useEffect, useRef } from 'react';
import styles from './ErrorPage.module.scss';
import lottie from 'lottie-web';
import errorBackImg from 'assets/404/404back.json';

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

  return (
    <div className={styles.container}>
      <div className={styles.contentContainer}>
        <div className={styles.errorBack} ref={errorBack} />
      </div>
    </div>
  );
}

export default ErrorPage;
