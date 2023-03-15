package TreeObjects;

/**
 * LocationData class
 */
public class LocationData {
    /**
     * array of Locations named data
     */
    Location[] data;

    public Location[] getData() {
        return data;
    }

    public void setData(Location[] data) {
        this.data = data;
    }

    /**
     * Gets a random location from the array
     * @return a random location from the array
     */
    public Location getRandomLocation() {
        int random = (int) (Math.random() * data.length);
        return data[random];
    }
}
