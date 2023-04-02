import TeamPage from 'Containers/ETC/TeamPage';
import { useRoutes } from 'react-router-dom';

import Home from './Containers/Main/Main';
import Signup from 'Containers/login/Signup';
import GameMain from 'Containers/gameMain';
import GamePlay from 'Containers/GamePlay';
import LookFarm from 'Containers/LookFarm';
import ErrorPage from 'Containers/Error/ErrorPage';
import { NFTBodyContainer } from 'Containers/NFT/Main/NFTBodyContainer';
import { NFTSearch } from 'Containers/NFT/Search';
import MyPage from 'Containers/MyPage';
import { NFTDetail } from 'Containers/NFT/Detail';
import { CategorySearch } from 'Containers/NFT/Category';
import Notification from 'Containers/Noti';

export default function Router() {
  return useRoutes([
    { path: '/', element: <Home /> },
    { path: '/nft', element: <NFTBodyContainer /> },
    { path: '/nft/category/:id', element: <CategorySearch /> },
    { path: '/nft/:keyword', element: <NFTSearch /> },
    { path: '/nft/search/:id', element: <NFTDetail /> },
    { path: '/team', element: <TeamPage /> },
    { path: '/signup', element: <Signup /> },
    { path: '/game/main', element: <GameMain /> },
    { path: '/game/play', element: <GamePlay /> },
    { path: '/game/lookfarm', element: <LookFarm /> },
    { path: '/nftMain', element: <NFTBodyContainer /> },
    {
      path: '/mypage/:address',
      element: <MyPage />,
    },
    {
      path: '/notify',
      element: <Notification />,
    },
    { path: '/*', element: <ErrorPage /> },
  ]);
}
