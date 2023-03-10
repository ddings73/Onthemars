import { useRoutes } from "react-router-dom";

import Login from "Containers/login/Login";
import NFTBodyContainer from "./Containers/nftMain/NFTBodyContainer";
import Main from "Containers/gameMain";

export default function Router() {
  return useRoutes([
    {
      path: "/login",
      element: <Login />
    },
    {
      path: "/game/main",
      element: <Main />
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