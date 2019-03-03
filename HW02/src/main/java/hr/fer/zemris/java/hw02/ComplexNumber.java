package hr.fer.zemris.java.hw02;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.atan2;
import static java.lang.Math.pow;
import static java.lang.Math.PI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents complex number( a + bi )
 * For more info  : @see <a href="https://en.wikipedia.org/wiki/Complex_number">Complex numbers</a>
 * @author Tomislav Bozuric
 *
 */

public class ComplexNumber {
	
	/**
	 * Real part of a complex number.
	 */
	private double real;
	
	/**
	 * Imaginary part of a complex number.
	 */
	private double imaginary;
	
	/**
	 * Creates new instance of a complex number
	 * @param real part of a complex number
	 * @param imaginary part of a complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Creates new instance of a complex number from real part.
	 * @param real part of a complex number
	 * @return new complex number with real part
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Creates new instance of a complex number from imaginary part.
	 * @param imaginary part of a complex number
	 * @return new complex number with imaginary part
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Creates new instance of a complex number from magnitude and angle
	 * @param magnitude of a complex number
	 * @param angle of a complex number in radians
	 * @return complex number in format (a + bi)
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}
	
	/**
	 * Creates new instance of a complex number if given string is valid.
	 * Acceptable formats : "a" , "bi" , "i" ,"-i",  "a+bi", "a-bi"
 	 * @param s complex number in string format
	 * @throws IllegalArgumentException if the given string is in invalid format
	 * @return new complex number 
	 */
	public static ComplexNumber parse(String s) {
		Pattern realPattern = Pattern.compile("^[-]?([0-9]+(\\.[0-9][0-9]*)?){1}$");
		Pattern imaginaryPattern = Pattern.compile("^[-]?[0-9]+(\\.[0-9][0-9]*)*i$|^[-]?i$");
		Pattern realAndImaginaryPattern = Pattern.compile("^([-]?[0-9]+(\\.[0-9][0-9]*)?)([\\+\\-]((([0-9]+(\\.[0-9][0-9]*)?)i)|i)){1}$");
		
		Matcher realAndImaginaryMatcher = realAndImaginaryPattern.matcher(s);
		if (realAndImaginaryMatcher.find()) {
			double real = Double.parseDouble(realAndImaginaryMatcher.group(1));
			String imaginary = realAndImaginaryMatcher.group(3);
			return new ComplexNumber(real, parseImaginary(imaginary));
		}
	
		else if (existsPatternInExpression(imaginaryPattern, s)) {
			return new ComplexNumber(0, parseImaginary(s));
		}
		else if (existsPatternInExpression(realPattern, s)) {
			return new ComplexNumber(Double.parseDouble(s), 0);
		} else {
			throw new IllegalArgumentException("Expression is invalid!");
		}
	}
	
	/**
	 * Parses imaginary number in a string format.
	 * @param s imaginary number in a string format etc.(5i, 2i ,i ,...)
	 * @return imaginary part of a complex number etc. (5, 2, 1, ...)
 	 */
	private static double parseImaginary(String s) {
		//removes "i" from imaginary number 
		s = s.substring(0, s.length() - 1);
		
		if (s.length() == 0) {
			return 1;
		}
		else if(s.equals("-")) {
			return -1;
		}else if(s.equals("+")){
			return 1;
		}
		return  Double.parseDouble(s);
	}
	
	/**
	 * Checks if the string matches the given pattern.
	 * @param pattern for comparison
	 * @param s input string
	 * @return whether the string matches given pattern
	 */
	private static boolean existsPatternInExpression(Pattern pattern, String s) {
		Matcher matcher = pattern.matcher(s);
		return matcher.find();
	}
	
	/**
	 * Returns real part of a complex number
	 * @return real part of a complex number
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Returns imaginary part of a complex number
	 * @return imaginary part of a  complex number
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Calculates magnitude of a complex number
	 * @return magnitude of a complex number
	 */
	public double getMagnitude() {
		return sqrt(real*real + imaginary*imaginary);
	}
	
