import React, { useState, useRef, useEffect } from 'react';
import styles from './NavBar.module.scss';
import Logo from 'assets/logo.png';
import SearchBox from './SearchBox';
import NotificationsIcon from '@mui/icons-material/Notifications';
import { NavLink, useNavigate } from 'react-router-dom';
import Login from './Login';
import Badge from '@mui/material/Badge';
import { getMessaging, onMessage } from 'firebase/messaging';
import ClearIcon from '@mui/icons-material/Clear';
import { api } from 'apis/api/ApiController';
import Swal from 'sweetalert2';
import { handleLogin } from 'component/button/login';

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
        if (window.screen.width <= 420) {
          sideMenu.current.style.transform = 'translateX(0px)';
          sideMenu.current.style.opacity = '1';
          sideMenu.current.style.width = '70%';
        } else {
          sideMenu.current.style.transform = 'translateX(0px)';
        }
      } else {
        if (window.screen.width <= 420) {
          sideMenu.current.style.transform = 'translateX(50px)';
          sideMenu.current.style.opacity = '0';
          sideMenu.current.style.width = '0%';
        } else {
          sideMenu.current.style.transform = 'translateX(400px)';
        }
      }
    }
  }
  // 사이드 메뉴바 스타일

  // Navbar 스크롤 좌표 감시
  useEffect(() => {
    if (navBarRef.current !== null) {
      if (scrollPosition >= 200) {
        // if(window.screen.width <= 420){

        // }
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
  });

  useEffect(() => {
    if (received === 'true') {
      setInvisible(false);
    } else {
      setInvisible(true);
    }
  }, [received]);

  const logout = () => {
    const refreshToken = sessionStorage.getItem('refreshToken');
    api.delete('/auth/login', { headers: { refreshToken } }).then((res) => {
      sessionStorage.removeItem('address');
      sessionStorage.removeItem('accessToken');
      sessionStorage.removeItem('refreshToken');
      sessionStorage.removeItem('received');
      navigate('/');
    });
  };

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
                Swal.fire('로그인이 필요합니다.', '', 'error');
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
              <div style={{ textDecoration: 'none', color: '#ffffff' }}>
                <h1>GAME</h1>
              </div>
              {sessionStorage.getItem('address') ?
                <NavLink onClick={menuToggle} to="/game/main" style={{ textDecoration: 'none', color: '#ffffff' }}>
                  <h2> 메인 </h2>
                </NavLink> :
                <div onClick={handleLogin} style={{ textDecoration: 'none', color: '#ffffff' }}>
                  <h2> 메인 </h2>
                </div>
              }
              {sessionStorage.getItem('address') ?
                <NavLink onClick={menuToggle} to="/game/play" style={{ textDecoration: 'none', color: '#ffffff' }}>
                  <h2> 내농장 </h2>
                </NavLink> :
                <div onClick={handleLogin} style={{ textDecoration: 'none', color: '#ffffff' }}>
                  <h2> 내농장 </h2>
                </div>}
            </div>
            <div className={styles.navNft}>
              <div style={{ textDecoration: 'none', color: '#ffffff' }}>
                <h1>NFT</h1>
              </div>
              <NavLink onClick={menuToggle} to="/nft/search" style={{ textDecoration: 'none', color: '#ffffff' }}>
                <h2> 메인 </h2>
              </NavLink>
              <NavLink onClick={menuToggle} to="/nft/search" style={{ textDecoration: 'none', color: '#ffffff' }}>
                <h2> 검색 </h2>
              </NavLink>
              <NavLink onClick={menuToggle} to="/nft/desc" style={{ textDecoration: 'none', color: '#ffffff' }}>
                <h2> NFT란</h2>
              </NavLink>
            </div>
            <div className={styles.bottom} >
              {sessionStorage.getItem('address') !== null ? (
                <h2 onClick={logout}>로그아웃</h2>
              ) : (
                <h2 style={{ visibility: 'hidden' }}>로그아웃</h2>
              )}
              <h3 onClick={menuToggle}>
                <ClearIcon sx={{ color: 'white' }} fontSize="large" />
              </h3>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default NavBar;
