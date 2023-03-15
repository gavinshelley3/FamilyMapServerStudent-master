package Model;
/**
 * Person class
 */
public class Person {
    /**
     * firstName
     */
    private String firstName;
    /**
     * lastName
     */
    private String lastName;
    /**
     * gender
     */
    private String gender;
    /**
     * personID
     */
    private String personID;
    /**
     * fatherID
     */
    private String fatherID;
    /**
     * motherID
     */
    private String motherID;
    /**
     * spouseID
     */
    private String spouseID;
    /**
     * associatedUsername
     */
    private String associatedUsername;

    /**
     * Person constructor
     */
    public Person() {}

    /**
     * Person constructor
     * @param firstName
     * @param lastName
     * @param gender
     * @param personID
     * @param fatherID
     * @param motherID
     * @param spouseID
     * @param associatedUsername
     */
    public Person(String firstName, String lastName, String gender, String personID,
                  String fatherID, String motherID, String spouseID, String associatedUsername) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
