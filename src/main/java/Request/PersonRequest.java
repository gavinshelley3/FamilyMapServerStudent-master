package Request;
/**
 * PersonRequest class
 */
public class PersonRequest {
    /**
     * personID
     */
    private String personID;
    /**
     * authtoken
     */
    private String authtoken;

    /**
     * PersonRequest constructor
     * @param personID
     * @param authtoken
     */

    public PersonRequest(String personID, String authtoken) {
        this.personID = personID;
        this.authtoken = authtoken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
