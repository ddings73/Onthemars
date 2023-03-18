import GlobalFont from './styles/GlobalFont';
import Router from './routes';
import NavBar from 'component/navbar/NavBar';
import styles from './App.module.scss';
import * as React from 'react';
import scrollbar from 'smooth-scrollbar';

function App() {
  return (
    <div className={styles.appContainer}>
      <NavBar />
      <GlobalFont />
      <div className={styles.contentContainer}>
        <Router />
      </div>
    </div>
  );
}
export default App;
