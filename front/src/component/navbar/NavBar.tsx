import React, { useState, useRef, useEffect } from 'react';
import styles from './NavBar.module.scss';
import Logo from 'assets/logo.png';
import SearchBox from './SearchBox';
import NotificationsIcon from '@mui/icons-material/Notifications';
import { NavLink, useNavigate } from 'react-router-dom';
import Login from './Login';
import Badge from '@mui/material/Badge';
import { getMessaging, onMessage } from 'firebase/messaging';

export function NavBar() {
  const navigate = useNavigate();
  // 스크롤 위치 입력 변수
  const [scrollPosition, setScrollPosition] = useState(0);
  const updateScroll = () => {
    setScrollPosition(window.scrollY || document.documentElement.scrollTop);
  };

  // 사이드 메뉴바 변수
  const navBarRef = useRef<HTMLDivElement>(null);
  const [isToggled, setIsToggled] = useState(false);

  // 사이드 메뉴바 토글 키 설정
  const sideMenu = useRef<HTMLDivElement>(null);
  function menuToggle() {
    setIsToggled(!isToggled);
    console.info('토글 상태: ', isToggled);
    if (sideMenu.current) {
      if (!isToggled) {
        sideMenu.current.style.transform = 'translateX(0px)';
      } else {
        sideMenu.current.style.transform = 'translateX(400px)';
      }
    }
  }
  // 사이드 메뉴바 스타일

  // Navbar 스크롤 좌표 감시
  useEffect(() => {
    if (navBarRef.current !== null) {
      if (scrollPosition >= 200) {
        navBarRef.current.style.backgroundColor = '#252525';
      } else {
        navBarRef.current.style.backgroundColor = 'transparent';
      }
    }
  }, [scrollPosition]);

  useEffect(() => {
    window.addEventListener('scroll', updateScroll);
  }, []);

  const [invisible, setInvisible] = useState<boolean>(true);
  const received = sessionStorage.getItem('received');

  const messaging = getMessaging();
  onMessage(messaging, (payload) => {
    sessionStorage.setItem('received', 'true');
    alert('메세지옴');
    console.log('Message received. ', payload);
  });

  useEffect(() => {
    if (received === 'true') {
      setInvisible(false);
    } else {
      setInvisible(true);
    }
  }, [received]);

  return (
    <div className={styles.navContainer} ref={navBarRef}>
      <NavLink to="/">
        <img className={styles.logo} src={Logo} alt="logoImg" />
      </NavLink>
      <div className={styles.menuContainer} style={{ marginRight: isToggled ? '23rem ' : '0' }}>
        <SearchBox />

        <Badge
          color="secondary"
          variant="dot"
          anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
          invisible={invisible}
        >
          <NotificationsIcon
            sx={{
              color: 'white',
            }}
            fontSize="large"
            className={styles.notiIcon}
            onClick={() => {
              if (sessionStorage.getItem('address') !== null) {
                sessionStorage.setItem('received', 'false');
                navigate('/notify');
              } else {
                alert('로그인이 필요합니다.');
              }
            }}
          />
        </Badge>

        <Login />

        <div className={isToggled ? styles.menubtn_open : styles.menubtn} onClick={menuToggle}>
          <div className={styles.icon} />
        </div>
        <div className={styles.sideMenu} ref={sideMenu}>
          <div className={styles.navItems}>
            <div className={styles.navMeta}>
              <NavLink to="/game/main" style={{ textDecoration: 'none', color: '#ffffff' }}>
                <h1>메타버스 게임</h1>
              </NavLink>
              <NavLink to="/game/play" style={{ textDecoration: 'none', color: '#ffffff' }}>
                <h2> 게임플레이 </h2>
              </NavLink>
              <NavLink to="/game/lookfarm" style={{ textDecoration: 'none', color: '#ffffff' }}>
                <h2> 농장 보기 </h2>
              </NavLink>
            </div>
            <div className={styles.navNft}>
              <NavLink to="/nft" style={{ textDecoration: 'none', color: '#ffffff' }}>
                <h1>NFT 서비스</h1>
              </NavLink>
              <NavLink to="/nft/search" style={{ textDecoration: 'none', color: '#ffffff' }}>
                <h2> NFT 검색 </h2>
              </NavLink>
              <NavLink to="/game/main" style={{ textDecoration: 'none', color: '#ffffff' }}>
                <h2> NFT 마켓</h2>
              </NavLink>
            </div>
            <h3>로그인</h3>
          </div>
        </div>
      </div>
    </div>
  );
}

export default NavBar;
