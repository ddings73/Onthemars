import { useEffect, useRef, useState } from 'react';
import styles from './index.module.scss';

import FarmContainer from './FarmContainer';
import GameTab from 'component/gameTab/GameTab';
import again from 'assets/combi/again.png';
import { Unity, useUnityContext } from 'react-unity-webgl';

function LookFarm() {
  const [isMyFarm, setIsMyFarm] = useState<boolean>();
  const [farmAddress, setFarmAddress] = useState<string>('');
  const handleToAnother = () => {
    setFarmAddress('');
  };

  // keyboardInput
  const canvasRef = useRef<HTMLCanvasElement>(null);
  useEffect(() => {
    const recaptureInputAndFocus = (): void => {
      const canvas = canvasRef.current;
      if (canvas) {
        canvas.setAttribute('tabindex', '1');
        canvas.focus();
      } else {
        setTimeout(recaptureInputAndFocus, 100);
      }
    };

    recaptureInputAndFocus();
  }, []);
  // REACT -> UNITY DATA POST

  const addString = farmAddress + '|' + 0 + '|' + isMyFarm;
  function handleUserData() {
    sendMessage('GameManager', 'GetAddress', addString);
  }
  useEffect(() => {
    handleUserData();
  });
  const {
    unityProvider,
    sendMessage,
    // addEventListener,
    // removeEventListener,
    UNSAFE__detachAndUnloadImmediate: detachAndUnloadImmediate,
    isLoaded,
    loadingProgression,
  } = useUnityContext({
    loaderUrl: '/Build/Build.loader.js',
    dataUrl: '/Build/Build.data',
    frameworkUrl: '/Build/Build.framework.js',
    codeUrl: '/Build/Build.wasm',
  });

  return (
    <div className={styles.container}>
      <GameTab active={false} />
      <div className={styles.another} onClick={handleToAnother}>
        <img className={styles.icon} src={again} alt="" />
        다른 농장가기
      </div>
      <div className={styles.text}>* 행성을 선택해 온더마스 사용자들의 농장을 구경해보세요.</div>
      {farmAddress !== '' ? (
        <div className={styles.farm}>
          <div
            className={styles.loading}
            style={{
              position: 'absolute',
              marginLeft: 'auto',
              marginRight: 'auto',
              left: '0',
              right: '0',
              textAlign: 'center',
              color: 'white',
              fontWeight: '700',
              fontSize: '1.5vw',
            }}
          >
            {!isLoaded && <p>온더마스 게임 불러오는 중... {Math.round(loadingProgression * 100)}%</p>}
          </div>
          <Unity
            className={styles.unityContainer}
            unityProvider={unityProvider}
            ref={canvasRef}
            style={{ marginTop: '2%', width: '110%', height: '110%' }}
          />
        </div> //여기 유니티 심기
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
