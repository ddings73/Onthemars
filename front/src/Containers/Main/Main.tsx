import { NavLink } from "react-router-dom";
import styles from "./Main.module.scss";
import Sprout from "assets/sprout.png"

export function Main() {
  return (
    <div className={styles.container}>
      {/* <img className={styles.mainImg} alt="메인이미지" /> */}
      <div className={styles.buttonContainer}>
        <div className={styles.buttonDiv}>
          <NavLink to="/">
            <button className={styles.button}>
              <p>NFT 둘러보기</p>
              <img className={styles.sproutImg} src={Sprout} alt="새싹" />
            </button>
          </NavLink>
        </div>
        <div className={styles.buttonDiv}>
          <img className={styles.sproutImg} src={Sprout} alt="새싹" />
          <NavLink to="/">
            <button className={styles.button}>
              내 농장가기
            </button>
          </NavLink>
        </div>
      </div>
    </div>
  );
}

export default Main;
