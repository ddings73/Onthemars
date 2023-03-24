import React, { useState, useRef, useEffect } from 'react';
import styles from './NavBar.module.scss';
import Logo from 'assets/logo.png';
import SearchBox from './SearchBox';
import NotificationsIcon from '@mui/icons-material/Notifications';
import MenuIcon from '@mui/icons-material/Menu';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { NavLink } from 'react-router-dom';
import Dropdown from './Dropdown';

export function NavBar() {
  // 스크롤 위치 입력 변수
  const [scrollPosition, setScrollPosition] = useState(0);
  const updateScroll = () => {
    setScrollPosition(window.scrollY || document.documentElement.scrollTop);
  };

  // 사이드 메뉴바 변수
  const navBarRef = useRef<HTMLDivElement>(null);
  const menuBar = useRef<HTMLDivElement>(null);
  const menuBarContent = useRef<HTMLDivElement>(null);
  const [sideToggle, setSideToggle] = useState(false);

  // 사이드 메뉴바 토글 키 설정
  function menuToggle() {
    setSideToggle(!sideToggle);
  }

  // 사이드 메뉴바 토글 키 감시
  useEffect(() => {
    if (menuBar.current !== null && menuBarContent.current !== null) {
      if (sideToggle) {
        if (window.innerWidth < 600) {
          menuBar.current.style.width = '90%';
        } else {
          menuBar.current.style.width = '20%';
        }

        menuBarContent.current.style.opacity = '1';
      } else {
        menuBar.current.style.width = '0%';
        menuBarContent.current.style.opacity = '0';
      }
    }
  }, [sideToggle]);

  // Navbar 스크롤 좌표 감시
  useEffect(() => {
    if (navBarRef.current !== null) {
      if (scrollPosition >= 300) {
        navBarRef.current.style.backgroundColor = '#151515';
      } else {
        navBarRef.current.style.backgroundColor = 'transparent';
      }
    }
  }, [scrollPosition]);

  useEffect(() => {
    window.addEventListener('scroll', updateScroll);
  });

  const [view, setView] = useState<boolean>(false);
  const tabRef = useRef<HTMLUListElement>(null); // 특정 영역 외 클릭시 드롭다운 메뉴 닫히게 (근데 안됨)

  return (
    <div className={styles.navContainer} ref={navBarRef}>
      <NavLink to="/">
        <img className={styles.logo} src={Logo} alt="logoImg" />
      </NavLink>
      <div className={styles.menuContainer}>
        <SearchBox />
        <NotificationsIcon
          sx={{
            color: 'white',
            margin: '0 1.5%',
          }}
          fontSize="large"
          className={styles.notiIcon}
        />

        <ul onClick={() => setView(!view)} className={styles.dropdownContainer} ref={tabRef}>
          <AccountCircleIcon
            sx={{
              color: 'white',
              marginRight: '1.5rem',
              fontSize: '3.5rem',
            }}
            className={styles.account}
          />
          {view && <Dropdown />}
        </ul>

        <MenuIcon
          sx={{ color: 'white', cursor: 'pointer', mr: '2%' }}
          fontSize="large"
          onClick={() => menuToggle()}
        />
        <div className={styles.sideBar} ref={menuBar}>
          <div className={styles.sideBarWrapper} ref={menuBarContent}>
            네브 메뉴
          </div>
        </div>
      </div>
    </div>
  );
}

export default NavBar;
