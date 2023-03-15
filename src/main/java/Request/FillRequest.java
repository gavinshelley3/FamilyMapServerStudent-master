package Request;
/**
 * FillRequest class
 */
public class FillRequest {
    /**
     * username
     */
    private String username;
    /**
     * generations
     */
    private int generations;

    /**
     * FillRequest constructor
     * @param username
     * @param generations
     */

    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }

    /**
     * FillRequest constructor
     * @param username
     */
    public FillRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

}
