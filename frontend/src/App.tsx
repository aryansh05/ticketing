import router from "@/app/AppRouter.tsx";
import {RouterProvider} from "react-router";

function App() {

  return (
    <>
        <RouterProvider router={router} />
    </>
  )
}

export default App
