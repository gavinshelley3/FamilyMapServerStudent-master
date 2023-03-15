package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest {
    EventDao eDao;
    PersonDao pDao;
    UserDao uDao;
    private LoadService loadService;
    private Database db;
    private LoadRequest loadRequest;
    private LoadResult loadResult;
    private User[] users;
    private Person[] persons;
    private Event[] events;
    private Event event;
    private Person person;
    private User user;

    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        db = new Database();
        Connection conn = db.getConnection();

        user = new User("username", "password", "email", "firstName", "lastName", "m", "personID");
        person = new Person("firstName", "lastName", "m", "personID", "fatherID", "motherID", "spouseID",
                "associatedUsername");
        event = new Event("eventType", "personID", "city", "country", 0, 0, 0, "eventID", "associatedUsername");

        users = new User[1];
        persons = new Person[1];
        events = new Event[1];

        users[0] = user;
        persons[0] = person;
        events[0] = event;

        uDao = new UserDao(conn);
        pDao = new PersonDao(conn);
        eDao = new EventDao(conn);

        uDao.clear();
        pDao.clear();
        eDao.clear();

        conn.commit();
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(false);
    }

    @Test
    void clearPass() throws DataAccessException {
        loadRequest = new LoadRequest(users, persons, events);
        loadService = new LoadService();
        loadResult = loadService.load(loadRequest);
        loadService.clear();
        assertNull(uDao.find(user.getUsername()));
        assertNull(pDao.find(person.getPersonID()));
        assertNull(eDao.find(event.getEventID()));
    }


    @Test
    void loadPass() throws DataAccessException {
        loadRequest = new LoadRequest(users, persons, events);
        loadService = new LoadService();
        loadResult = loadService.load(loadRequest);
        assertNotNull(loadResult);
        assertEquals(loadResult.getMessage(), "Successfully added 1 users, 1 persons, and 1 events to the database.");
    }

    @Test
    void loadFail() throws DataAccessException {
        persons[0] = null;
        loadRequest = new LoadRequest(users, persons, events);
        loadService = new LoadService();
        loadResult = loadService.load(loadRequest);
        assertNotEquals("Error: Person cannot be null", loadResult.getMessage());
    }
}