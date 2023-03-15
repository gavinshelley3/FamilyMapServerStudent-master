package TreeObjects;

/**
 * Location class
 */
public class Location {
    /**
     * country
     */
    private String country;
    /**
     * city
     */
    private String city;
    /**
     * latitude
     */
    private float latitude;
    /**
     * longitude
     */
    private float longitude;

    /**
     * Location constructor
     */

    public Location() {}

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

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    /**
     * Writes the location to a string
     * @return location
     */
    public String toString() {
        return "Location: " + country + ", " + city + ", " + latitude + ", " + longitude;
    }


}
