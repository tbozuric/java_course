package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.QueryParserException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class QueryParserTest {

    private static QueryParser qp1;
    private static QueryParser qp2;
    private static QueryParser qp3;

    @BeforeClass
    public static void initialization(){
        qp1 = new QueryParser("   jmbag        =\"0123456789\"    ");
        qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
        qp3 = new QueryParser("\t\t\t\r\n         lastName=\"Boris\"");


    }

    @Test
    public void testIsDirectQuery(){
        Assert.assertTrue(qp1.isDirectQuery());
        Assert.assertFalse(qp2.isDirectQuery());
        Assert.assertFalse(qp3.isDirectQuery());
    }

    @Test
    public void testQueriedJMBAG(){
        Assert.assertEquals(qp1.getQueriedJMBAG(), "0123456789");

    }

    @Test(expected = IllegalStateException.class)
    public void testQueridJMBAGForUndirectQuery(){
        qp2.getQueriedJMBAG();
    }

    @Test
    public void testQuerySize(){
        Assert.assertEquals(1 , qp1.getQuery().size());
        Assert.assertEquals(2 , qp2.getQuery().size());
        Assert.assertEquals(1, qp3.getQuery().size());
    }
    @Test(expected = QueryParserException.class)
    public void testInvalidFormatOfQuery(){
        new QueryParser("\t jmbag=>\"0123456789\"");
    }

    @Test(expected = QueryParserException.class)
    public void testInvalidSequenceInQuery(){
        new QueryParser("\t jmbag=\"0123456789\"  and and and lastName=\"marko\"  ");

    }

    @Test(expected = QueryParserException.class)
    public void testInvalidAttributeName(){
        new QueryParser("\t jmbag=\"0123456789\"  and lastNaMe=\"marko\"  ");
    }


    @Test
    public void testLIKEInQuery(){
       QueryParser queryParser = new QueryParser("\t jmbag=\"0123456789\"  and lastName=\"marko\" and firstName LIKE \"Bos*\"");
       Assert.assertEquals(3 , queryParser.getQuery().size());
       Assert.assertTrue(queryParser.getQuery().get(1).getComparisonOperator() == ComparisonOperators.EQUALS);
       Assert.assertEquals("Bos*" , queryParser.getQuery().get(2).getStringLiteral());
       Assert.assertTrue(queryParser.getQuery().get(2).getComparisonOperator()== ComparisonOperators.LIKE);
       Assert.assertTrue(queryParser.getQuery().get(2).getFieldValueGetter()== FieldValueGetters.FIRST_NAME);
       Assert.assertTrue(queryParser.getQuery().get(1).getFieldValueGetter()== FieldValueGetters.LAST_NAME);
    }

}
