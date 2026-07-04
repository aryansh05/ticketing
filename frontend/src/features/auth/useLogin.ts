import {type ChangeEvent, useState} from "react";
import type {LoginRequest} from "@/features/auth/authType.ts";
import {login} from "@/features/auth/authService.ts";
import {toast} from "sonner";

function useLogin(onSuccess: () => void) {
    const [formData, setFormData] = useState<LoginRequest>({
        email: "",
        password: ""
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    function handleChange(e : ChangeEvent<HTMLInputElement>){
        const {
            name,
            value
        } = e.target;
    setFormData((data) => ({
        ...data,
        [name] : value
    }));
    }

    async function handleSubmit(e : ChangeEvent<HTMLFormElement>){
        e.preventDefault();
        setLoading(true);
        setError("");

        try{
            const {accessToken, user} = await login(formData);
            localStorage.setItem("accessToken", accessToken);
            localStorage.setItem("user", JSON.stringify(user));
            console.log(accessToken);
            console.log(user);
            toast.success(`Welcome back, ${user.fullName}`);

            setFormData({
                email: "",
                password: ""
            })
            onSuccess();
        }catch (error: any){
            const errorMessage = error?.response?.data?.message ?? "Login failed";
            setError(errorMessage);
            toast.error(errorMessage);
        }finally {
            setLoading(false);
        }
    }

    return {
        formData,
        error,
        loading,
        handleChange,
        handleSubmit
    }
}

export default useLogin;