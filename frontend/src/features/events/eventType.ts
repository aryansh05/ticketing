export interface EventResponse {
    id: string;
    userId: string;
    title: string;
    description: string;
    category: string;
    status: string;
    visibility: string;
    startTime: string; // ISO Instant string
    endTime: string;
}