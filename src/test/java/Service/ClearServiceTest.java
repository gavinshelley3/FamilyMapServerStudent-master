package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.ClearRequest;
import Request.LoadRequest;
import Request.LoginRequest;
import Result.ClearResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClearServiceTest {
    private Database db;


    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        db = new Database();
        Connection conn = db.getConnection();

        UserDao userDao = new UserDao(conn);
        PersonDao personDao = new PersonDao(conn);
        EventDao eventDao = new EventDao(conn);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);

        userDao.clear();
        personDao.clear();
        eventDao.clear();
        authTokenDao.clear();

        conn.commit();

        User user = new User("username", "password", "email", "firstName", "lastName", "m", "personID");
        Person person = new Person("firstName", "lastName", "m", "personID", "fatherID", "motherID", "spouseID", "associatedUsername");
        Event event = new Event("eventType", "personID", "city", "country", 0, 0, 0, "eventID", "associatedUsername");


        Person[] persons = new Person[1];
        Event[] events = new Event[1];
        User[] users = new User[1];

        persons[0] = person;
        events[0] = event;
        users[0] = user;

        LoadRequest loadRequest = new LoadRequest(users, persons, events);
        LoadService loadService = new LoadService();
        loadService.load(loadRequest);

        LoginRequest loginRequest = new LoginRequest("username", "password");
        LoginService loginService = new LoginService();
        loginService.login(loginRequest);
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(false);
    }

    @Test
    void clear() throws DataAccessException {
        ClearRequest clearRequest = new ClearRequest();
        ClearService clearService = new ClearService();
        ClearResult clearResult = clearService.clear(clearRequest);
        String message = clearResult.getMessage();
        boolean success = clearResult.isSuccess();
        assertTrue(success);
        assertEquals("Clear succeeded.", message);
    }
}