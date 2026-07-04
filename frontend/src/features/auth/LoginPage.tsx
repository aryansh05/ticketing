import useLogin from "@/features/auth/useLogin.ts";
import {Card, CardContent, CardDescription, CardHeader, CardTitle} from "@/shared/components/ui/card.tsx";
import {Label} from "@/shared/components/ui/label.tsx";
import {LockKeyholeIcon, Mail} from "lucide-react";
import {Input} from "@/shared/components/ui/input.tsx";
import {ErrorIcon} from "react-hot-toast";
import {Button} from "@/shared/components/ui/button.tsx";
import {Spinner} from "@/shared/components/ui/spinner.tsx";
import OAuth2Buttons from "@/features/auth/OAuth2Buttons.tsx";
import type {LoginProps} from "@/features/auth/authType.ts";

function LoginPage({onRegisterClick,onLoginSuccess} : LoginProps){
    const {
        formData,
        error,
        loading,
        handleChange,
        handleSubmit
    } = useLogin(onLoginSuccess);

    return(
        <>
            <div className="w-full max-w-md">
                <Card>
                    <CardHeader className="flex flex-col items-center">
                        <CardTitle className="text-lg">Welcome Back</CardTitle>
                        <CardDescription>Please provide your details below.</CardDescription>
                    </CardHeader>
                    <CardContent>
                        <form onSubmit={handleSubmit} className="flex flex-col gap-6">
                            <div className="relative grid gap-2">
                                <Label htmlFor="email">Email</Label>

                                <Mail className="absolute left-3 top-2/3 -translate-y-1/3 h-4 w-4 text-muted-foreground pointer-events-none" />

                                <Input
                                    id="email"
                                    name="email"
                                    type="email"
                                    placeholder="email@example.com"
                                    value={formData.email}
                                    onChange={handleChange}
                                    className="pl-10"
                                    required
                                />
                            </div>

                            <div className="relative grid gap-2">
                                <Label htmlFor="password">Password</Label>

                                <LockKeyholeIcon className="absolute left-3 top-2/3 -translate-y-1/3 h-4 w-4 text-muted-foreground pointer-events-none" />

                                <Input
                                    id="password"
                                    name="password"
                                    type="password"
                                    placeholder="Password"
                                    value={formData.password}
                                    onChange={handleChange}
                                    className="pl-10"
                                    required
                                />
                            </div>

                            {error && (
                                <div className="text-red-700 font-bold flex items-center justify-center gap-2">
                                    <ErrorIcon />
                                    <span>{error}</span>
                                </div>
                            )}

                            <Button
                                size="lg"
                                type="submit"
                                className="w-full mb-2"
                                disabled={loading}
                            >
                                {loading ? (
                                    <>
                                        <Spinner className="mr-2 h-4 w-4" />
                                        Please wait...
                                    </>
                                ) : (
                                    "Login"
                                )}
                            </Button>
                        </form>
                        <div className="flex py-2 items-center">
                            <div className="grow border-t border-border"></div>
                            <span className="shrink mx-4 text-xs uppercase text-muted-foreground tracking-wider">Or</span>
                            <div className="grow border-t border-border"></div>
                        </div>
                        <OAuth2Buttons />
                        <div className="flex flex-col items-center w-full gap-4 py-2">
                            <p className="text-sm text-muted-foreground text-center mt-4">
                                Don't have an account?{" "}
                                <button
                                    type="button"
                                    onClick={onRegisterClick}
                                    className="text-primary hover:underline"
                                >
                                    Register
                                </button>

                            </p>
                        </div>
                    </CardContent>
                </Card>
            </div>
        </>
    )
}

export default LoginPage;