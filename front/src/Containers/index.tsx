import Main from "./main";
import styles from "./index.module.scss";
import BackSrc from "../assets/spaceback.jpg";
import NavBar from "../component/navbar/NavBar";

function Home() {
  return (
    <>
      <NavBar />
      <Main />
      <img className={styles.backImg} src={BackSrc} alt="back" />
    </>
  );
}
export default Home;

