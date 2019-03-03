package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentRecord;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConditionalExpressionTest {


    private static StudentRecord student1;
    private static StudentRecord student2;
    private ConditionalExpression conditionalExpression;


    @BeforeClass
    public static void initialization(){
        student1 = new StudentRecord("0000000040" , "Zrinka" , "Bosčić" , 5);
        student2 = new StudentRecord("0000000031" ,"Bošan" , "Kušelj Posavec", 	4);
    }

    @Test
    public void testStudentSatisfiesLikeComparision(){
        conditionalExpression = new ConditionalExpression(FieldValueGetters.LAST_NAME , "Bos*" , ComparisonOperators.LIKE);
        Assert.assertTrue(conditionalExpression.getComparisonOperator().satisfied(conditionalExpression.getFieldValueGetter().get(student1),conditionalExpression.getStringLiteral()));

        conditionalExpression.setStringLiteral("Kušelj*");
        Assert.assertTrue(conditionalExpression.getComparisonOperator().satisfied(conditionalExpression.getFieldValueGetter().get(student2),conditionalExpression.getStringLiteral()));

        conditionalExpression.setStringLiteral("*");
        Assert.assertTrue(conditionalExpression.getComparisonOperator().satisfied(conditionalExpression.getFieldValueGetter().get(student1),conditionalExpression.getStringLiteral()));

    }


    @Test
    public void testStudentNotSatisfiesLikeComparasion(){
        conditionalExpression = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Bošk*" , ComparisonOperators.LIKE);
        Assert.assertFalse(conditionalExpression.getComparisonOperator().satisfied(conditionalExpression.getFieldValueGetter().get(student2),conditionalExpression.getStringLiteral()));

    }

    @Test
    public void testStudentSatisfiesEqualsComparision(){
        conditionalExpression = new ConditionalExpression(FieldValueGetters.LAST_NAME , "Bosčić" , ComparisonOperators.EQUALS);
        Assert.assertTrue(conditionalExpression.getComparisonOperator().satisfied(conditionalExpression.getFieldValueGetter().get(student1),conditionalExpression.getStringLiteral()));

        conditionalExpression.setStringLiteral("Kušelj Posavec");
        Assert.assertTrue(conditionalExpression.getComparisonOperator().satisfied(conditionalExpression.getFieldValueGetter().get(student2),conditionalExpression.getStringLiteral()));

        conditionalExpression.setStringLiteral("0000000031");
        conditionalExpression.setFieldValueGetter(FieldValueGetters.JMBAG);

        Assert.assertTrue(conditionalExpression.getComparisonOperator().satisfied(conditionalExpression.getFieldValueGetter().get(student2),conditionalExpression.getStringLiteral()));


    }


}
