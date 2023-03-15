package JUnit;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private Database db;
    private User user;
    private UserDao userDao;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        user = new User("username", "password", "email", "firstName", "lastName", "m", "personID");
        userDao = new UserDao(db.getConnection());
        userDao.clear();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.closeConnection(false);
    }

    @Test
    void insertPass() throws Exception {
        try {
            userDao.insert(user);
            User compareTest = userDao.find(user.getUsername());
            assertNotNull(compareTest);
            assertEquals(user.getUsername(), compareTest.getUsername());
            assertEquals(user.getPassword(), compareTest.getPassword());
            assertEquals(user.getEmail(), compareTest.getEmail());
            assertEquals(user.getFirstName(), compareTest.getFirstName());
            assertEquals(user.getLastName(), compareTest.getLastName());
            assertEquals(user.getGender(), compareTest.getGender());
            assertEquals(user.getPersonID(), compareTest.getPersonID());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void insertFail() throws Exception {
        userDao.insert(user);
        assertThrows(DataAccessException.class, () -> userDao.insert(user));
    }

    @Test
    void findPass() throws Exception {
        try {
            userDao.insert(user);
            User compareTest = userDao.find(user.getUsername());
            assertNotNull(compareTest);
            assertEquals(user.getUsername(), compareTest.getUsername());
            assertEquals(user.getPassword(), compareTest.getPassword());
            assertEquals(user.getEmail(), compareTest.getEmail());
            assertEquals(user.getFirstName(), compareTest.getFirstName());
            assertEquals(user.getLastName(), compareTest.getLastName());
            assertEquals(user.getGender(), compareTest.getGender());
            assertEquals(user.getPersonID(), compareTest.getPersonID());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findFail() throws Exception {
        assertNotEquals(user, userDao.find("notARealUsername"));
    }

    @Test
    void clear() throws Exception {
        try {
            userDao.insert(user);
            userDao.clear();
            User compareTest = userDao.find(user.getUsername());
            assertNull(compareTest);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}