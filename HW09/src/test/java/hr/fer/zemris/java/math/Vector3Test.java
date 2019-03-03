package hr.fer.zemris.java.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class Vector3Test {

    private static final double DELTA = 1E-3;
    private static Vector3 test;

    @Before
    public void initialization() {
        test = new Vector3(1, -1, 5);
    }


    @Test
    public void add() {
        Vector3 other = new Vector3(1, 1, 1);
        test = test.add(other);
        Assert.assertEquals(2, test.getX(), DELTA);
        Assert.assertEquals(0, test.getY(), DELTA);
        Assert.assertEquals(6, test.getZ(), DELTA);


    }

    @Test
    public void sub() {
        Vector3 other = new Vector3(1, 1, 1);
        test = test.sub(other);
        Assert.assertEquals(0, test.getX(), DELTA);
        Assert.assertEquals(-2, test.getY(), DELTA);
        Assert.assertEquals(4, test.getZ(), DELTA);

    }

    @Test
    public void dot() {
        Vector3 other = new Vector3(1, 1, 1);
        Assert.assertEquals(5, test.dot(other), DELTA);
        Assert.assertEquals(27, test.dot(test), DELTA);

    }

    @Test
    public void cross() {
        Vector3 other = new Vector3(1, 1, 1);
        Vector3 cross = test.cross(test);
        Assert.assertEquals(0, cross.getX(), DELTA);
        Assert.assertEquals(0, cross.getY(), DELTA);
        Assert.assertEquals(0, cross.getZ(), DELTA);

        test = test.cross(other);
        Assert.assertEquals(-6, test.getX(), DELTA);
        Assert.assertEquals(4, test.getY(), DELTA);
        Assert.assertEquals(2, test.getZ(), DELTA);

    }

    @Test
    public void norm() {
        Assert.assertEquals(5.196, test.norm(), DELTA);
        Assert.assertEquals(1.732, new Vector3(1, 1, 1).norm(), DELTA);

    }

    @Test
    public void normalized() {
        Vector3 normalized = test.normalized();
        Assert.assertEquals(test.getX() / 5.196, normalized.getX(), DELTA);
        Assert.assertEquals(test.getY() / 5.196, normalized.getY(), DELTA);
        Assert.assertEquals(test.getZ() / 5.196, normalized.getZ(), DELTA);
    }

    @Test
    public void cosAngle() {
        Vector3 other = new Vector3(2, 4, 1);
        Assert.assertEquals(0.125988, test.cosAngle(other), DELTA);
        other = new Vector3(1, -1, 5);
        Assert.assertEquals(1, test.cosAngle(other), DELTA);

    }

    @Test
    public void scale() {
        Vector3 scale = test.scale(2);
        Assert.assertEquals(2, scale.getX(), DELTA);
        Assert.assertEquals(-2, scale.getY(), DELTA);
        Assert.assertEquals(10, scale.getZ(), DELTA);
    }

    @Test
    public void toArray() {
        Assert.assertTrue(Arrays.equals(new double[]{1, -1, 5}, test.toArray()));
    }

    @Test(expected = NullPointerException.class)
    public void addNull() {
        test.add(null);
    }

    @Test(expected = NullPointerException.class)
    public void subNull() {
        test.sub(null);
    }

    @Test(expected = NullPointerException.class)
    public void dotNull() {
        test.dot(null);
    }

    @Test(expected = NullPointerException.class)
    public void crossNull() {
        test.cross(null);
    }

    @Test(expected = NullPointerException.class)
    public void cosAngleNull() {
        test.cosAngle(null);
    }


}
