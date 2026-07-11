import useEvents from "@/features/events/useEvents";
import { Card } from "@/shared/components/ui/card";
import Hero from "@/features/events/Hero.tsx";

function Events() {
    const {
        events,
        loading,
        error,
    } = useEvents();

    function formatDate(dateString: string) {
        return new Intl.DateTimeFormat("en-IN", {
            day: "numeric",
            month: "short",
            year: "numeric",
        }).format(new Date(dateString));
    }

    function formatTime(dateString: string) {
        return new Intl.DateTimeFormat("en-IN", {
            hour: "numeric",
            minute: "2-digit",
        }).format(new Date(dateString));
    }

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;

    return (
        <>
            <div className="mb-8">
                <Hero />
            </div>
        <section>
            <div className="w-full max-w-7xl mx-auto">
                <div className="mb-4">
                    <span className="text-2xl font-bold">All Events</span>
                </div>
            <div className="grid grid-cols-1 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                {events.map((e) => (
                    <Card key={e.id} className="gap-0 overflow-hidden py-0">
                        <div className="h-100 bg-gray-400"></div>
                        <div className="p-2">
                        <h1 className="font-bold text-lg">{e.title}</h1>
                        <div className="text-muted-foreground">
                        <p>{e.category}</p>
                        <p>{e.status}</p>
                            <p>{formatDate(e.startTime)}</p>

                            <p>
                                {formatTime(e.startTime)} - {formatTime(e.endTime)}
                            </p>
                        </div>
                        </div>
                    </Card>
                ))}
            </div>
            </div>
        </section>
        </>
    );
}

export default Events;