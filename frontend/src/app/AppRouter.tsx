import {createBrowserRouter, Navigate} from "react-router";
import AppLayout from "@/app/AppLayout";
import HomePage from "@/features/Home/HomePage.tsx";
import AuthLayout from "@/features/auth/AuthLayout.tsx";
import LoginPage from "@/features/auth/LoginPage.tsx";
import RegisterPage from "@/features/auth/RegisterPage.tsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <AppLayout />,
        children: [
            {
                index: true,
                element: <HomePage />
            }
        ]
    },
    {
        path: "/auth",
        element: <AuthLayout />,
        children: [
            {
              index: true,
              element: <Navigate to="login" replace />
            },
            {
                path: "login",
                element: <LoginPage />
            },
            {
                path: "register",
                element: <RegisterPage />
            }
        ]
    }
])

export default router;