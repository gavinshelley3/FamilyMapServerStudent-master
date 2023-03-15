package DataAccess;

import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * PersonDao class
 */
public class PersonDao {
    /**
     * The connection to the database
     */
    private final Connection conn;

    /**
     * PersonDao constructor
     * @param conn
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new person into the database
     * @param person the person to be inserted
     * @throws DataAccessException if an error occurs while accessing the database
     */
    public void insert(Person person) throws DataAccessException {
        if (person == null) {
            throw new DataAccessException("Person cannot be null");
        }
        if (find(person.getPersonID()) != null) {
            throw new DataAccessException("Person already exists");
        }
        String sql = "INSERT INTO PersonTable (firstName, lastName, gender, personID, fatherID, motherID, " +
                "spouseID, associatedUsername) " +
                "VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getGender());
            statement.setString(4, person.getPersonID());
            statement.setString(5, person.getFatherID());
            statement.setString(6, person.getMotherID());
            statement.setString(7, person.getSpouseID());
            statement.setString(8, person.getAssociatedUsername());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }


    /**
     * Finds a person in the database
     * @param personID the personID of the person to be found
     * @return the person with the given personID
     * @throws DataAccessException if an error occurs while accessing the database
     */
    public Person find(String personID) throws DataAccessException {
        if (personID == null) {
            throw new DataAccessException("PersonID cannot be null");
        }

        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM PersonTable WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"), rs.getString("fatherID"), rs.getString("motherID"), rs.getString(
                        "spouseID"), rs.getString("associatedUsername"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        }
    }
    /**
     * Finds all persons in the database
     * @return an array of all persons in the database
     * @throws DataAccessException if an error occurs while accessing the database
     */
    public Person[] findAll(String associatedUsername) throws DataAccessException {
        if (associatedUsername == null || associatedUsername.equals("")) {
            throw new DataAccessException("associatedUsername is null");
        }
        Person[] personArray;
        ArrayList<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM PersonTable WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            ResultSet rs;
            rs = stmt.executeQuery();
            Person person;
            while (rs.next()) {
                person = new Person(rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"), rs.getString("fatherID"), rs.getString("motherID"), rs.getString(
                        "spouseID"), rs.getString("associatedUsername"));
                persons.add(person);//add person to array
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        }
        personArray = persons.toArray(new Person[0]);
        if(personArray.length == 0){
            return null;
        }
        return personArray;
    }

    /**
     * Deletes all persons from the database
     * @throws DataAccessException if an error occurs while accessing the database
     */
    public void clear() throws DataAccessException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM PersonTable")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("SQL Error encountered while clearing PersonTable");
        }
    }

    /**
     * Deletes all persons from the database
     * @throws DataAccessException if an error occurs while accessing the database
     */
    public void deleteAll(String username) throws DataAccessException {
        if (username == null || username.equals("")) {
            throw new DataAccessException("username is null");
        }
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM PersonTable WHERE associatedUsername = ?")) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("SQL Error encountered while clearing PersonTable");
        }
    }
    /**
     * Updates the spouseID of a person in the database
     * @throws DataAccessException if an error occurs while accessing the database
     */
    public void insertSpouseID(String spouseID, String personID) throws DataAccessException {
        String sql = "UPDATE PersonTable SET spouseID = ? WHERE personID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, spouseID);
            stmt.setString(2, personID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting spouseID into the database");
        }
    }
}
