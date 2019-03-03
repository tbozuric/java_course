package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StudentDatabaseTest {

    private static StudentDatabase studentDatabase;
    private static List<String> lines = new ArrayList<>();

    @BeforeClass
     public static void initialization(){
         try {
             lines = Files.readAllLines(Paths.get("src/main/resources/database.txt"), StandardCharsets.UTF_8);
             studentDatabase = new StudentDatabase(lines);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }


     @Test
    public void testForJMBAG(){
        //0000000026	Katunarić	Zoran	3
        StudentRecord record = studentDatabase.forJMBAG("0000000026");
        Assert.assertEquals(record.getFirstName(), "Zoran");
        Assert.assertEquals(record.getLastName(), "Katunarić");
        Assert.assertEquals(record.getFinalGrade(), 3);

        //0000000031	Krušelj Posavec	Bojan	4
        record = studentDatabase.forJMBAG("0000000031");
        Assert.assertEquals(record.getFirstName(), "Bojan");
        Assert.assertEquals(record.getLastName(), "Krušelj Posavec");
        Assert.assertEquals(record.getFinalGrade(), 4);


     }

     @Test
     public void testForNonExistsJmbag(){
        StudentRecord record = studentDatabase.forJMBAG("0020000026");
        Assert.assertTrue(record == null);
     }

     @Test
     public void testFilterAlwaysTrue(){
        Assert.assertEquals(lines.size() , studentDatabase.filter(record->true).size());
     }

     @Test
    public void testFilterAlwaysFalse(){
        Assert.assertEquals( 0 , studentDatabase.filter(record -> false).size());
     }

}
