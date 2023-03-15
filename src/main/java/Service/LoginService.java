package Service;

import DataAccess.AuthTokenDao;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.AuthToken;
import Request.LoginRequest;
import Result.LoginResult;

import java.util.UUID;

/**
 * LoginService class
 */
public class LoginService {
    //Logs the user in
    //Returns an authtoken

    // Check if username and password match
    // If so, create new auth token
    // Return success

    /**
     * LoginService constructor
     */
    public LoginService() {

    }

    /**
     * Logs the user in
     * @param request
     * @return result
     */
    public LoginResult login(LoginRequest request) {
        LoginResult result = new LoginResult();
        Database db = new Database();
        UUID uuid = UUID.randomUUID();
        String authtokenString = uuid.toString();
        AuthToken authtoken = new AuthToken(authtokenString, request.getUsername());

        try{
            db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            if (userDao.find(request.getUsername()) != null) {
                if (userDao.find(request.getUsername()).getPassword().equals(request.getPassword())) {
                    authTokenDao.insert(authtoken);
                    result.setAuthtoken(authtokenString);
                    result.setSuccess(true);
                    result.setMessage("Successfully logged in.");
                    result.setUsername(request.getUsername());
                    result.setPersonID(userDao.find(request.getUsername()).getPersonID());
                    db.closeConnection(true);
                } else {
                    result.setSuccess(false);
                    result.setMessage("Error: Incorrect password.");
                    db.closeConnection(false);
                }
            } else {
                result.setSuccess(false);
                result.setMessage("Error: User does not exist.");
                db.closeConnection(false);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("Error: " + e.getMessage());
            db.closeConnection(false);
        }
        return result;
    }

}
