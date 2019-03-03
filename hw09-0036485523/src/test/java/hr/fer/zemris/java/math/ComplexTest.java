package hr.fer.zemris.java.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ComplexTest {

    private static final double DELTA = 1e-3;
    private static Complex number1;
    private static Complex number2;

    @Before
    public void initialization() {
        number1 = new Complex(2, -1);
        number2 = new Complex(-5.7, -1.2);
    }

    @Test
    public void module() {
        Assert.assertEquals(2.236, number1.module(), DELTA);
        Assert.assertEquals(5.82494, number2.module(), DELTA);
    }


    @Test
    public void multiply() {
        Complex result = number1.multiply(number2);
        Assert.assertEquals(-12.6, result.getReal(), DELTA);
        Assert.assertEquals(3.3, result.getImaginary(), DELTA);
    }

    @Test
    public void divide() {
        Complex result = number1.divide(number2);
        Assert.assertEquals(-0.3, result.getReal(), DELTA);
        Assert.assertEquals(0.2387, result.getImaginary(), DELTA);
    }

    @Test
    public void add() {
        Complex result = number1.add(number2);
        Assert.assertEquals(-3.7, result.getReal(), DELTA);
        Assert.assertEquals(-2.2, result.getImaginary(), DELTA);
    }

    @Test
    public void sub() {
        Complex result = number1.sub(number2);
        Assert.assertEquals(7.7, result.getReal(), DELTA);
        Assert.assertEquals(0.2, result.getImaginary(), DELTA);

    }

    @Test
    public void negate() {
        Complex negate1 = number1.negate();
        Complex negate2 = number2.negate();
        Assert.assertEquals(-2, negate1.getReal(), DELTA);
        Assert.assertEquals(1, negate1.getImaginary(), DELTA);
        Assert.assertEquals(5.7, negate2.getReal(), DELTA);
        Assert.assertEquals(1.2, negate2.getImaginary(), DELTA);
    }

    @Test
    public void power() {
        Complex c1 = number1.power(0);
        Complex c2 = number1.power(1);
        Complex c6 = number1.power(6);
        Complex c15 = number1.power(15);

        Assert.assertEquals(1, c1.getReal(), DELTA);
        Assert.assertEquals(0, c1.getImaginary(), DELTA);

        Assert.assertEquals(2, c2.getReal(), DELTA);
        Assert.assertEquals(-1, c2.getImaginary(), DELTA);

        Assert.assertEquals(-117, c6.getReal(), DELTA);
        Assert.assertEquals(-44, c6.getImaginary(), DELTA);

        Assert.assertEquals(136762, c15.getReal(), DELTA);
        Assert.assertEquals(-108691, c15.getImaginary(), DELTA);
    }

    @Test
    public void root() {
        List<Complex> roots = number1.root(5);

        Assert.assertEquals(1.16957, roots.get(0).getReal(), DELTA);
        Assert.assertEquals(-0.10877, roots.get(0).getImaginary(), DELTA);

        Assert.assertEquals(0.46486, roots.get(1).getReal(), DELTA);
        Assert.assertEquals(1.07872, roots.get(1).getImaginary(), DELTA);

        Assert.assertEquals(-0.88227, roots.get(2).getReal(), DELTA);
        Assert.assertEquals(0.77545, roots.get(2).getImaginary(), DELTA);

        Assert.assertEquals(-1.01013, roots.get(3).getReal(), DELTA);
        Assert.assertEquals(-0.59946, roots.get(3).getImaginary(), DELTA);

        Assert.assertEquals(0.25798, roots.get(4).getReal(), DELTA);
        Assert.assertEquals(-1.14594, roots.get(4).getImaginary(), DELTA);

    }

    @Test
    public void distance() {
        Assert.assertEquals(7.7025, number1.distance(number2), DELTA);
        Assert.assertEquals(0, number1.distance(number1), DELTA);
        Assert.assertEquals(0, number2.distance(number2), DELTA);
    }


    @Test(expected = NullPointerException.class)
    public void multiplyNull() {
        number1.multiply(null);
    }

    @Test(expected = NullPointerException.class)
    public void divideNull() {
        number1.divide(null);
    }


    @Test(expected = NullPointerException.class)
    public void addNull() {
        number1.add(null);
    }


    @Test(expected = NullPointerException.class)
    public void subNull() {
        number1.sub(null);
    }

    @Test(expected = NullPointerException.class)
    public void distanceNull() {
        number1.distance(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rootZero() {
        number1.root(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void powerLessThanZero() {
        number1.power(-5);
    }
}
