package hackathon.ticketmaster;

import hackathon.ticketmaster.ApiImage;
import hackathon.ticketmaster.ApiEventDate;

public class ApiEvent {

    public String name;
    public String type;
    public String id;
    public boolean test;
    public String locale;
    public ApiImage[] images;
    public ApiEventDate dates;

    public ApiEvent(String name) {
        this.name = name;
    }


} //ApiEvent
