package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FamilyTreeGeneratorTest {
    private FamilyTreeGenerator familyTreeGenerator;
    private int generations;
    private int birthYear;
    private int marriageYear;
    private int deathYear;
    private User user;
    private String gender;
    private PersonDao personDao;
    private EventDao eventDao;
    private Connection connection;


    @BeforeEach
    void setUp() throws FileNotFoundException, DataAccessException {
        familyTreeGenerator = new FamilyTreeGenerator();
        generations = 4;
        birthYear = 1900;
        marriageYear = 1920;
        deathYear = 2000;
        user = new User("person", "password", "email", "person", "mont", "m", "personID");

        Database db = new Database();
        connection = db.openConnection();
        personDao = new PersonDao(connection);
        eventDao = new EventDao(connection);
        personDao.clear();
        eventDao.clear();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }


    @Test
    void generateDataPass() {
        familyTreeGenerator.generateData();
        assertNotNull(familyTreeGenerator);
    }


    @Test
    void generateFamilyTreePass() throws FileNotFoundException {
        familyTreeGenerator.generateFamilyTree(user.getUsername(), gender, generations, user, connection);
        assertNotNull(familyTreeGenerator);
        assertNotNull(personDao);
        assertNotNull(eventDao);
        assert (familyTreeGenerator.getPeopleCount() > 0);
        assert (familyTreeGenerator.getEventCount() > 0);
    }

    @Test
    void generateFamilyTreeFail() throws FileNotFoundException {
        familyTreeGenerator.generateFamilyTree(user.getUsername(), "4", -10, user, connection);
        assertEquals(0, familyTreeGenerator.getPeopleCount());
        assertEquals(0, familyTreeGenerator.getEventCount());
    }

    @Test
    void generatePeoplePass() throws DataAccessException {
        familyTreeGenerator.generateData();
        familyTreeGenerator.generatePeople(user, user.getUsername(), gender, generations, birthYear, marriageYear, deathYear, connection);
        assertNotNull(familyTreeGenerator);
        assertNotNull(personDao);
        assertNotNull(eventDao);
    }
}