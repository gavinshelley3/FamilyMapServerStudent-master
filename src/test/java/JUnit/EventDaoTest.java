package JUnit;

import DataAccess.Database;
import DataAccess.EventDao;
import Model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventDaoTest {
    private Database db;
    private Event event;
    private EventDao eventDAO;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        event = new Event("eventType", "personID", "city", "country", 0, 0, 0, "eventID", "associatedUsername");
        eventDAO = new EventDao(db.getConnection());
        eventDAO.clear();
    }

    @AfterEach
    void tearDown() throws Exception {
        db.closeConnection(false);
    }

    @Test
    void insert() throws Exception {
        try {
            eventDAO.insert(event);
            Event compareTest = eventDAO.find(event.getEventID());
            assertNotNull(compareTest);
            assertEquals(event.getEventType(), compareTest.getEventType());
            assertEquals(event.getPersonID(), compareTest.getPersonID());
            assertEquals(event.getCity(), compareTest.getCity());
            assertEquals(event.getCountry(), compareTest.getCountry());
            assertEquals(event.getLatitude(), compareTest.getLatitude());
            assertEquals(event.getLongitude(), compareTest.getLongitude());
            assertEquals(event.getYear(), compareTest.getYear());
            assertEquals(event.getEventID(), compareTest.getEventID());
            assertEquals(event.getAssociatedUsername(), compareTest.getAssociatedUsername());
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            eventDAO.insert(null);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    void find() throws Exception {
        try {
            eventDAO.insert(event);
            Event compareTest = eventDAO.find(event.getEventID());
            assertNotNull(compareTest);
            assertEquals(event.getEventType(), compareTest.getEventType());
            assertEquals(event.getPersonID(), compareTest.getPersonID());
            assertEquals(event.getCity(), compareTest.getCity());
            assertEquals(event.getCountry(), compareTest.getCountry());
            assertEquals(event.getLatitude(), compareTest.getLatitude());
            assertEquals(event.getLongitude(), compareTest.getLongitude());
            assertEquals(event.getYear(), compareTest.getYear());
            assertEquals(event.getEventID(), compareTest.getEventID());
            assertEquals(event.getAssociatedUsername(), compareTest.getAssociatedUsername());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void clear() throws Exception {
        try {
            eventDAO.insert(event);
            eventDAO.clear();
            Event compareTest = eventDAO.find(event.getEventID());
            assertNull(compareTest);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}