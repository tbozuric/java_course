package hr.fer.zemris.java.hw01;


import java.util.Scanner;
/**
 * The program calculates the perimeter and area of a rectangle for the given width and height.
 * You can set the height and width over the command line arguments, or enter it manually.
 * The width and height of the rectangle must be positive numbers.
 * @author Tomislav Bozuric
 *
 */
public class Rectangle {


	private static final double ZERO_THRESHOLD = 1E-6;

	/**
	 * Method invoked when running the program.
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {

		if (args.length != 0 && args.length != 2) {
			System.out.println("Program preko komandne linije mora primiti širinu i visinu pravokutnika!");
			System.exit(1);
		}

		double width = 0.0;
		double height = 0.0;

		if (args.length == 2) {
			try {
				width = Double.parseDouble(args[0]);
				height = Double.parseDouble(args[1]);
				if (!isNumberPositive(width) || !isNumberPositive(height)) {
					System.out.println("Oba ulazna broja moraju biti strogo pozitivni!");
					System.exit(1);
				}
			} catch (NumberFormatException ex) {
				System.out.println("Ulazni argumenti se ne mogu protumačiti kao brojevi.");
				System.exit(1);
			}
		} else {
			Scanner scanner = new Scanner(System.in);
			width = loadParameter("Učitaj širinu > ", scanner);
			height = loadParameter("Učitaj visinu > ", scanner);
			scanner.close();
		}
		printAreaAndPerimeter(width, height);
	
	}
	/**
	 * Calculates the area of a rectangle.
	 * @param width of a rectangle
	 * @param height of a rectangle
	 * @return area of a rectangle
	 */
	public static double calculateArea(double width, double height) {
		return width * height;
	}
	
	/**
	 * Calculates the perimeter of a rectangle.
	 * @param width of a rectangle
	 * @param height of a rectangle
	 * @return perimeter of a rectangle
	 */
	public static double calculatePerimeter(double width, double height) {
		return 2 * (width + height);
	}

	/**
	 * Prints on the standard output the area and perimeter of a rectangle.
	 * @param width of a rectangle
	 * @param height of a rectangle
	 */
	public static void printAreaAndPerimeter(double width, double height) {
		System.out.println("Pravokutnik širine " + width + " i visine " + height + " ima površinu "
				+ calculateArea(width, height) + " te opseg " + calculatePerimeter(width, height));
	}

	/**
	 * Reads from the standard input until a positive number is loaded.
	 * @param message that will be printed on the standard output until the condition is met
	 * @return loaded parameter
	 */
	private static double loadParameter(String message, Scanner scanner) {
		String line = null;

		while (true) {
			System.out.print(message);

			try {
				line = scanner.next();
				double number = Double.parseDouble(line);
				if (isZero(number)) {
					System.out.println("Niti širina, niti visina pravokutnika ne mogu biti jednaki nuli!");
					continue;
				}
				if (!isNumberPositive(number)) {
					System.out.println("Unijeli ste negativnu vrijednost.");
					continue;
				}
				return number;
			} catch (NumberFormatException ex) {
				System.out.println("'" + line + "' se ne može protumačiti kao broj.");
			}

		}
	}

	/**
	 * Checks if the given number is positive.
	 * @param number that we want to check
	 * @return whether the number is positive
	 */
	private static boolean isNumberPositive(double number) {
		return number > 0;
	}
	
	/**
	 * Checks if the given number is zero.
	 * @param number that we want to check
	 * @return whether the number is equal to zero
	 */
	private static boolean isZero(double number) {
		return number >= -ZERO_THRESHOLD && number <= ZERO_THRESHOLD;
	}

}
