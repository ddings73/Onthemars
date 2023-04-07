import { useNavigate } from 'react-router-dom';
import VideoSrc from 'assets/Longvinter.mp4';
import styles from './GameContainer.module.scss';
import { handleLogin } from 'component/button/login';

export function GameContainer() {
  const navigate = useNavigate();
  return (
    <div className={styles.gameContainer}>
      <div className={styles.contentWrapper}>
        <div className={styles.contentLeft}>
          <video autoPlay muted loop className={styles.videoBack}>
            <source src={VideoSrc} type="video/mp4" />
          </video>
          <div className={styles.divWrapper} />
        </div>
        <div className={styles.contentRight}>
          <h1>METAVERSE</h1>
          <p>
            {' '}
            메타버스 세상에서 직접 재배한 농작물로 <br />
            나만의 NFT 아이템을 수집하고, <br />
            친구들과 함께 소통해보세요!
          </p>
          {sessionStorage.getItem('address') ?
            <button type="button" className={styles.playButton} onClick={() => navigate('/game/play')}>
              PLAY
            </button> :
            <button type="button" className={styles.playButton} onClick={handleLogin}>
              PLAY
            </button>}
        </div>
      </div>
      {/* <div className={styles.scrollDown}>
        <KeyboardDoubleArrowDownIcon fontSize="large" className={styles.arrow} />
        <p>Scroll Down for Detail!</p>
        <KeyboardDoubleArrowDownIcon fontSize="large" className={styles.arrow} />
      </div> */}
    </div>
  );
}

export default GameContainer;
