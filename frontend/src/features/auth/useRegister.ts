import type {RegisterRequest} from "@/features/auth/authType.ts";
import {type ChangeEvent, useState} from "react";
import {register} from "@/features/auth/authService.ts";
import {toast} from "sonner";
import {useNavigate} from "react-router";

export default function useRegister(){
    const [formData, setFormData] = useState<RegisterRequest>({
        fullName: "",
        email: "",
        password: "",
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const navigate = useNavigate();

    function handleChange(e : ChangeEvent<HTMLInputElement>) {
        const {
            name,
            value
        } = e.target;

        setFormData((data) => ({
            ...data, [name] : value
        }));
    }

    async function handleSubmit(e : ChangeEvent<HTMLFormElement>){
        e.preventDefault();

        setLoading(true);
        setError("");

        try{
            const response = await register(formData);
            toast.success(response.message);

            setFormData(() => ({
                fullName: "",
                email: "",
                password: ""
            }))
            navigate("/auth/login");
        }catch (error: any){
            const errorMessage = error?.response?.data?.message ?? "Registration failed";
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