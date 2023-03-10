import DescriptionContainer from "./DescriptionContainer";
import GameContainer from "./GameContainer";
import styles from "./index.module.scss";
import BackSrc from "assets/spaceback.jpg";

function Main() {
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
export default Main;
