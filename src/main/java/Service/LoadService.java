package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;

/**
 * LoadService class
 */
public class LoadService {
    //Clears all data from the database (just like the /clear API)
    //Loads the user, person, and event data from the request body into the database.

    /**
     * LoadService constructor
     */
    public LoadService() {

    }

    /**
     * Loads the user, person, and event data from the request body into the database.
     * @param request
     * @return result
     * @throws DataAccessException
     */
    public LoadResult load(LoadRequest request) throws DataAccessException {
        LoadResult result = new LoadResult();
        Database db = new Database();
        try {
            db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());
            clear();
            for (User user : request.getUsers()) {
                if (user != null) {
                    userDao.insert(user);
                }
            }
            for (Person person : request.getPersons()) {
                if (person != null) {
                    personDao.insert(person);
                }
            }
            for (Event event : request.getEvents()) {
                if (event != null) {
                    eventDao.insert(event);
                }
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

    /**
     * Clears the database
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        Database db = new Database();
        try {
            db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());
            userDao.clear();
            personDao.clear();
            eventDao.clear();
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }
    }

}
