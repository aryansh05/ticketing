import useRegister from "@/features/auth/useRegister";
import {Card, CardContent, CardDescription, CardHeader, CardTitle} from "@/shared/components/ui/card.tsx";
import {Label} from "@/shared/components/ui/label.tsx";
import {Input} from "@/shared/components/ui/input.tsx";
import {Button} from "@/shared/components/ui/button.tsx";
import {ErrorIcon} from "react-hot-toast";
import {LockKeyholeIcon, Mail, User} from "lucide-react";
import {Spinner} from "@/shared/components/ui/spinner.tsx";
import OAuth2Buttons from "@/features/auth/OAuth2Buttons.tsx";
import type {RegisterProps} from "@/features/auth/authType.ts";

export default function RegisterPage({onLoginClick} : RegisterProps) {
    const {
        formData,
        loading,
        error,
        handleChange,
        handleSubmit,
    } = useRegister(onLoginClick);

    return (
        <>
            <div className="w-full max-w-md">
                <Card>
                    <CardHeader className="flex flex-col items-center">
                        <CardTitle className="text-lg">Create your account</CardTitle>
                        <CardDescription>Please provide your details below.</CardDescription>
                    </CardHeader>
                    <CardContent>
                        <form onSubmit={handleSubmit} className="flex flex-col gap-6">
                            <div className="relative grid gap-2">
                                <Label htmlFor="fullName">Full Name</Label>

                                    <User className="absolute left-3 top-2/3 -translate-y-1/3 h-4 w-4 text-muted-foreground pointer-events-none"/>

                                    <Input
                                        id="fullName"
                                        name="fullName"
                                        type="text"
                                        placeholder="Your Name"
                                        value={formData.fullName}
                                        onChange={handleChange}
                                        className="pl-10"
                                        required
                                    />
                            </div>

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
                                    "Register"
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
                                Already have an account?{" "}
                                <button
                                    type="button"
                                    onClick={onLoginClick}
                                    className="text-primary hover:underline"
                                >
                                    Login
                                </button>
                            </p>
                        </div>
                    </CardContent>
                </Card>
            </div>
        </>
    )
}
