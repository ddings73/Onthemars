import styles from './index.module.scss';
import GameTab from 'component/gameTab/GameTab';
import UnityContainer from './UnityContainer';


function GamePlay() {
  return (
    <div className={styles.container}>
      <GameTab active={true} />
      <UnityContainer />
    </div>
  );
}
export default GamePlay;
