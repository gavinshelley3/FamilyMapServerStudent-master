package DataAccess;

import Model.User;
import org.sqlite.SQLiteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * UserDao class
 */
public class UserDao {
    /**
     * The connection to the database
     */
    private final Connection conn;
    /**
     * UserDao constructor
     * @param conn the connection to the database
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new user into the database
     * @param user the user to be inserted
     * @throws DataAccessException if an error occurs while accessing the database
     */
    public void insert(User user) throws DataAccessException {
        if (user == null) {
            throw new DataAccessException("User cannot be null");
        }
        String sql = "INSERT INTO UserTable (username, password, email, firstName, lastName, gender, personID) " +
                "VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Finds a user in the database
     * @param username the username of the user to be found
     * @return the user with the given username
     * @throws DataAccessException if an error occurs while accessing the database
     */
    public User find(String username) throws DataAccessException {
        if (username == null) {
            throw new DataAccessException("Username cannot be null");
        }
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM UserTable WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"), rs.getString("personID"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        }
    }

    /**
     * Clears all users from the database
     * @throws DataAccessException if an error occurs while accessing the database
     */
    public void clear() throws DataAccessException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM UserTable")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("SQL Error encountered while clearing UserTable");
        }
    }
}
