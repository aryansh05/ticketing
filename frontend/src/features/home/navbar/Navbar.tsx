import { Link, NavLink } from "react-router";
import {MapPin, UserRound} from "lucide-react";
import { Button } from "@/shared/components/ui/button";
import { Separator } from "@/shared/components/ui/separator";
import useNavbar from "@/features/home/navbar/useNavbar.ts";
import {Dialog, DialogContent, DialogTrigger} from "@/shared/components/ui/dialog.tsx";
import AuthDialog from "@/features/auth/AuthDialog.tsx";
import {useState} from "react";

function Navbar() {
    const {location, navLinkClass} = useNavbar();
    const [open, setOpen] = useState(false);

    return (
        <header className="sticky top-0 z-50 flex w-full max-w-7xl mx-auto items-center justify-between rounded-4xl border border-white/20 bg-white/10 px-6 py-4 shadow-lg backdrop-blur-md dark:border-white/10 dark:bg-black/20 text-sm overflow-hidden mb-4">
            <div className="flex items-center gap-4">
                <div>
                    <Link to="/" className="flex text-amber-700 items-center text-2xl font-bold">
                        Ticketing
                    </Link>
                    <div className="text-muted-foreground text-xs flex justify-center">
                        <span>By Aryan Sharma</span>
                    </div>
                </div>

                <Separator orientation="vertical" className="h-8" />

                <div className="flex items-center gap-1 text-sm text-muted-foreground">
                    <MapPin className="h-4 w-4 text-red-500" />
                    <span>{location}</span>
                </div>
            </div>

            <nav className="lg:block hidden">
                <ul className="flex items-center gap-6">
                    <li>
                        <NavLink to="/all" className={navLinkClass}>
                            All
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/dining" className={navLinkClass}>
                            Dining
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/events" className={navLinkClass}>
                            Events
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/movies" className={navLinkClass}>
                            Movies
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/plays" className={navLinkClass}>
                            Plays
                        </NavLink>
                    </li>
                </ul>
            </nav>

            <Dialog open={open} onOpenChange={setOpen}>
                <DialogTrigger asChild>
                    <Button variant="ghost" size="icon" className="size-10">
                        <UserRound className="size-7 text-amber-700" />
                    </Button>
                </DialogTrigger>

                <DialogContent className="gap-0 p-0">
                    <AuthDialog onLoginSuccess={() => setOpen(false)} />
                </DialogContent>
            </Dialog>
        </header>
    );
}

export default Navbar;