package Service;

import DataAccess.DataAccessException;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import Model.Event;
import Model.Person;
import Model.User;
import TreeObjects.LocationData;
import TreeObjects.fnames;
import TreeObjects.mnames;
import TreeObjects.snames;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;

/**
 * FamilyTreeGenerator class
 */
public class FamilyTreeGenerator {
    /**
     * Gson object used to parse json files
     */
    Gson gson = new Gson();
    /**
     * peopleCount is the number of people in the family tree
     */
    int peopleCount = 0;
    /**
     * eventCount is the number of events in the family tree
     */
    int eventCount = 0;
    /**
     * initialGeneration is the number of generations in the family tree
     */
    int initialGeneration;
    /**
     * locationData is the location data for the family tree
     */
    LocationData locationData = new LocationData();
    /**
     * snames surNames is the surname data for the family tree
     */
    snames surNames = new snames();
    /**
     * fnames femaleNames is the female name data for the family tree
     */
    fnames femaleNames = new fnames();
    /**
     * mnames maleNames is the male name data for the family tree
     */
    mnames maleNames = new mnames();

    /**
     * FamilyTreeGenerator constructor
     */
    public FamilyTreeGenerator() {

    }

    /**
     * Generates a family tree for a user
     */
    public void generateData() {
        try {
            Reader reader = new FileReader("json/locations.json");
            locationData = gson.fromJson(reader, LocationData.class);

            Reader readerSurnames = new FileReader("json/snames.json");
            surNames = gson.fromJson(readerSurnames, snames.class);

            Reader readerFemaleNames = new FileReader("json/fnames.json");
            femaleNames = gson.fromJson(readerFemaleNames, fnames.class);

            Reader readerMaleNames = new FileReader("json/mnames.json");
            maleNames = gson.fromJson(readerMaleNames, mnames.class);

            System.out.println("Files found");
        } catch (FileNotFoundException e) {
            System.out.println("Files not found");
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a person
     * @param gender
     * @param generations
     * @return person
     */
    public Person generatePerson(String gender, int generations) {
        Person mother = null;
        Person father = null;
        if (generations > 1) {
            mother = generatePerson("f", generations - 1);
            father = generatePerson("m", generations - 1);
        }
        return new Person();
    }

    /**
     * Generates a person's family tree
     * @param username
     * @param gender
     * @param generations
     * @param user
     * @param conn
     * @throws FileNotFoundException
     */
    public void generateFamilyTree(String username, String gender, int generations, User user, Connection conn) throws FileNotFoundException {
        System.out.println("Generating family tree...");
        if (generations > 0 || gender.equals("f") || gender.equals("m")) {
            try {
                generateData();
                int birthYear = 1999;
                int marriageYear = 2024;
                int deathYear = 2075;
                initialGeneration = generations;
                Person person = generatePeople(user, username, gender, generations, birthYear, marriageYear,
                        deathYear, conn);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error: error generating family tree");
            }
        }
        else {
            System.out.println("Error: error generating family tree");
        }
    }

    /**
     * Generates a person's parents
     * @param user
     * @param username
     * @param gender
     * @param generations
     * @param birthYear
     * @param marriageYear
     * @param deathYear
     * @param conn
     * @return Person
     * @throws DataAccessException
     */
    public Person generatePeople(User user, String username, String gender, int generations, int birthYear,
                                 int marriageYear, int deathYear, Connection conn) throws DataAccessException {
        try {
            PersonDao personDao = new PersonDao(conn);
            EventDao eventDao = new EventDao(conn);
            Person mom = null;
            Person dad = null;

            Person person = new Person();

            if (generations >= 1) {
                int randMom = generateRandom();
                int randDad = generateRandom();
                int randMarriage = generateRandom();

                mom = generatePeople(user, username,"f", generations - 1, birthYear - randMom,
                        marriageYear - randMarriage, deathYear - randMom, conn);
                dad = generatePeople(user, username,"m", generations - 1, birthYear - randDad,
                        marriageYear - randMarriage, deathYear - randDad, conn);
                mom.setSpouseID(dad.getPersonID());
                dad.setSpouseID(mom.getPersonID());

                Event[] weddings = EventGenerator.marriage(mom, dad, marriageYear, locationData);
                eventCount += 2;

                personDao.insertSpouseID(mom.getSpouseID(), mom.getPersonID());
                personDao.insertSpouseID(dad.getSpouseID(), dad.getPersonID());

                eventDao.insert(weddings[0]);
                eventDao.insert(weddings[1]);

                Event[] deathMarriage = EventGenerator.death(mom, dad, deathYear, locationData);
                eventCount += 2;

                eventDao.insert(deathMarriage[0]);
                eventDao.insert(deathMarriage[1]);
            }


            if (dad == null || mom == null) {
                person = PersonGenerator.person(username,gender, surNames, femaleNames, maleNames);
                peopleCount++;
            }
            else {
                if (initialGeneration == generations) {
                    person = new Person(user.getFirstName(), user.getLastName(), gender, user.getPersonID(),
                            dad.getPersonID(), mom.getPersonID(), null, username);
                } else {
                    person = PersonGenerator.person(username, mom, dad, gender, surNames, femaleNames, maleNames);
                }
                peopleCount++;
                birthYear += generateRandom();
            }


            Event birth = EventGenerator.birth(person, birthYear, locationData);
            eventCount++;

            personDao.insert(person);
            eventDao.insert(birth);
            conn.commit();

            return person;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public int getEventCount() {
        return eventCount;
    }

    /**
     * Generates a random number
     * @return
     */
    public int generateRandom() {
        int min = 22;
        int max = 25;
        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }
}
