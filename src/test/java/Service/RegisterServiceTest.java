package Service;

import DataAccess.*;
import Request.RegisterRequest;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RegisterServiceTest {
    Database db;
    Connection conn;
    RegisterRequest registerRequest;
    RegisterService registerService;

    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        db = new Database();
        conn = db.getConnection();
        registerService = new RegisterService();
        registerRequest = new RegisterRequest("username", "password", "email", "firstName", "lastName", "m", "personID");
        UserDao userDao = new UserDao(conn);
        PersonDao personDao = new PersonDao(conn);
        EventDao eventDao = new EventDao(conn);
        userDao.clear();
        personDao.clear();
        eventDao.clear();
        conn.commit();
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(false);
    }

    @Test
    void registerPass() {
        RegisterResult registerResult = registerService.register(registerRequest);
        assertNotNull(registerResult);
        assertEquals(registerRequest.getUsername(), registerResult.getUsername());
    }

    @Test
    void registerFail() {
        RegisterResult registerResult = registerService.register(registerRequest);
        assertNotNull(registerResult);
        assertEquals(registerRequest.getUsername(), registerResult.getUsername());
        RegisterResult registerResult2 = registerService.register(registerRequest);
        assertEquals("Error: Username already exists.", registerResult2.getMessage());
    }
}