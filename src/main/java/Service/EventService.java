package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.AuthToken;
import Model.Event;
import Request.EventRequest;
import Result.EventResult;


import java.sql.Connection;
/**
 * EventService class
 */
public class EventService {
    /**
     * EventService constructor
     */
    public EventService() {

    }
    // Returns the single Event object with the specified ID (if the event is associated with the current user).
    // The current user is determined by the provided authtoken.
    // Returns ALL events for ALL family members of the current user.
    // The current user is determined from the provided authtoken.
    /**
     * Gets all events for all family members of the current user. The current user is
     * determined from the provided auth token.
     * @param eventRequest
     * @return eventResult
     */

    public EventResult getEvents(EventRequest eventRequest) {
        EventResult result = new EventResult();
        Database db = new Database();
        try{
            db.openConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());
            String associatedUsername;
            if(authTokenDao.find(eventRequest.getAuthtoken()) != null){
                associatedUsername = authTokenDao.find(eventRequest.getAuthtoken()).getUsername();
                result.setData(eventDao.findAll(associatedUsername));
                result.setSuccess(true);
                result.setMessage("Successfully found events");
            } else{
                db.closeConnection(false);
                result.setMessage("Error: Invalid auth token");
                result.setSuccess(false);
                return result;
            }
        }catch(DataAccessException e){
            result.setSuccess(false);
            result.setMessage("Error: Internal server error");
            db.closeConnection(false);
            return result;
        }
        db.closeConnection(true);
        return result;
    }

    /**
     * Gets the single event object with the specified ID (if the event is associated with the current user).
     * @param eventRequest
     * @return eventResult
     */
    public EventResult getEvent(EventRequest eventRequest) {
        Database db = new Database();
        try {
            db.openConnection();
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            EventDao eventDao = new EventDao(conn);
            EventResult eventResult = new EventResult();
            if (authTokenDao.find(eventRequest.getAuthtoken()) != null) {
                Event event = eventDao.find(eventRequest.getEventID());
                if (event != null) {
                    AuthToken authToken = authTokenDao.find(eventRequest.getAuthtoken());
                    if (event.getAssociatedUsername().equals(authToken.getUsername())) {
                        eventResult.setSuccess(true);
                        eventResult.setAssociatedUsername(event.getAssociatedUsername());
                        eventResult.setCity(event.getCity());
                        eventResult.setCountry(event.getCountry());
                        eventResult.setEventID(event.getEventID());
                        eventResult.setEventType(event.getEventType());
                        eventResult.setLatitude(event.getLatitude());
                        eventResult.setLongitude(event.getLongitude());
                        eventResult.setPersonID(event.getPersonID());
                        eventResult.setYear(event.getYear());

                        db.closeConnection(true);
                    }
                    else {
                        eventResult.setSuccess(false);
                        eventResult.setMessage("Error: Event does not belong to this user");
                        db.closeConnection(false);
                    }
                }
                else {
                    db.closeConnection(false);
                    eventResult.setMessage("Error: Event not found");
                    eventResult.setSuccess(false);

                }
            }
            else {
                db.closeConnection(false);
                eventResult.setSuccess(false);
                eventResult.setMessage("Error: Event does not exist");
            }
            return eventResult;
        }
        catch (Exception e) {
            e.printStackTrace();
            EventResult eventResult = new EventResult("Error: " + e.getMessage(), false);
            db.closeConnection(false);
            return eventResult;
        }
    }
}
