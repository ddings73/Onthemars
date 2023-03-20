import TeamPage from 'Containers/ETC/TeamPage';
import { useRoutes } from 'react-router-dom';

import Home from './Containers/Main/Main';
import Login from 'Containers/login/Login';
import GameMain from 'Containers/gameMain';
import ErrorPage from 'Containers/Error/ErrorPage';
import { NFTBodyContainer } from 'Containers/NFT/Main/NFTBodyContainer';
import { NFTSearch } from 'Containers/NFT/Search';

export default function Router() {
  return useRoutes([
    {
      path: '/',
      element: <Home />,
    },
    {
      path: '/nft',
      element: <NFTBodyContainer />,
    },
    {
      path: '/nft/search',
      element: <NFTSearch />,
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
      path: '/*',
      element: <ErrorPage />,
    },
  ]);
}
