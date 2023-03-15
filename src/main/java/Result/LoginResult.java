package Result;

import Model.User;

/**
 * LoginResult class
 */
public class LoginResult {
    /**
     * authtoken
     */
    private String authtoken;
    /**
     * username
     */
    private String username;
    /**
     * personID
     */
    private String personID;
    /**
     * success
     */
    private boolean success;
    /**
     * message
     */
    private String message;

    /**
     * LoginResult constructor
     */
    public LoginResult() {}

    /**
     * LoginResult constructor
     * @param authtoken
     * @param username
     * @param personID
     * @param success
     * @param message
     */
    public LoginResult(String authtoken, String username, String personID, boolean success, String message) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.message = message;
    }

    /**
     * Logs in the user
     * @param user
     * @param authtoken
     * @return result
     */
    public LoginResult login(User user, String authtoken) {
        LoginResult result = new LoginResult();
        result.authtoken = authtoken;
        result.username = user.getUsername();
        result.personID = user.getPersonID();
        result.success = true;
        return result;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
