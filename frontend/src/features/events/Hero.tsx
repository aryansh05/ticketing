import useEvents from "./useEvents";
import { Spinner } from "@/shared/components/ui/spinner.tsx";
import {ChevronLeft, ChevronRight} from "lucide-react";

function Hero() {
    const {
        events,
        currentEvent,
        loading,
        error,
        nextEvent,
        prevEvent,
        goToEvent,
        current
    } = useEvents();

    if (loading) {
        return (
            <div className="flex min-h-[550px] items-center justify-center gap-3">
                <Spinner />
                <p>Loading...</p>
            </div>
        );
    }

    if (error) {
        return (
            <div className="flex min-h-[550px] items-center justify-center">
                <p>{error}</p>
            </div>
        );
    }

    if (!currentEvent) {
        return (
            <div className="flex min-h-[550px] items-center justify-center">
                <p>No event found</p>
            </div>
        );
    }

    return (
        <section className="relative min-h-[550px] overflow-hidden">
            <div className="absolute inset-0 bg-cover bg-center "
                 style={{
                     backgroundImage: `url(https://picsum.photos/800/600)`,
                 }}
            />

            <div className="absolute inset-0 bg-gradient-to-b from-white/30 via-white/85 to-white"/>
            <div className="relative z-10 min-h-[550px] gap-16 mx-auto flex max-w-7xl items-center justify-between px-16 lg:px-24">
            <div className="flex-1 max-w-3xl">
                <h1 className="text-5xl text-black font-bold leading-tight tracking-tight">{currentEvent.title}</h1>
            </div>
            <div className="shrink-0">
                <img className="h-[445px] w-[330px] object-cover rounded-xl shadow-lg"
                    src="https://picsum.photos/800/600"/>
            </div>
            </div>

            <button
                onClick={prevEvent}
                className="absolute left-6 top-1/2 z-10 -translate-y-1/2 lg:left-10"
            >
                <ChevronLeft className="text-black h-7 w-7"/>

            </button>
            <button
                onClick={nextEvent}
                className="absolute right-6 top-1/2 z-10 -translate-y-1/2 lg:right-10"
            >
                <ChevronRight className="text-black h-7 w-7"/>
            </button>
            <div className="absolute bottom-6 left-1/2 z-10 flex -translate-x-1/2 items-center gap-2">
                {events.map((event, index) => (
                    <button
                        key={event.id}
                        onClick={() => goToEvent(index)}
                        className={`h-2 rounded-full transition-all duration-300 ${
                            index === current
                                ? "w-6 bg-black"
                                : "w-2 bg-gray-300"
                        }`}
                    />
                ))}
            </div>
        </section>
    );
}

export default Hero;