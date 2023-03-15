package Request;
/**
 * LoginRequest class
 */
public class LoginRequest {
    /**
     * username
     */
    private String username;
    /**
     * password
     */
    private String password;

    /**
     * LoginRequest constructor
     */

    public LoginRequest() {}

    /**
     * LoginRequest constructor
     * @param username
     * @param password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * LoginRequest constructor
     * @param username
     * @param password
     * @return request
     */
    public LoginRequest login(String username, String password) {
        LoginRequest request = new LoginRequest();
        request.username = username;
        request.password = password;
        return request;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
