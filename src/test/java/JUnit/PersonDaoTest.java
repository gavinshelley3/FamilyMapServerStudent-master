package JUnit;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {
    private Database db;
    private Person person;
    private PersonDao personDao;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        person = new Person("personID", "associatedUsername", "firstName", "lastName", "gender", "fatherID", "motherID", "spouseID");
        personDao = new PersonDao(db.getConnection());
        personDao.clear();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.closeConnection(false);
    }

    @Test
    void insert() throws Exception {
        try {
            personDao.insert(person);
            Person compareTest = personDao.find(person.getPersonID());
            assertNotNull(compareTest);
            assertEquals(person.getPersonID(), compareTest.getPersonID());
            assertEquals(person.getAssociatedUsername(), compareTest.getAssociatedUsername());
            assertEquals(person.getFirstName(), compareTest.getFirstName());
            assertEquals(person.getLastName(), compareTest.getLastName());
            assertEquals(person.getGender(), compareTest.getGender());
            assertEquals(person.getFatherID(), compareTest.getFatherID());
            assertEquals(person.getMotherID(), compareTest.getMotherID());
            assertEquals(person.getSpouseID(), compareTest.getSpouseID());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertThrows(DataAccessException.class, () -> personDao.insert(person));

    }

    @Test
    void find() throws Exception {
        try {
            personDao.insert(person);
            Person compareTest = personDao.find(person.getPersonID());
            assertNotNull(compareTest);
            assertEquals(person.getPersonID(), compareTest.getPersonID());
            assertEquals(person.getAssociatedUsername(), compareTest.getAssociatedUsername());
            assertEquals(person.getFirstName(), compareTest.getFirstName());
            assertEquals(person.getLastName(), compareTest.getLastName());
            assertEquals(person.getGender(), compareTest.getGender());
            assertEquals(person.getFatherID(), compareTest.getFatherID());
            assertEquals(person.getMotherID(), compareTest.getMotherID());
            assertEquals(person.getSpouseID(), compareTest.getSpouseID());
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertThrows(DataAccessException.class, () -> personDao.find(null));

    }

    @Test
    void clear() throws Exception {
        try {
            personDao.insert(person);
            personDao.clear();
            Person compareTest = personDao.find(person.getPersonID());
            assertNull(compareTest);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}