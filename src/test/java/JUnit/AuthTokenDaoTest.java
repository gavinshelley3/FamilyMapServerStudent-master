package JUnit;

import DataAccess.AuthTokenDao;
import DataAccess.Database;
import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDaoTest {
    private Database db;
    private AuthToken authToken;
    private AuthTokenDao authTokenDao;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        authToken = new AuthToken("username", "authToken");
        authTokenDao = new AuthTokenDao(db.getConnection());
        authTokenDao.clear();
    }

    @AfterEach
    void tearDown() throws Exception {
        db.closeConnection(false);
    }

    @Test
    void insert() throws Exception {
        try {
            authTokenDao.insert(authToken);
            AuthToken compareTest = authTokenDao.find(authToken.getAuthtoken());
            assertNotNull(compareTest);
            assertEquals(authToken.getAuthtoken(), compareTest.getAuthtoken());
            assertEquals(authToken.getUsername(), compareTest.getUsername());
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            authTokenDao.insert(null);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    void find() throws Exception {
        try {
            authTokenDao.insert(authToken);
            AuthToken compareTest = authTokenDao.find(authToken.getAuthtoken());
            assertNotNull(compareTest);
            assertEquals(authToken.getAuthtoken(), compareTest.getAuthtoken());
            assertEquals(authToken.getUsername(), compareTest.getUsername());
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            authTokenDao.find(null);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    void clear() throws Exception {
        try {
            authTokenDao.insert(authToken);
            authTokenDao.clear();
            AuthToken compareTest = authTokenDao.find(authToken.getAuthtoken());
            assertNull(compareTest);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}