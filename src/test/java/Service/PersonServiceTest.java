package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Person;
import Request.PersonRequest;
import Result.PersonResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonServiceTest {
    private Database db;
    private Person person1;
    private PersonRequest personRequest;
    private PersonService personService;
    private PersonResult personResult;


    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        db = new Database();
        Connection conn = db.getConnection();
        PersonDao personDao = new PersonDao(conn);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        personDao.clear();
        authTokenDao.clear();
        AuthToken authToken = new AuthToken("authtoken", "username");
        person1 = new Person("firstName1", "lastName1", "m", "personID1", "fatherID1", "motherID1", "spouseID1", "username");
        Person person2 = new Person("firstName2", "lastName2", "m", "personID2", "fatherID2", "motherID2", "spouseID2", "username");
        personDao.insert(person1);
        personDao.insert(person2);
        authTokenDao.insert(authToken);
        conn.commit();
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(false);
    }

    @Test
    void getPersonPass() {
        personRequest = new PersonRequest("personID1", "authtoken");
        personService = new PersonService();
        personResult = personService.getPerson(personRequest);
        assertEquals(person1.getPersonID(), personResult.getPersonID());
    }

    @Test
    void getPersonFail() {
        personRequest = new PersonRequest("personID3", "authtoken");
        personService = new PersonService();
        personResult = personService.getPerson(personRequest);
        assertEquals("Error: Person not found", personResult.getMessage());
    }

    @Test
    void getPersonsPass() {
        personRequest = new PersonRequest(null, "authtoken");
        personService = new PersonService();
        personResult = personService.getPersons(personRequest);
        assertEquals(2, personResult.getData().length);
    }

    @Test
    void getPersonsFail() {
        personRequest = new PersonRequest(null, "authtoken2");
        personService = new PersonService();
        personResult = personService.getPersons(personRequest);
        assertEquals("Error: Invalid authtoken", personResult.getMessage());
    }
}