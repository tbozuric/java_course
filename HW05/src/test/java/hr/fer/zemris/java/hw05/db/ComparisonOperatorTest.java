package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.IComparisonOperator;
import org.junit.Assert;
import org.junit.Test;

public class ComparisonOperatorTest {

    private IComparisonOperator operator;

    @Test
    public void testLessOperator(){
        operator = ComparisonOperators.LESS;
        Assert.assertTrue(operator.satisfied("Ana", "Jasna"));
        Assert.assertTrue(operator.satisfied("Ciro", "Petar"));
        Assert.assertTrue(operator.satisfied("Zeba", "Žarko"));
        Assert.assertTrue(operator.satisfied("AAAAb", "AAAAc"));
    }

    @Test
    public void testLessOrEqualsOperator(){
        operator = ComparisonOperators.LESS_OR_EQUALS;
        Assert.assertTrue(operator.satisfied("Ana", "Jasna"));
        Assert.assertTrue(operator.satisfied("Ciro", "Ciro"));
        Assert.assertTrue(operator.satisfied("Zeba", "Žarko"));
    }

    @Test
    public void testGreaterOperator(){
        operator = ComparisonOperators.GREATER;
        Assert.assertTrue(operator.satisfied( "Jasna", "Ana"));
        Assert.assertTrue(operator.satisfied( "Petar" , "Ciro"));
        Assert.assertTrue(operator.satisfied( "Žarko", "Zeba"));
        Assert.assertTrue(operator.satisfied( "AAAAc" ,"AAAAb"));
    }

    @Test
    public void testGreaterOrEqualsOperator(){
        operator = ComparisonOperators.GREATER_OR_EQUALS;
        Assert.assertTrue(operator.satisfied( "Jasna", "Ana"));
        Assert.assertTrue(operator.satisfied( "Petar" , "Ciro"));
        Assert.assertTrue(operator.satisfied( "Žarko", "Žarko"));
        Assert.assertTrue(operator.satisfied( "AAAAc" ,"AAAAb"));
    }

    @Test
    public void testEqualsOperator(){
        operator = ComparisonOperators.EQUALS;
        Assert.assertTrue(operator.satisfied( "Žarko", "Žarko"));
        Assert.assertTrue(operator.satisfied( "ZZaaac", "ZZaaac"));
        Assert.assertTrue(operator.satisfied( "Čarko", "Čarko"));
    }

    @Test
    public void testNotEqualsOperator(){
        operator = ComparisonOperators.NOT_EQUALS;
        Assert.assertTrue(operator.satisfied( "Jasna", "Ana"));
        Assert.assertTrue(operator.satisfied( "Petar" , "Ciro"));
        Assert.assertTrue(operator.satisfied( "Žarko", "Žarkon"));
        Assert.assertTrue(operator.satisfied( "AAAAc" ,"AAAAb"));
    }

    @Test
    public void testLikeOperator(){
        operator = ComparisonOperators.LIKE;
        Assert.assertTrue(operator.satisfied("AAAA", "AA*AA"));
        Assert.assertTrue(operator.satisfied("bbvvbaAAA","*AA"));
        Assert.assertTrue(operator.satisfied("bbAAvvbaAA","*AA"));
        Assert.assertTrue(operator.satisfied("AAAAAmarkoIMarAAijaAAAAA","AAAAA*AAAAA"));
        Assert.assertTrue(operator.satisfied("Bosčić","Bos*"));

    }
}
