package Service;

import DataAccess.AuthTokenDao;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Person;
import Request.PersonRequest;
import Result.PersonResult;

import java.sql.Connection;

/**
 * PersonService class
 */
public class PersonService {
    //Returns the single Person object with the specified ID (if the person is associated with the current user). The current user is determined by the provided authtoken.

    /**
     * PersonService constructor
     */
    public PersonService() {}

    /**
     * Gets the person with the specified ID (if the person is associated with the current user).
     * @param request
     * @return result
     */
    public PersonResult getPerson(PersonRequest request) {
        Database db = new Database();
        try {
            db.openConnection();
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            PersonDao personDao = new PersonDao(conn);

            if (authTokenDao.find(request.getAuthtoken()) != null){
                Person person = personDao.find(request.getPersonID());
                AuthToken authToken = authTokenDao.find(request.getAuthtoken());
                if (person != null) {
                    PersonResult result;
                    if (person.getAssociatedUsername().equals(authToken.getUsername())) {
                        result = new PersonResult(person, "Successfully found person", true);
                        db.closeConnection(true);
                    }
                    else {
                        result = new PersonResult("Error: Person does not belong to user", false);
                        db.closeConnection(false);
                    }
                    return result;
                }
                else {
                    PersonResult result = new PersonResult("Error: Person not found", false);
                    db.closeConnection(false);
                    return result;
                }
            }
            else {
                PersonResult result = new PersonResult("Error: Invalid authtoken", false);
                db.closeConnection(false);
                return result;
            }
        }
        catch (Exception e) {
            PersonResult result = new PersonResult("Error: Internal server error", false);
            db.closeConnection(false);
            return result;
        }
    }

    /**
     * Gets all persons for all family members of the current user. The current user is
     * @param request
     * @return result
     */
    public PersonResult getPersons(PersonRequest request) {
        Database db = new Database();
        try {
            db.openConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            String associatedUsername;
            if (authTokenDao.find(request.getAuthtoken()) != null) {
                associatedUsername = authTokenDao.find(request.getAuthtoken()).getUsername();
                PersonResult result = new PersonResult(personDao.findAll(associatedUsername), "Successfully returned " +
                        "all family members of the current user.", true);
                db.closeConnection(true);
                return result;
            } else{
                PersonResult result = new PersonResult("Error: Invalid authtoken", false);
                db.closeConnection(true);
                return result;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            PersonResult result = new PersonResult("Error: " + e.getMessage(), false);
            db.closeConnection(false);
            return result;
        }
    }


}
