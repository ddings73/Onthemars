import TeamPage from 'Containers/ETC/TeamPage';
import { useRoutes } from 'react-router-dom';

import Home from './Containers/Main/Main';
import NFTBodyContainer from './Containers/NFT/NFTBodyContainer';
import Login from 'Containers/login/Login';
import GameMain from 'Containers/gameMain';
import ErrorPage from 'Containers/Error/ErrorPage';

export default function Router() {
  return useRoutes([
    {
      path: '/',
      element: <Home />,
      children: [{ path: '/', element: <Home /> }],
    },
    {
      path: '/nft',
      element: <NFTBodyContainer />,
      children: [
        { path: "", element: <NFTBodyContainer /> },
      ],
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
