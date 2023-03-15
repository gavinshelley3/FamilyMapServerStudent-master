package Result;

import Model.Person;

/**
 *  PersonResult class
 */
public class PersonResult {
    /**
     * array of persons named data
     */
    private Person[] data;
    /**
     * associatedUsername
     */
    private String associatedUsername;
    /**
     * personID
     */
    private String personID;
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
     * message
     */
    private String message;
    /**
     * success
     */
    private boolean success;
    /**
     * person
     */

    private Person person;

    /**
     * PersonResult constructor
     */
    public PersonResult() {

    }

    /**
     * PersonResult constructor
     * @param data
     * @param message
     * @param success
     */
    public PersonResult(Person[] data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    /**
     * PersonResult constructor
     * @param person
     * @param message
     * @param success
     */
    public PersonResult(Person person, String message, boolean success){
        this.associatedUsername = person.getAssociatedUsername();
        this.personID = person.getPersonID();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.fatherID = person.getFatherID();
        this.motherID = person.getMotherID();
        this.spouseID = person.getSpouseID();
        this.message = message;
        this.success = success;
    }

    /**
     * PersonResult constructor
     * @param message
     * @param success
     */
    public PersonResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    /**
     * Creates a new PersonResult object
     * @param associatedUsername
     * @param personID
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     * @param message
     * @param success
     * @return result
     */
    public PersonResult person(String associatedUsername, String personID, String firstName, String lastName,
                               String gender,
                               String fatherID, String motherID, String spouseID, String message, boolean success) {
        PersonResult result = new PersonResult();
        result.associatedUsername = associatedUsername;
        result.personID = personID;
        result.firstName = firstName;
        result.lastName = lastName;
        result.gender = gender;
        result.fatherID = fatherID;
        result.motherID = motherID;
        result.spouseID = spouseID;
        result.message = message;
        result.success = success;
        return result;
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Person getPerson() {
        return person;
    }

    public Person getPerson(String personID) {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
