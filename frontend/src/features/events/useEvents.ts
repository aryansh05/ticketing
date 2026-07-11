import { useEffect, useState } from "react";
import type { EventResponse } from "@/features/events/eventType";
import { getAllMyEvents } from "@/features/events/eventService";

function useEvents() {
    const [events, setEvents] = useState<EventResponse[]>([]);
    const [current, setCurrent] = useState(0);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        async function fetchEvents() {
            try {
                const data = await getAllMyEvents();
                setEvents(data);
            } catch (err) {
                setError("Failed to load events");
                console.error(err);
            } finally {
                setLoading(false);
            }
        }

        fetchEvents();
    }, []);

    useEffect(() => {
        if (events.length === 0) return;

        const interval = setInterval(() => {
            setCurrent((prev) => (prev + 1) % events.length);
        }, 3000);

        return () => clearInterval(interval);
    }, [events]);

    function nextEvent(){
        setCurrent((e) => (e + 1) % events.length);
    }

    function prevEvent(){
        setCurrent((e) => e == 0 ? events.length - 1 : e - 1);
    };

    function goToEvent(i : number){
        setCurrent(i);
    }

    return {
        events,
        current,
        currentEvent: events[current],
        loading,
        error,
        nextEvent,
        prevEvent,
        goToEvent
    };
}

export default useEvents;