package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class Vector2DTest {

    private Vector2D vector;
    private static final double DELTA = 1e-3;

    @Before
    public void initialization() {
        vector = new Vector2D(1.0, 1.0);
    }

    @Test
    public void translate() {
        vector.translate(new Vector2D(3, 3));
        Assert.assertEquals(4, vector.getX(), DELTA);
        Assert.assertEquals(4, vector.getY(), DELTA);
        vector.translate(new Vector2D(-1, -4.5));
        Assert.assertEquals(3, vector.getX(), DELTA);
        Assert.assertEquals(-0.5, vector.getY(), DELTA);

    }

    @Test(expected = IllegalArgumentException.class)
    public void translateWithNullOffset() {
        vector.translate(null);
    }

    @Test
    public void translated() {
        Vector2D translated = vector.translated(new Vector2D(3, 3));
        Assert.assertEquals(4, translated.getX(), DELTA);
        Assert.assertEquals(4, translated.getY(), DELTA);

    }

    @Test(expected = IllegalArgumentException.class)
    public void translatedWithNullOffset() {
        Vector2D translated = vector.translated(null);
    }

    @Test
    public void rotate() {
        vector.rotate(45);
        Assert.assertEquals(0, vector.getX(), DELTA);
        Assert.assertEquals(1.414, vector.getY(), DELTA);
        vector.rotate(-180);
        Assert.assertEquals(0, vector.getX(), DELTA);
        Assert.assertEquals(-1.414, vector.getY(), DELTA);
        vector.rotate(0);
        Assert.assertEquals(0, vector.getX(), DELTA);
        Assert.assertEquals(-1.414, vector.getY(), DELTA);

    }

    @Test
    public void rotated() {
        Vector2D rotated = vector.rotated(45);
        Assert.assertEquals(0, rotated.getX(), DELTA);
        Assert.assertEquals(1.414, rotated.getY(), DELTA);
    }

    @Test
    public void scale() {
        vector.scale(2);
        Assert.assertEquals(vector.getX(), 2, DELTA);
        Assert.assertEquals(vector.getY(), 2, DELTA);
        vector.scale(0.5);
        Assert.assertEquals(vector.getX(), 1, DELTA);
        Assert.assertEquals(vector.getY(), 1, DELTA);
        vector.scale(-0.5);
        Assert.assertEquals(vector.getX(), -0.5, DELTA);
        Assert.assertEquals(vector.getY(), -0.5, DELTA);

    }

    @Test
    public void scaled() {
        Vector2D scaled = vector.scaled(2);
        Assert.assertEquals(scaled.getX(), 2, DELTA);
        Assert.assertEquals(scaled.getY(), 2, DELTA);
    }

    @Test
    public void copy() {
        Vector2D copy = vector.copy();
        Assert.assertEquals(vector.getX(), copy.getX(), DELTA);
        Assert.assertEquals(vector.getY(), copy.getY(), DELTA);
    }
}