package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.IFieldValueGetter;
import hr.fer.zemris.java.hw05.db.StudentRecord;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FieldValueGettersTest {

    private IFieldValueGetter fieldValueGetter;
    private static StudentRecord student1;
    private static StudentRecord student2;


    @BeforeClass
    public static void initialization(){
        student1 = new StudentRecord("0000000040" , "Zrinka" , "Mišura" , 5);
        student2 = new StudentRecord("0000000031" ,"Bojan" , "Kušelj Posavec", 	4);

    }

    @Test
    public void testFirstNameValueGetter(){
        fieldValueGetter = FieldValueGetters.FIRST_NAME;
        Assert.assertTrue(fieldValueGetter.get(student1).equals("Zrinka"));
        Assert.assertTrue(fieldValueGetter.get(student2).equals("Bojan"));
    }

    @Test
    public void testLastNameValueGetter(){
        fieldValueGetter = FieldValueGetters.LAST_NAME;
        Assert.assertTrue(fieldValueGetter.get(student1).equals("Mišura"));
        Assert.assertTrue(fieldValueGetter.get(student2).equals("Kušelj Posavec"));
    }


    @Test
    public void testJMBAGValueGetter(){
        fieldValueGetter = FieldValueGetters.JMBAG;
        Assert.assertTrue(fieldValueGetter.get(student1).equals("0000000040"));
        Assert.assertTrue(fieldValueGetter.get(student2).equals("0000000031"));
    }
}
