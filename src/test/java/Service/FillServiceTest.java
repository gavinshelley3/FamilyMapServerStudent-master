package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Request.FillRequest;
import Result.FillResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {
    private Database db;
    private Connection conn;
    private FillRequest fillRequest;
    private FillService fillService;
    private FillResult fillResult;
    private UserDao userDao;
    private PersonDao personDao;


    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        db = new Database();
        conn = db.getConnection();
        fillRequest = new FillRequest("username", 4);
        fillService = new FillService();
        userDao = new UserDao(conn);
        personDao = new PersonDao(conn);
        EventDao eventDao = new EventDao(conn);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        userDao.clear();
        personDao.clear();
        eventDao.clear();
        authTokenDao.clear();
        User user = new User("username", "password", "email", "firstName", "lastName", "m", "personID");
        Person person1 = new Person("firstName1", "lastName1", "m", "username", "fatherID1", "motherID1", "spouseID1", "username");
        AuthToken authToken = new AuthToken("authtoken", "username");
        userDao.insert(user);
        personDao.insert(person1);
        authTokenDao.insert(authToken);
        conn.commit();
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(false);
    }

    @Test
    void fillPass() throws DataAccessException {
        fillResult = fillService.fill(fillRequest);
        assertTrue(fillResult.isSuccess());
    }

    @Test
    void fillFail() {
        fillRequest = null;
        assertThrows(NullPointerException.class, () -> fillService.fill(fillRequest));
    }

    @Test
    void fillGenerationsPass() throws DataAccessException {
        fillResult = fillService.fill(fillRequest);
        assertTrue(fillResult.isSuccess());
    }

    @Test
    void fillGenerationsFail() {
        fillRequest = new FillRequest("username", -1);
        assertThrows(DataAccessException.class, () -> fillService.fillGenerations(fillRequest));
    }

    @Test
    void clear() throws DataAccessException, SQLException {
        fillRequest = new FillRequest("username", 4);
        fillResult = fillService.fill(fillRequest);
        assertNotNull(userDao.find(fillRequest.getUsername()));
        fillService.clear(fillRequest, conn);
        assertNull(personDao.find("personID"));
    }
}