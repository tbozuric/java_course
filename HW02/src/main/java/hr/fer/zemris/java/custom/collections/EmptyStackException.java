package hr.fer.zemris.java.custom.collections;

/**
 * Thrown to indicate that stack is empty.
 * @author Tomislav Bozuric
 *
 */


public class EmptyStackException extends RuntimeException{
	

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code EmptyStackException} with no detail message.
	 */
	public EmptyStackException() {
		
	}
	
	/**
	 * Constructs an {@code IndexOutOfBoundsException} with the specified detail message.
	 * @param message the detail message
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}
