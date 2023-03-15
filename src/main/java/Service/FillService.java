package Service;

import DataAccess.*;
import Model.User;
import Request.FillRequest;
import Result.FillResult;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * FillService class
 */
public class FillService {
    //Populates the server's database with generated data for the specified username. The required "username" parameter must be a user already registered with the server. If there is any data in the database already associated with the given username, it is deleted.
    //The optional "generations" parameter lets the caller specify the number of generations of ancestors to be generated, and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events).
    //More details can be found in the earlier section titled “Family History Information Generation”
    // Fill database with data for the specified user's ancestors
    // Return success

    /**
     * FillService constructor
     */
    public FillService() {

    }

    /**
     * Fills the database with data for the specified user's ancestors
     * @param request
     * @return fillResult
     * @throws DataAccessException
     */
    public FillResult fill(FillRequest request) throws DataAccessException {
        Database db = new Database();
        int generations = 4;

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            UserDao userDao = new UserDao(conn);
            User user = userDao.find(request.getUsername());
            clear(request, conn);
            FamilyTreeGenerator familyTreeGenerator = new FamilyTreeGenerator();
            familyTreeGenerator.generateFamilyTree(request.getUsername(), user.getGender(), generations, user, conn);

            FillResult result = new FillResult("Successfully added " + familyTreeGenerator.getPeopleCount() + " " +
                    "persons and " + familyTreeGenerator.getEventCount() + " events to the database.", true);
            db.closeConnection(true);
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            FillResult result = new FillResult("Error: " + e.getMessage(),false);
            db.closeConnection(true);
            return result;
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Fills the database with the specified number of generations
     * @param request
     * @return result
     * @throws DataAccessException
     */
    public FillResult fillGenerations(FillRequest request) throws DataAccessException {
        if (request.getGenerations() < 0) {
            throw new DataAccessException("Generations must be a positive integer");
        }

        Database db = new Database();
        try {
            db.openConnection();
            Connection conn = db.getConnection();
            UserDao userDao = new UserDao(conn);
            User user = userDao.find(request.getUsername());

            if (user == null) {
                throw new DataAccessException("User not found");
            }

            clear(request, conn);
            FamilyTreeGenerator familyTreeGenerator = new FamilyTreeGenerator();
            familyTreeGenerator.generateFamilyTree(request.getUsername(), user.getGender(), request.getGenerations(), user, conn);
            FillResult result = new FillResult("Successfully added " + familyTreeGenerator.getPeopleCount() + " " +
                    "persons and " + familyTreeGenerator.getEventCount() + " events to the database.", true);
            db.closeConnection(true);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            FillResult result = new FillResult("Error: " + e.getMessage(), false);
            db.closeConnection(false);
            return result;
        }
    }

    /**
     * Clears the database
     * @param request
     * @param conn
     * @throws DataAccessException
     * @throws SQLException
     */

    public void clear(FillRequest request, Connection conn) throws DataAccessException, SQLException {
        try{
            PersonDao personDao = new PersonDao(conn);
            EventDao eventDao = new EventDao(conn);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);

            personDao.deleteAll(request.getUsername());
            eventDao.delete(request.getUsername());
            authTokenDao.clear();

            conn.commit();
        }
        catch (DataAccessException e) {
            conn.rollback();
            e.printStackTrace();
        }
    }

}
