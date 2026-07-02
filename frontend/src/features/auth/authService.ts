import type {LoginRequest, LoginResponse, RegisterRequest, RegisterResponse} from "@/features/auth/authType.ts";
import myAxios from "@/app/apiClient.ts";

export const register = async (
    request: RegisterRequest
): Promise<RegisterResponse> => {
    const {data} = await myAxios.post("/auth/register", request);
    return data;
}

export const login = async (
    request: LoginRequest
): Promise<LoginResponse> => {
    const {data} = await myAxios.post("/auth/login", request);
    return data;
}