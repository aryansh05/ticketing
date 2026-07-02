import {Outlet} from "react-router";

function AuthLayout(){
    return (
        <>
            <div className="flex justify-center min-h-screen items-center bg-background">
                <Outlet />
            </div>
        </>
    )
}

export default AuthLayout;