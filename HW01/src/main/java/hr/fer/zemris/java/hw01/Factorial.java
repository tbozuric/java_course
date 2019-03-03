package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * A program that reads the numbers from the standard input and for each entered
 * number calculates n! if the number satisfies the limitations.
 * Entered number must be an integer in a range of 1 to 20.
 * 
 * @author Tomislav Bozuric
 *
 */
public class Factorial {
	
	private static final int LOWER_LIMIT = 1;
	private static final int UPPER_LIMIT = 20;
	private static final int FACTORIAL_UPPER_LIMIT = 20;

	/**
	 * Method invoked when running the program.
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		String line;

		while (true) {
			System.out.print("Unesite broj > ");

			line = scanner.next();
			if (line.equals("kraj")) {
				System.out.println("Doviđenja.");
				break;
			}

			try {
				int number = Integer.parseInt(line);
				if (!isInRange(number)) {
					System.out.format("'%d' nije broj u dozovljenom rasponu.\n", number);
				} else {
					System.out.format("%d! = %d\n", number, calculateFactorial(number));
				}
			} catch (NumberFormatException ex) {
				System.out.println("'" + line + "' nije cijeli broj.");
			}
		}

		scanner.close();

	}
	/**
	 * Calculates factorial of a certain positive number.
	 * @param number of whose factorial we want to calculate
	 * @throws IllegalArgumentException if the given number is less than 0 or grather than 20
	 * @return factorial of a positive number
	 */
	public static long calculateFactorial(int number) {
		if (number < 0 || number > FACTORIAL_UPPER_LIMIT) {
			throw new IllegalArgumentException("Argument metode mora biti pozitivan broj manji ili jednak 20!");
		}
		if (number == 1 || number == 0 ) {
			return 1;
		}
		return number * calculateFactorial(number - 1);
	}
	/**
	 * Checks if the given number is in the desired range.
	 * @param number that we want to check
	 * @return whether the number is in the desired interval
	 */
	private static boolean isInRange(int number) {
		return number >= LOWER_LIMIT && number <= UPPER_LIMIT;
	}

}
