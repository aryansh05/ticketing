import {useState} from "react";
import LoginPage from "@/features/auth/LoginPage.tsx";
import RegisterPage from "@/features/auth/RegisterPage.tsx";
import type {AuthDialogContent} from "@/features/auth/authType.ts";

function AuthDialog({
    onLoginSuccess
}: AuthDialogContent){
    const [mode, setMode] = useState<"login" | "register">("login");
    return (
        <>
            {
                mode === "login" ? (
                    <LoginPage
                        onRegisterClick={() => setMode("register")}
                        onLoginSuccess={onLoginSuccess} />
                ) : (
                    <RegisterPage onLoginClick={() => setMode("login")} />
                )
            }
        </>
    )
}

export default AuthDialog;