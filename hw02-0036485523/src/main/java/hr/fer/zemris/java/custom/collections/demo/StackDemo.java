package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
/**
 * A command-line program which accepts a single command-line argument: expression which should be evaluated. 
 * Expression must be in postfix representation.
 * @author Tomislav Bozuric
 *
 */
public class StackDemo {

	/**
	 * A message that will be printed if an expression is invalid.	
	 */
	private static final String INVALID_EXPRESSION_MESSAGE = "Expression is invalid!";
	
	/**
	 * Method invoked when running the program.
	 * @param args command-line arguments
	 * @throws IllegalArgumentException if the number of command-line arguments is not equal to 1.
	 * @throws NumberFormatException if the number in input argument is not an integer.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Number of parameters must be 1.");
		}
		String[] input = args[0].split("\\s+");
		ObjectStack stack = new ObjectStack();
		
		for (String element : input) {
			if (isNumeric(element)) {
				try {
					int number = Integer.parseInt(element);
					stack.push(number);
				} catch (NumberFormatException ex) {
					System.out.println(INVALID_EXPRESSION_MESSAGE + " Input numbers must be integers!");
					System.exit(1);
				}
			} else {
				if (!isOperator(element)) {
					System.out.println(INVALID_EXPRESSION_MESSAGE + " Operator " + element + " is not valid!");
					System.exit(1);
				}
				performOperation(element, stack);
			}
		}
		if(stack.size() != 1) {
			System.out.println(INVALID_EXPRESSION_MESSAGE);
			System.exit(1);
		}
		System.out.println("Expression evaluates to " + stack.pop() + ".");
	}
	
	/**
	 * Performs the operation over the operands and push result back on the stack
	 * @param operator  that will be applied
	 * @param stack reference to stack
	 * @throws EmptyStackException if there are not enough elements on the stack 
	 */
	private static void performOperation(String operator, ObjectStack stack) {
		try {
			int secondOperand = (int) stack.pop();
			int firstOperand = (int) stack.pop();
			int result = parseExpression(firstOperand, secondOperand, operator);
			stack.push(result);
		}catch(EmptyStackException ex) {
			System.out.println(INVALID_EXPRESSION_MESSAGE);
			System.exit(1);
		}
		
	}
	
	/**
	 * Parses the input operator and applies it to operands.
	 * @param firstOperand first operand
	 * @param secondOperand second operand 
	 * @param operator that will be applied to operands
	 * @return evaluated expression
	 */
	private static int parseExpression(int firstOperand, int secondOperand, String operator) {
		switch (operator) {
			case "+":
				return firstOperand + secondOperand;

			case "-":
				return firstOperand - secondOperand;

			case "*":
				return firstOperand * secondOperand;

			case "/":
				if (secondOperand == 0) {
					System.out.println("Division by zero is not allowed!");
					System.exit(1);
				}
				return firstOperand / secondOperand;

			default:
				return firstOperand % secondOperand;
		}

	}
	
	/**
	 * Checks if the given string is number.
	 * @param input any string 
	 * @return whether the given string is number
	 */
	private static boolean isNumeric(String input) {
		return input.matches("[-]?\\d*\\.?\\d+");
	}
	
	/**
	 * Checks if the given string is an operator.
	 * @param input any string
	 * @return whether the given string is operator
	 */
	private static boolean isOperator(String input) {
		return input.matches("[+\\-*/%]{1}");
	}
}
