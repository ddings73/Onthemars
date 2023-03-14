import TeamPage from 'Containers/ETC/TeamPage';
import { useRoutes } from 'react-router-dom';

import Home from './Containers/Main/Main';
import NFTBodyContainer from './Containers/nftMain/NFTBodyContainer';
import Login from 'Containers/login/Login';
import GameMain from 'Containers/gameMain';
import Main from 'Containers/Main/Main';
import ErrorPage from 'Containers/Error/ErrorPage';

export default function Router() {
  const user = true;
  return useRoutes([
    {
      path: '/',
      element: <Home />,
      children: [{ path: '/', element: <Home /> }],
    },
    {
      path: '/nftMain',
      element: <NFTBodyContainer />,
    },
    {
      path: '/team',
      element: <TeamPage />,
    },
    {
      path: '/login',
      element: <Login />,
    },
    {
      path: '/game/main',
      element: <GameMain />,
    },
    {
      path: '/nftMain',
      element: <NFTBodyContainer />,
    },
    {
      path: '/*',
      element: <ErrorPage />,
    },
  ]);
}
