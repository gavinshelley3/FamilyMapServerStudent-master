package Service;

import Model.Event;
import Model.Person;
import TreeObjects.Location;
import TreeObjects.LocationData;
/**
 * EventGenerator class
 */
public class EventGenerator {
    /**
     * Generate a birth event for a person
     * @param person
     * @param year
     * @param locationData
     * @return birth event
     */
    public static Event birth(Person person, int year, LocationData locationData) {
        Location randomLocation = locationData.getRandomLocation();
        String personID = "";
        String associatedUsername = "";
        if (person != null) {
            personID = person.getPersonID();
            associatedUsername = person.getAssociatedUsername();
        }
        return new Event("birth", personID, randomLocation.getCity(),
                randomLocation.getCountry(), randomLocation.getLatitude(), randomLocation.getLongitude(), year,
                personID + "_birth", associatedUsername);
    }

    /**
     * Generate a baptism event for a person
     * @param mom
     * @param dad
     * @param marriageYear
     * @param locationData
     * @return baptism event
     */
    public static Event[] marriage(Person mom, Person dad, int marriageYear, LocationData locationData) {
        Location randomLocationMom = new Location();
        Location randomLocationDad = new Location();
        Event momMarriageEvent = new Event("marriage", mom.getPersonID(), randomLocationMom.getCity(),
                randomLocationMom.getCountry(), randomLocationMom.getLatitude(), randomLocationMom.getLongitude(),
                marriageYear, mom.getPersonID() + "_marriage", mom.getAssociatedUsername());
        Event dadMarriageEvent = new Event("marriage", dad.getPersonID(), randomLocationDad.getCity(),
                randomLocationDad.getCountry(), randomLocationDad.getLatitude(), randomLocationDad.getLongitude(),
                marriageYear, dad.getPersonID() + "_marriage", dad.getAssociatedUsername());
        return new Event[]{momMarriageEvent, dadMarriageEvent};
    }

    /**
     * Generate a death event for a person
     * @param child
     * @param parent
     * @param year
     * @param locationData
     * @return death event
     */
    public static Event[] death(Person child, Person parent, int year, LocationData locationData) {
        Location randomLocationChild = new Location();
        Location randomLocationParent = new Location();
        Event childDeathEvent = new Event("death", child.getPersonID(), randomLocationChild.getCity(),
                randomLocationChild.getCountry(), randomLocationChild.getLatitude(), randomLocationChild.getLongitude(),
                year, child.getPersonID() + "_death", child.getAssociatedUsername());
        Event parentDeathEvent = new Event("death", parent.getPersonID(), randomLocationParent.getCity(),
                randomLocationParent.getCountry(), randomLocationParent.getLatitude(), randomLocationParent.getLongitude(),
                year, parent.getPersonID() + "_death", parent.getAssociatedUsername());
        return new Event[]{childDeathEvent, parentDeathEvent};
    }


}
