package hr.fer.zemris.custom.scripting.exec;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ValueWrapperTest {

    private ValueWrapper v1;
    private ValueWrapper v2;

    @Before
    public void initialization(){
         v1 = new ValueWrapper(null);
         v2 = new ValueWrapper(null);
    }

    @Test
    public void testGetValue(){
        Assert.assertTrue(v1.getValue() == null);
        Assert.assertTrue(v2.getValue() == null);
        v1.add(v2.getValue());
        Assert.assertTrue(v1.getValue() instanceof Integer);
        Assert.assertEquals( 0, v1.getValue());
        v1.add(new ValueWrapper(2.52).getValue());
        Assert.assertTrue(v1.getValue() instanceof Double);
        Assert.assertEquals(  2.52 , v1.getValue());
    }

    @Test
    public void testAdd(){
       v1.add(v2.getValue());
       Assert.assertEquals( 0, v1.getValue());
       Assert.assertTrue(v1.getValue() instanceof  Integer);
       v1.setValue(5.63);
       v1.add(v2.getValue());
       Assert.assertEquals( 5.63, v1.getValue());
       Assert.assertTrue(v1.getValue() instanceof  Double);
       v1.setValue(1);
       v2.setValue(2);
       v1.add(v2.getValue());
       Assert.assertEquals( 3, v1.getValue() );
       Assert.assertTrue(v1.getValue() instanceof Integer);
       v2.setValue("1.52e2");
       v1.add(v2.getValue());
       Assert.assertTrue(v1.getValue() instanceof Double);
       Assert.assertEquals(155.0 , v1.getValue() );
       v2.setValue("5");
       v1.setValue("66");
       v1.add(v2.getValue());
       Assert.assertTrue(v1.getValue() instanceof  Integer);
       Assert.assertEquals(71 , v1.getValue());

    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddWithIllegalArgument(){
        v1.add(new ValueWrapper("stefica").getValue());
    }

    @Test
    public void testMultiply(){
        v1.multiply(v2.getValue());
        Assert.assertEquals( 0, v1.getValue());
        Assert.assertTrue(v1.getValue() instanceof  Integer);
        v1.setValue(5.0);
        v1.multiply(v2.getValue());
        Assert.assertEquals( 0.0, v1.getValue());
        Assert.assertTrue(v1.getValue() instanceof  Double);
        v1.setValue(1);
        v2.setValue(2);
        v1.multiply(v2.getValue());
        Assert.assertEquals( 2, v1.getValue() );
        Assert.assertTrue(v1.getValue() instanceof Integer);
        v2.setValue("1.0e2");
        v1.multiply(v2.getValue());
        Assert.assertTrue(v1.getValue() instanceof Double);
        Assert.assertEquals(200.0 , v1.getValue() );
        v2.setValue("5");
        v1.setValue("5");
        v1.multiply(v2.getValue());
        Assert.assertTrue(v1.getValue() instanceof  Integer);
        Assert.assertEquals(25 , v1.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiplyWithIllegalArgument(){
        v1.multiply(new ValueWrapper("štefanija").getValue());
    }

    @Test
    public void testDivide(){
        v2.setValue(1);
        v1.divide(v2.getValue());
        Assert.assertEquals( 0, v1.getValue());
        Assert.assertTrue(v1.getValue() instanceof  Integer);
        v1.setValue(5.0);
        v1.divide(v2.getValue());
        Assert.assertEquals( 5.0, v1.getValue());
        Assert.assertTrue(v1.getValue() instanceof  Double);
        v1.setValue(1);
        v2.setValue(2);
        v1.divide(v2.getValue());
        Assert.assertEquals( 0, v1.getValue() );
        Assert.assertTrue(v1.getValue() instanceof Integer);
        v1.setValue(1000);
        v2.setValue("1.0e2");
        v1.divide(v2.getValue());
        Assert.assertTrue(v1.getValue() instanceof Double);
        Assert.assertEquals(10.0 , v1.getValue() );
        v2.setValue("5");
        v1.setValue("5");
        v1.divide(v2.getValue());
        Assert.assertTrue(v1.getValue() instanceof  Integer);
        Assert.assertEquals(1 , v1.getValue());
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideWithZero(){
        v2.setValue(0.000);
        v1.divide(v2.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivideWithIllegalArgument(){
        v2.setValue("petraE6.3");
        v1.divide(v2.getValue());
    }

    @Test
    public void testSubtract(){
        v2.setValue(1);
        v1.subtract(v2.getValue());
        Assert.assertEquals( -1, v1.getValue());
        Assert.assertTrue(v1.getValue() instanceof  Integer);
        v1.setValue(5.0);
        v1.subtract(v2.getValue());
        Assert.assertEquals( 4.0, v1.getValue());
        Assert.assertTrue(v1.getValue() instanceof  Double);
        v1.setValue(1);
        v2.setValue(2);
        v1.subtract(v2.getValue());
        Assert.assertEquals( -1, v1.getValue() );
        Assert.assertTrue(v1.getValue() instanceof Integer);
        v1.setValue(1000);
        v2.setValue("1.0e2");
        v1.subtract(v2.getValue());
        Assert.assertTrue(v1.getValue() instanceof Double);
        Assert.assertEquals(900.0 , v1.getValue() );
        v2.setValue("5");
        v1.setValue("5");
        v1.subtract(v2.getValue());
        Assert.assertTrue(v1.getValue() instanceof  Integer);
        Assert.assertEquals(0 , v1.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubtractWithIllegalArgument(){
        v2.setValue(new ArrayList<>(5));
        v1.subtract(v2.getValue());
    }


    @Test
    public void numericalCompare(){
        Assert.assertEquals(v1.getValue(), v2.getValue());
        v1.setValue(1);
        Assert.assertEquals(1, v1.numCompare(v2.getValue()));
        Assert.assertTrue(v2.getValue() == null);
        v2.setValue(1.0);
        Assert.assertEquals( 0, v1.numCompare(v2.getValue()));
        v1.setValue(-2.2);
        Assert.assertEquals( -1, v1.numCompare(v2.getValue()));

    }
}
