import styles from "./index.module.scss";
import BackSrc from "assets/spaceback.jpg";
import GameContainer from "./GameContainer";
import DescriptionContainer from "./DescriptionContainer";

function GameMain() {
  return (
    <>
      <div>
        <GameContainer />
        <DescriptionContainer />
        <DescriptionContainer />
      </div>
      <img className={styles.backImg} src={BackSrc} alt="back" />
    </>
  );
}
export default GameMain;
