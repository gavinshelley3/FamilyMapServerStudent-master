package Service;

import DataAccess.*;
import Request.ClearRequest;
import Result.ClearResult;

import java.sql.Connection;

/**
 * ClearService class
 */
public class ClearService {
    //Deletes ALL data from the database, including user, authtoken, person, and event data

    /**
     * ClearService constructor
     */
    public ClearService() {

    }

    /**
     * Clears the database
     * @param request
     * @return clearResult
     * @throws DataAccessException
     */
    public ClearResult clear(ClearRequest request) throws DataAccessException {
        Database db = new Database();
        ClearResult clearResult = new ClearResult();
        try {
            db.openConnection();
            Connection conn = db.getConnection();
            UserDao userDao = new UserDao(conn);
            PersonDao personDao = new PersonDao(conn);
            EventDao eventDao = new EventDao(conn);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            userDao.clear();
            personDao.clear();
            eventDao.clear();
            authTokenDao.clear();
            db.closeConnection(true);
            clearResult.setMessage("Clear succeeded.");
            clearResult.setSuccess(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            clearResult.setMessage("Error: " + e.getMessage());
            clearResult.setSuccess(false);
        }
        return clearResult;
    }

}
