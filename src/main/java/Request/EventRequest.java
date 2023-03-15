package Request;
/**
 * ClearRequest class
 */
public class EventRequest {
    /**
     * eventID
     */
    private String eventID;
    /**
     * authtoken
     */
    private String authtoken;

    /**
     * EventRequest constructor
     * @param authtoken
     * @param eventID
     */

    public EventRequest(String authtoken, String eventID) {
        this.eventID = eventID;
        this.authtoken = authtoken;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
