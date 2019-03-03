package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

/**
 * Simple tests to check the methods for calculation of the area and perimeter of a rectangle.
 * 
 * @author Tomislav Bozuric
 *
 */
public class RectangleTest {
	

	@Test
	public void areaOfARectangle() {
		double width = 8.5;
		double height = 2.3;
		double actualArea = Rectangle.calculateArea(width, height);
		double expectedArea = width * height;
		Assert.assertTrue((expectedArea - actualArea) < 1E-6);

	}

	@Test
	public void perimeterOfARectangle() {
		double width = 8.9;
		double height = 2.8;
		double actualPerimiter = Rectangle.calculatePerimeter(width, height);
		double expectedPerimeter = 2 * (width + height);
		Assert.assertTrue((expectedPerimeter - actualPerimiter) < 1E-6);
	}

}
