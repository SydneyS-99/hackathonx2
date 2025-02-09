

package hackathon.ticketmaster;

import com.google.gson.annotations.SerializedName;
import hackathon.ticketmaster.EmbeddedResponse;

public class ApiResponse {

    @SerializedName("_embedded")
    public EmbeddedResponse embedded;

} // ApiResponse
