package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Request.RegisterRequest;
import Result.LoadResult;
import Result.RegisterResult;

import java.sql.Connection;

/**
 * RegisterService class
 */
public class RegisterService {
    //Creates a new user account (user row in the database)
    //Generates 4 generations of ancestor data for the new user (just like the /fill endpoint if called with a
    // generations value of 4 and this new user’s username as parameters)
    //Logs the user in
    //Returns an authtoken

    /**
     * RegisterService constructor
     */
    public RegisterService() {

    }

    /**
     * Creates a new user account (user row in the database)
     * @param request
     * @return result
     */
    public RegisterResult register(RegisterRequest request) {
        RegisterResult result = new RegisterResult();
        Database db = new Database();
        try {
            db.openConnection();
            Connection conn = db.getConnection();
            UserDao userDao = new UserDao(conn);
            new PersonDao(conn);
            new EventDao(conn);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            User user = new User(request.getUsername(), request.getPassword(), request.getEmail(),
                    request.getFirstName(), request.getLastName(), request.getGender(), request.generatePersonID());
            if (userDao.find(request.getUsername()) == null) {
                userDao.insert(user);
                result.setSuccess(true);
                result.generateAuthtoken();
                AuthToken authToken = new AuthToken(result.generateAuthtoken(), user.getUsername());
                authTokenDao.insert(authToken);
                result.setAuthtoken(authToken.getAuthtoken());
                result.setSuccess(true);
                result.setMessage("Successfully added user to the database.");
                result.setUsername(request.getUsername());
                result.setPersonID(request.generatePersonID());

                int generations = 4;
                FamilyTreeGenerator familyTreeGenerator = new FamilyTreeGenerator();
                familyTreeGenerator.generateFamilyTree(request.getUsername(),request.getGender(), generations, user,
                        conn);
                db.closeConnection(true);
            } else {
                db.closeConnection(false);
                result.setSuccess(false);
                result.setMessage("Error: Username already exists.");
            }
        }
        catch (Exception e) {
            db.closeConnection(false);
            e.printStackTrace();
            result.setAuthtoken(null);
            result.setPersonID(null);
            result.setUsername(null);
            result.setSuccess(false);
            result.setMessage("Error: " + e.getMessage());
        }


        return result;
    }

    /**
     * Generates 4 generations of ancestor data for the new user (just like the /fill endpoint if called with a
     * generations value of 4 and this new user’s username as parameters)
     * @param request
     * @return result
     */
    public LoadResult load(LoadRequest request) {
        LoadResult result = new LoadResult();
        Database db = new Database();
        try {
            db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());
            for (User user : request.getUsers()) {
                userDao.insert(user);
            }
            for (Person person : request.getPersons()) {
                personDao.insert(person);
            }
            for (Event event : request.getEvents()) {
                eventDao.insert(event);
            }
            db.closeConnection(true);
            result.setMessage("Successfully added " + request.getUsers().length + " users, " + request.getPersons().length + " persons, and " + request.getEvents().length + " events to the database.");
            result.setSuccess(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            result.setMessage("Error: " + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

}
