import GlobalFont from './styles/GlobalFont';
import Router from './routes';
import NavBar from 'component/navbar/NavBar';
import styles from './App.module.scss'

function App() {
  return (
    <div className={styles.appContainer}>
      <NavBar />
      <GlobalFont />
      <Router />
    </div>
  );
}
export default App;

