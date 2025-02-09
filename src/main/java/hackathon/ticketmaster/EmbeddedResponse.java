package hackathon.ticketmaster;

import hackathon.ticketmaster.ApiEvent;
import com.google.gson.annotations.SerializedName;

public class EmbeddedResponse {

    @SerializedName("events")
    public ApiEvent[] events;

} //EmbeddedResponse
