package Service;

import DataAccess.*;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoginServiceTest {
    RegisterService registerService;
    RegisterRequest registerRequest;
    RegisterResult registerResult;
    LoginService loginService;
    LoginRequest loginRequest;
    LoginResult loginResult;
    Database db;
    UserDao userDao;
    PersonDao personDao;
    EventDao eventDao;
    AuthTokenDao authTokenDao;
    Connection conn;

    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        registerService = new RegisterService();
        loginService = new LoginService();
        registerRequest = new RegisterRequest("username", "password", "email", "firstName", "lastName", "m", "personID");
        loginRequest = new LoginRequest("username", "password");
        db = new Database();
        conn = db.getConnection();
        userDao = new UserDao(conn);
        personDao = new PersonDao(conn);
        eventDao = new EventDao(conn);
        authTokenDao = new AuthTokenDao(conn);
        userDao.clear();
        personDao.clear();
        eventDao.clear();
        authTokenDao.clear();
        conn.commit();
    }

    @AfterEach
    void tearDown() throws DataAccessException, SQLException {
        db.closeConnection(false);
    }

    @Test
    void loginPass() {
        registerResult = registerService.register(registerRequest);
        loginResult = loginService.login(loginRequest);
        assertNotNull(loginResult);
        assertNotNull(loginResult.getAuthtoken());
        assertEquals(registerResult.getPersonID(), loginResult.getPersonID());
    }

    @Test
    void loginFail() {
        registerService.register(registerRequest);
        loginRequest.setPassword("incorrectPassword");
        loginResult = loginService.login(loginRequest);
        assertEquals("Error: Incorrect password.", loginResult.getMessage());
    }
}