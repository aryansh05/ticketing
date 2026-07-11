import myAxios from "@/app/apiClient.ts";
import type {EventResponse} from "@/features/events/eventType.ts";

export const getAllMyEvents = async ():Promise<EventResponse[]> => {
    const {data} = await myAxios.get("/event/me");
    return data;
}