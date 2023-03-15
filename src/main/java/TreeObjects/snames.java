package TreeObjects;

/**
 * snames class
 */
public class snames {
    /**
     * array of Strings named data
     */
    String[] data;

    public  String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
    /**
     * Gets a random name from the array
     * @return a random name from the array
     */
    public String getRandomSname() {
        int random = (int) (Math.random() * data.length);
        return data[random];
    }
}
