package hr.fer.zemris.java.hw02;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static java.lang.Math.sqrt;
import static java.lang.Math.PI;

public class ComplexNumberTest {

	private static ComplexNumber number1;
	private static ComplexNumber number2;
	private static ComplexNumber number3;
	private static ComplexNumber number4;
	private static final double DELTA = 1E-2;

	@BeforeClass
	public static void initialization() {
		number1 = new ComplexNumber(2, -1);
		number2 = new ComplexNumber(-5.7, -1.2);
		number3 = ComplexNumber.fromReal(4.5);
		number4 = ComplexNumber.fromImaginary(-1.7);
	}

	@Test
	public void getReal() {
		Assert.assertEquals(2, number1.getReal(), DELTA);
		Assert.assertEquals(-5.7, number2.getReal(), DELTA);
	}

	@Test
	public void getImaginary() {
		Assert.assertEquals(-1, number1.getImaginary(), DELTA);
		Assert.assertEquals(-1.7, number4.getImaginary(), DELTA);
	}

	@Test
	public void getMagnitude() {
		Assert.assertEquals(4.5, number3.getMagnitude(), DELTA);
		Assert.assertEquals(1.7, number4.getMagnitude(), DELTA);
		Assert.assertEquals(sqrt(5), number1.getMagnitude(), DELTA);
	}

	@Test
	public void getAngle() {
		Assert.assertEquals(3 * PI / 2, number4.getAngle(), DELTA);
		Assert.assertEquals(0, number3.getAngle(), DELTA);
		Assert.assertEquals(5.8194513249247, number1.getAngle(), DELTA);
	}

	@Test
	public void add() {
		ComplexNumber sum = number1.add(number2);
		Assert.assertEquals(-3.7, sum.getReal(), DELTA);
		Assert.assertEquals(-2.2, sum.getImaginary(), DELTA);
	}

	@Test
	public void sub() {
		ComplexNumber difference = number4.sub(number3);
		Assert.assertEquals(-4.5, difference.getReal(), DELTA);
		Assert.assertEquals(-1.7, difference.getImaginary(), DELTA);

	}

	@Test
	public void mul() {
		ComplexNumber product = number1.mul(number2);
		Assert.assertEquals(-12.6, product.getReal(), DELTA);
		Assert.assertEquals(3.3, product.getImaginary(), DELTA);
	}

	@Test
	public void div() {
		ComplexNumber result = number1.div(number2);
		Assert.assertEquals(-0.3006, result.getReal(), DELTA);
		Assert.assertEquals(0.2387, result.getImaginary(), DELTA);

	}

	@Test(expected = IllegalArgumentException.class)
	public void divWithZero() {
		ComplexNumber other = new ComplexNumber(0, 0);
		number1.div(other);
	}

	@Test
	public void power() {
		ComplexNumber result = number1.power(5);
		Assert.assertEquals(-38, result.getReal(), DELTA);
		Assert.assertEquals(-41, result.getImaginary(), DELTA);
		Assert.assertEquals(1, number1.power(0).getReal(), DELTA);

	}

	@Test
	public void root() {
		ComplexNumber[] roots = number1.root(5);

		Assert.assertEquals(0.46486, roots[0].getReal(), DELTA);
		Assert.assertEquals(1.07872, roots[0].getImaginary(), DELTA);

		Assert.assertEquals(-1.01013, roots[2].getReal(), DELTA);
		Assert.assertEquals(-0.59946, roots[2].getImaginary(), DELTA);

		Assert.assertEquals(1.16957, roots[4].getReal(), DELTA);
		Assert.assertEquals(-0.10877, roots[4].getImaginary(), DELTA);

	}

	@Test
	public void fromMagnitudeAndAngle() {
		ComplexNumber number = ComplexNumber.fromMagnitudeAndAngle(2.24, 1.57);
		Assert.assertEquals(0, number.getReal(), DELTA);
		Assert.assertEquals(2.24, number.getImaginary(), DELTA);
	}
	
	@Test
	public void parse() {
		Assert.assertEquals(3.5, ComplexNumber.parse("3.5").getReal() , DELTA);
		Assert.assertEquals(-3432.5, ComplexNumber.parse("-3432.5").getReal() , DELTA);
		Assert.assertEquals(0, ComplexNumber.parse("3.5").getImaginary(), DELTA);
		Assert.assertEquals(8, ComplexNumber.parse("3.52+8i").getImaginary() , DELTA);
		Assert.assertEquals(-8, ComplexNumber.parse("-8i").getImaginary() , DELTA);
		Assert.assertEquals(1, ComplexNumber.parse("i").getImaginary() , DELTA);
		Assert.assertEquals(-1, ComplexNumber.parse("-i").getImaginary() , DELTA);
		Assert.assertEquals(-53.23, ComplexNumber.parse("3.5-53.23i").getImaginary() , DELTA);
		Assert.assertEquals(3.5, ComplexNumber.parse("3.5-53.23i").getReal() , DELTA);
		Assert.assertEquals(-3, ComplexNumber.parse("-3").getReal() , DELTA);
		
	}

}
