package hackathon.ticketmaster;

import com.google.gson.annotations.SerializedName;
import hackathon.ticketmaster.EventStatus;
import hackathon.ticketmaster.EventDate;

public class ApiEventDate {

    @SerializedName("start")
    public EventDate start;
    @SerializedName("end")
    public EventDate end;
    public String timezone;
    public EventStatus status;
    public boolean spanMultipleDays;


} //EventDates
