package Service;

import Model.Person;
import TreeObjects.fnames;
import TreeObjects.mnames;
import TreeObjects.snames;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.*;

class PersonGeneratorTest {
    private Gson gson = new Gson();
    private PersonGenerator personGenerator;
    private Person dad;
    private Person mom;
    private Person person;
    private snames surnames;
    private fnames femaleNames;
    private mnames maleNames;
    private String gender;
    private String username;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        Reader readerSurnames = new FileReader("json/snames.json");
        surnames = (snames) gson.fromJson(readerSurnames, snames.class);

        Reader readerFemaleNames = new FileReader("json/fnames.json");
        femaleNames = (fnames) gson.fromJson(readerFemaleNames, fnames.class);

        Reader readerMaleNames = new FileReader("json/mnames.json");
        maleNames = (mnames) gson.fromJson(readerMaleNames, mnames.class);

        personGenerator = new PersonGenerator();

        dad = new Person("firstName", "lastName", "m", "personID_fatherID", "fatherID", "motherID", "spouseID", "associatedUsername");
        mom = new Person("firstName", "lastName", "m", "personID_motherID", "fatherID", "motherID", "spouseID", "associatedUsername");
        gender = "m";
        username = "username";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void personWithoutParentsPass() {
        person = personGenerator.person(username, gender, surnames, femaleNames, maleNames);
        assertNotNull(person);
        assertEquals("username", person.getAssociatedUsername());
        assertEquals("m", person.getGender());
    }

    @Test
    void personWithoutParentsFail() {
        person = personGenerator.person(username, "4", surnames, femaleNames, maleNames);
//        assertNull(person);
        assertNotEquals("m", person.getGender());
        assertNotEquals("f", person.getGender());
    }

    @Test
    void testPersonWithParentsPass() {
        person = personGenerator.person(username, mom, dad, gender, surnames, femaleNames, maleNames);
        assertNotNull(person);
        assertEquals("username", person.getAssociatedUsername());
        assertEquals("m", person.getGender());
        assertEquals("personID_fatherID", person.getFatherID());
        assertEquals("personID_motherID", person.getMotherID());
    }

    @Test
    void testPersonWithParentsFail() {
        person = personGenerator.person(username, mom, dad, "4", surnames, femaleNames, maleNames);
        assertNull(person);
    }
}