

package hackathon.ticketmaster;

import com.google.gson.annotations.SerializedName;

public class EventDate {

    @SerializedName("localDate")
    public String localDate;
    @SerializedName("localTime")
    public String localTime;
    public String dateTime;

}//EventDate
