export type RegisterRequest = {
    fullName: string,
    email: string,
    password: string
}

export interface RegisterResponse {
    success: boolean,
    message: string
}

export type LoginRequest = {
    email : string,
    password: string
}

export type User = {
    id: string,
    fullName: string,
    email: string,
    authProvider : string
}

export type LoginResponse = {
    accessToken: string,
    user: User
}

export type LoginProps = {
    onRegisterClick: () => void;
    onLoginSuccess: () => void;
};

export type RegisterProps = {
    onLoginClick: () => void;
}
export type AuthDialogContent = {
    onLoginSuccess: () => void;
};
