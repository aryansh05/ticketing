import {useEffect, useState} from "react";

function useNavbar(){
    const [location, setLocation] = useState("Detecting...");

    useEffect(() => {
        fetch("https://ipwho.is/")
            .then((res) => res.json())
            .then((data) => {
                if (data.success) {
                    setLocation(`${data.city}, ${data.region}`);
                } else {
                    setLocation("Location unavailable");
                }
            })
            .catch(() => {
                setLocation("Location unavailable");
            });
    }, []);

    const navLinkClass = ({ isActive }: { isActive: boolean }) =>
        `rounded-lg px-3 py-2 transition-colors ${
            isActive
                ? "bg-amber-700 text-white"
                : "hover: hover:text-amber-700"
        }`;

    return {
        location,
        navLinkClass
    };
}

export default useNavbar;