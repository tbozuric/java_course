package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

/**
 * Simple tests to check the method for factorial calculation.
 *
 * @author Tomislav Bozuric
 */
public class FactorialTest {

    @Test
    public void calculateFactorialOfSmallPositiveNumber() {
        Assert.assertEquals(120L, Factorial.calculateFactorial(5));
    }

    @Test
    public void calculateFactorialOfBiggerPositiveNumber() {
        Assert.assertEquals(1307674368000L, Factorial.calculateFactorial(15));
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateFactorialOfNegativeNumber() {
        Factorial.calculateFactorial(-5);
    }

    @Test
    public void calculateFactorialOfZero() {
        Assert.assertEquals(1, Factorial.calculateFactorial(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateFactorialOfBigNumber() {
        Factorial.calculateFactorial(40);
    }

}
