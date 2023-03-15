package Request;

import Model.Event;
import Model.Person;
import Model.User;
/**
 * LoadRequest class
 */
public class LoadRequest {
    /**
     * Users
     */
    private User[] users;
    /**
     * Persons
     */
    private Person[] persons;
    /**
     * Events
     */
    private Event[] events;

    /**
     * LoadRequest constructor
     * @param users
     * @param persons
     * @param events
     */

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
