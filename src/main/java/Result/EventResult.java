package Result;

import Model.Event;

/**
 * EventResult class
 */
public class EventResult {
    /**
     * associatedUsername
     */
    private String associatedUsername;
    /**
     * eventID
     */
    private String eventID;
    /**
     * personID
     */
    private String personID;
    /**
     * latitude
     */
    private double latitude;
    /**
     * longitude
     */
    private double longitude;
    /**
     * country
     */
    private String country;
    /**
     * city
     */
    private String city;
    /**
     * eventType
     */
    private String eventType;
    /**
     * year
     */
    private int year;
    /**
     * message
     */
    private String message;
    /**
     * success
     */
    private boolean success;
    /**
     * data
     */
    private Event[] data;

    /**
     * EventResult constructor
     */
    public EventResult() {

    }

    /**
     * EventResult constructor
     * @param message
     * @param success
     */
    public EventResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }
}
