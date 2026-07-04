import {Outlet} from "react-router";
import Navbar from "@/features/home/navbar/Navbar.tsx";

function AppLayout() {
    return (
        <>
            <Navbar/>
            <main>
                <Outlet/>
            </main>
        </>
    );
}

export default AppLayout;