package hr.fer.zemris.java.hw02.demo;

import java.util.Arrays;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * A demonstration program for complex number computation.
 * @author Tomislav Bozuric
 *
 */

public class ComplexDemo {
	
	/**
	 * Method invoked when running the program.
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = new ComplexNumber(3,3);
		
		ComplexNumber c = new ComplexNumber(2, -1);
		System.out.println(c);

		System.out.println(ComplexNumber.parse("-2.71-3.15i"));

		
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		System.out.println(c3);
	
	}
}
