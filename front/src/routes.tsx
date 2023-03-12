import { useRoutes } from "react-router-dom";

import Login from "Containers/login/Login";
import NFTBodyContainer from "./Containers/nftMain/NFTBodyContainer";
import GameMain from "Containers/gameMain";
import Main from "Containers/Main/Main";

export default function Router() {
  const user = true;
  return useRoutes([
    {
      path: "/",
      element: <Main />
    },
    {
      path: "/login",
      element: <Login />
    },
    {
      path: "/game/main",
      element: <GameMain />
    },
    {
      path: "/nftMain",
      element: <NFTBodyContainer />
    },
    // {
    //   path: "/*",
    //   element: <Known404 />,
    // },
  ]);
}