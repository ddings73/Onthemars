import React, { useEffect, useRef, useState } from 'react';
import Lottie, { AnimationConfig } from 'lottie-web';
import animationData from '../../assets/lotties/lottieBack.json';
import maskImg from '../../assets/mask_frame.png';
import styles from './DescriptionContainer.module.scss';

export function DescriptionContainer() {
  // 스크롤 위치 입력 변수
  const [scrollPosition, setScrollPosition] = useState(0);
  const updateScroll = () => {
    setScrollPosition(window.scrollY || document.documentElement.scrollTop);
  };

  // lottie Animation
  const lottieDesImg = useRef<HTMLImageElement>(null);

  // Scroll Animation
  const desTextTitle = useRef<HTMLHeadingElement>(null);
  const desTextContent = useRef<HTMLParagraphElement>(null);
  // Page 요소 스크롤 스타일

  useEffect(() => {
    if (lottieDesImg.current) {
      const maxScroll = 1000;
      const startScale = 0.7;
      const endScale = 1.2;
      const range = endScale - startScale;

      let newScale = startScale + (range * scrollPosition) / maxScroll;
      if (scrollPosition >= maxScroll) {
        newScale = endScale;
      }

      lottieDesImg.current.style.transform = `scale(${newScale})`;
    }

    if (desTextTitle.current && desTextContent.current) {
      const maxScroll = 1000;
      const startOpacity = 0;
      const endOpacity = 1;
      const startSpace = 0;
      const endSpace = 15;
      const range = endOpacity - startOpacity;
      const spaceRange = endSpace - startSpace;

      let newOpacity = startOpacity + (range * scrollPosition) / maxScroll;
      let newSpace = startSpace + (spaceRange * scrollPosition) / maxScroll;
      if (scrollPosition >= maxScroll) {
        newOpacity = endOpacity;
        newSpace = endSpace;
      }
      if (scrollPosition >= maxScroll - 400) {
        desTextContent.current.style.opacity = `1`;
        desTextContent.current.style.transform = 'translateX(0)';
      } else {
        desTextContent.current.style.opacity = `0`;
        desTextContent.current.style.transform = 'translateX(-30px)';
      }

      desTextTitle.current.style.opacity = newOpacity.toString();
      desTextTitle.current.style.letterSpacing = `${newSpace}px`;
    }
  }, [scrollPosition]);

  useEffect(() => {
    window.addEventListener('scroll', updateScroll);
    console.log(scrollPosition);
  });

  return (
    <div className={styles.container}>
      <div className={styles.contentWrapper}>
        <div className={styles.contentLeft}>
          <h1 ref={desTextTitle}>test Title</h1>
          <hr />
          <p ref={desTextContent}>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
            labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
            nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit
            esse cillum dolore eu fugiat nulla pariatur.
          </p>
        </div>
        <div className={styles.contentRight}>
          <img
            className={styles.lottieImg}
            src="https://modernfarmer.com/wp-content/uploads/2015/10/matt-damon-martian-1200x739.jpg"
            alt=""
            ref={lottieDesImg}
          />
        </div>
      </div>
    </div>
  );
}

export default DescriptionContainer;
