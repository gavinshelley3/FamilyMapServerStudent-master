package Result;

import Model.User;

import java.util.UUID;

/**
 * RegisterResult class
 */
public class RegisterResult {
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
     * RegisterResult constructor
     */

    public RegisterResult() {

    }

    /**
     * RegisterResult constructor
     * @param authtoken
     * @param username
     * @param personID
     * @param success
     * @param message
     */
    public RegisterResult(String authtoken, String username, String personID, boolean success, String message) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.message = message;
    }

    /**
     * Registers the user
     * @param user
     * @param authtoken
     * @return result
     */
    public RegisterResult register(User user, String authtoken) {
        RegisterResult result = new RegisterResult();
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

    public String generateAuthtoken() {
        return UUID.randomUUID().toString();
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
