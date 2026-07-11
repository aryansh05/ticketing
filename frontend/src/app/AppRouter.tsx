import {createBrowserRouter} from "react-router";
import AppLayout from "@/app/AppLayout";
import HomePage from "@/features/home/HomePage.tsx";
import Events from "@/features/events/Events.tsx";
import Dining from "@/features/dining/Dining.tsx";
import Movies from "@/features/movies/Movies.tsx";
import Play from "@/features/plays/Play.tsx";
import All from "@/features/all/All.tsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <AppLayout />,
        children: [
            {
                index: true,
                element: <HomePage />
            },
            {
                path: "all",
                element: <All />
            },
            {
                path: "events",
                element: <Events />
            },
            {
                path: "plays",
                element: <Play />
            },
            {
                path: "dining",
                element: <Dining />
            },            {
                path: "movies",
                element: <Movies />
            },

        ]
    },
])

export default router;