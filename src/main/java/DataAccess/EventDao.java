package DataAccess;

import Model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * EventDao class
 */
public class EventDao {
    /**
     * The connection to the database
     */
    private final Connection conn;
    /**
     * EventDao constructor
     * @param conn the connection to the database
     */
    public EventDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new event into the database
     * @param event the event to be inserted
     * @throws DataAccessException if an error occurs while accessing the database
     */
    public void insert(Event event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO EventTable (eventType, personID, city, country, latitude, " +
                "longitude, year, eventID, associatedUsername) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventType());
            stmt.setString(2, event.getPersonID());
            stmt.setString(3, event.getCity());
            stmt.setString(4, event.getCountry());
            stmt.setFloat(5, event.getLatitude());
            stmt.setFloat(6, event.getLongitude());
            stmt.setInt(7, event.getYear());
            stmt.setString(8, event.getEventID());
            stmt.setString(9, event.getAssociatedUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    /**
     * Finds an event in the database
     * @param eventID the eventID of the event to be found
     * @return the event with the given eventID
     * @throws DataAccessException if an error occurs while accessing the database
     */
    public Event find(String eventID) throws DataAccessException {
        if (eventID == null) {
            throw new DataAccessException("EventID cannot be null");
        }
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM EventTable WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventType"), rs.getString("personID"),
                        rs.getString("city"), rs.getString("country"), rs.getFloat("latitude"),
                        rs.getFloat("longitude"), rs.getInt("year"), rs.getString("eventID"),
                        rs.getString("associatedUsername"));
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }

    }

    /**
     * Deletes all events from the database
     * @throws DataAccessException if an error occurs while accessing the database
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM EventTable";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    /**
     * Deletes all events associated with a given username
     * @param username the username of the events to be deleted
     * @throws DataAccessException if an error occurs while accessing the database
     */
    public void delete(String username) throws DataAccessException {
        String sql = "DELETE FROM EventTable WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting events from the database");
        }
    }

    /**
     * Finds all events associated with a given username
     * @param associatedUsername
     * @return an array of events associated with the given username
     * @throws DataAccessException
     */
    public Event[] findAll(String associatedUsername) throws DataAccessException {
        if(associatedUsername == null){
            throw new DataAccessException("AssociatedUsername is null");
        }
        Event event;
        Event [] events;
        ArrayList<Event> eventList = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM EventTable WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            while (rs.next()) {
                event = new Event(rs.getString("eventType"), rs.getString("personID"),
                        rs.getString("city"), rs.getString("country"), rs.getFloat("latitude"),
                        rs.getFloat("longitude"), rs.getInt("year"), rs.getString("eventID"),
                        rs.getString("associatedUsername"));
                eventList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
        events = eventList.toArray(new Event[eventList.size()]);
        return events;
    }
}
