package Model;
/**
 * Event class
 */
public class Event {
    /**
     * eventType
     */
    private String eventType;
    /**
     * personID
     */
    private String personID;
    /**
     * city
     */
    private String city;
    /**
     * country
     */
    private String country;
    /**
     * latitude
     */
    private float latitude;
    /**
     * longitude
     */
    private float longitude;
    /**
     * year
     */
    private int year;
    /**
     * eventID
     */
    private String eventID;
    /**
     * associatedUsername
     */
    private String associatedUsername;

    /**
     * Event constructor
     */
    public Event() {}

    /**
     * Event constructor
     * @param eventType
     * @param personID
     * @param city
     * @param country
     * @param latitude
     * @param longitude
     * @param year
     * @param eventID
     * @param associatedUsername
     */
    public Event(String eventType, String personID, String city, String country, float latitude, float longitude,
                 int year, String eventID, String associatedUsername) {
        this.eventType = eventType;
        this.personID = personID;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.year = year;
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
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
}