	/**
	 * Calculates the angle of a complex number in range [0 , 2 * PI]
	 * @return angle of a complex number in radians
	 */
	public double getAngle() {
		double radians = atan2(imaginary, real);
		return (radians + 2 * PI) % (2 * PI);
	}
	
	/**
	 * Adds two complex numbers
	 * @param other  complex number
	 * @return  a new complex number that represents sum of complex numbers
	 */
	public ComplexNumber add(ComplexNumber other) {
		return new ComplexNumber(real + other.real, imaginary + other.imaginary);
	}
	
	/**
	 * Subtracts two complex numbers
	 * @param other complex number
	 * @return a new complex number that represents difference of two complex numbers
	 */
	public ComplexNumber sub(ComplexNumber other) {
		return new ComplexNumber(real - other.real, imaginary - other.imaginary);
	}
	
	/**
	 * Multiplies two complex numbers.
	 * @param other complex number
	 * @return a new complex number that represents product of two complex numbers.
	 */
	public ComplexNumber mul(ComplexNumber other) {
		return new ComplexNumber(real*other.real - imaginary*other.imaginary,
				real*other.imaginary + imaginary*other.real);
	}
	
	/**
	 * Divides two complex numbers.
	 * @param other complex number
	 * @throws IllegalArgumentException if second complex  number has a real and imaginary part equal to zero
	 * @return a new complex number that represents quotient of two complex numbers.
	 */
	public ComplexNumber div(ComplexNumber other) {
		double numeratorReal = real*other.real + imaginary*other.imaginary;
		double numeratorImaginary = -real*other.imaginary + imaginary*other.real;
		double denominator = other.real*other.real + other.imaginary*other.imaginary;
		if (isZero(denominator)) {
			throw new IllegalArgumentException("Division by zero is not allowed!");
		}
		return new ComplexNumber(numeratorReal / denominator, numeratorImaginary / denominator);
	}
	
	/**
	 * Calculates  n-th power of a complex number.
	 * @see <a href="https://brilliant.org/wiki/de-moivres-theorem/">De Moivres Theorem</a>
	 * @param n nth power 
	 * @throws IllegalArgumentException if given power is less than 0.
	 * @return a new complex number that is raised to the n-th power
	 */
	public ComplexNumber power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("The exponent must be grather or equal to zero." + " Was " + n);
		double magnitude = getMagnitude();
		double angle =  getAngle();
		double factor = pow(magnitude , n);
		// De Moivre's Theorem
		return new ComplexNumber(factor * cos(n * angle), factor * sin(n * angle));
	}


	/**
	 * Calculates n roots of a complex number. 
	 * @param n number of roots
	 * @throws IllegalArgumentException if the given number is less or equal to zero
	 * @return array of roots of a complex number
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException(
					"The roots of the complex number can only be calculated for positive number of roots!");
		ComplexNumber[] roots = new ComplexNumber[n];
		double magnitude = getMagnitude();
		double angle = getAngle();
		double factor = pow(magnitude, 1.0 / n);
		for (int k = 0; k < n; k++) {
			double real = cos((2 * k * PI + angle) / n);
			double imaginary = sin((2 * k * PI + angle) / n);
			roots[k] = new ComplexNumber(factor * real, factor * imaginary);
		}
		return roots;

	}
	
	/**
	 * Returns a string representation of a complex number in a format (a + bi)
	 */
	public String toString() {

		if (imaginary < 0) {
			return real + "" + imaginary + "i";
		}
		return real + "+" + imaginary + "i";
	}

	/**
	 * Checks if the given value is equal to zero
	 * @param value number that we want to check
	 * @return whether the given value is equal to zero
	 */
	private boolean isZero(double value) {
		double threshold = 1E-6;
		return value >= -threshold && value <= threshold;
	}
}
