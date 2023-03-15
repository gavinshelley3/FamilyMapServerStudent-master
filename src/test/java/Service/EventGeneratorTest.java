package Service;

import DataAccess.DataAccessException;
import Model.Event;
import Model.Person;
import TreeObjects.LocationData;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EventGeneratorTest {
    private final Gson gson = new Gson();
    private LocationData locationData;
    private int year;
    private Person person1;
    private Person person2;
    private Event event1;
    private Event[] events;

    @BeforeEach
    void setUp() throws DataAccessException, FileNotFoundException {
        Reader reader = new FileReader("json/locations.json");
        locationData = gson.fromJson(reader, LocationData.class);
        year = 2000;
        person1 = new Person("firstName1", "lastName1", "m", "personID1", "fatherID1", "motherID1", "spouseID1", "associatedUsername1");
        person2 = new Person("firstName2", "lastName2", "m", "personID2", "fatherID2", "motherID2", "spouseID2", "associatedUsername2");
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void birthPass() {
        event1 = EventGenerator.birth(person1, year, locationData);
        assertEquals(event1.getEventType(), "birth");
        assertEquals(event1.getPersonID(), person1.getPersonID());
        assertEquals(event1.getAssociatedUsername(), person1.getAssociatedUsername());
        assertEquals(event1.getEventID(), person1.getPersonID() + "_birth");
    }

    @Test
    void birthFail() {
        event1 = EventGenerator.birth(person1, year, locationData);
        assertNotEquals(event1.getEventType(), "marriage");
        assertNotEquals(event1.getPersonID(), person2.getPersonID());
        assertNotEquals(event1.getEventID(), person2.getPersonID() + "_birth");
    }


    @Test
    void marriagePass() {
        events = EventGenerator.marriage(person1, person2, year, locationData);
        assertEquals(events[0].getEventType(), "marriage");
        assertEquals(events[1].getEventType(), "marriage");
        assertEquals(events[0].getPersonID(), person1.getPersonID());
        assertEquals(events[1].getPersonID(), person2.getPersonID());
        assertEquals(events[0].getAssociatedUsername(), person1.getAssociatedUsername());
        assertEquals(events[1].getAssociatedUsername(), person2.getAssociatedUsername());
        assertEquals(events[0].getEventID(), person1.getPersonID() + "_marriage");
        assertEquals(events[1].getEventID(), person2.getPersonID() + "_marriage");

    }

    @Test
    void marriageFail() {
        events = EventGenerator.marriage(person1, person2, year, locationData);
        assertNotEquals(events[0].getEventType(), "birth");
        assertNotEquals(events[1].getEventType(), "birth");
        assertNotEquals(events[0].getPersonID(), person2.getPersonID());
        assertNotEquals(events[1].getPersonID(), person1.getPersonID());
        assertNotEquals(events[0].getEventID(), person2.getPersonID() + "_marriage");
        assertNotEquals(events[1].getEventID(), person1.getPersonID() + "_marriage");
    }


    @Test
    void deathPass() {
        events = EventGenerator.death(person1, person2, year, locationData);
        assertEquals(events[0].getEventType(), "death");
        assertEquals(events[1].getEventType(), "death");
        assertEquals(events[0].getPersonID(), person1.getPersonID());
        assertEquals(events[1].getPersonID(), person2.getPersonID());
        assertEquals(events[0].getAssociatedUsername(), person1.getAssociatedUsername());
        assertEquals(events[1].getAssociatedUsername(), person2.getAssociatedUsername());
        assertEquals(events[0].getEventID(), person1.getPersonID() + "_death");
        assertEquals(events[1].getEventID(), person2.getPersonID() + "_death");
    }

    @Test
    void deathFail() {
        events = EventGenerator.death(person1, person2, year, locationData);
        assertNotEquals(events[0].getEventType(), "birth");
        assertNotEquals(events[1].getEventType(), "birth");
        assertNotEquals(events[0].getPersonID(), person2.getPersonID());
        assertNotEquals(events[1].getPersonID(), person1.getPersonID());
        assertNotEquals(events[0].getEventID(), person2.getPersonID() + "_death");
        assertNotEquals(events[1].getEventID(), person1.getPersonID() + "_death");
    }
}