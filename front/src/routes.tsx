import { useRoutes } from "react-router-dom";

import Home from "./Containers";
import NFTBodyContainer from "./Containers/nftMain/NFTBodyContainer";

export default function Router() {
  return useRoutes([
    {
      path: "/",
      element: <Home />,
      children: [
        { path: "/", element: <Home /> },
      ],
    },
    {
      path: "/nftMain",
      element: <NFTBodyContainer />
    }
  ]);
}