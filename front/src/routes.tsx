import { useRoutes } from "react-router-dom";

import Home from "./Containers";

export default function Router() {
  return useRoutes([
    {
      path: "/",
      element: <Home />,
      children: [
        { path: "/", element: <Home /> },
      ],
    },
  ]);
}