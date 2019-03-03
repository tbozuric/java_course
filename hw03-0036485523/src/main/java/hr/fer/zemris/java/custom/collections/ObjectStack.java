package hr.fer.zemris.java.custom.collections;

/**
 * This class represents a stack. In the background we use array indexed collection for actual element storage(as a Adaptee).
 * For more info  : @see <a href="http://en.wikipedia.org/wiki/Adapter_pattern">Adapter pattern</a>
 * @author Tomislav Bozuric
 *
 */

public class ObjectStack {
	
	/**
	 * An array of object references.
	 */
	private ArrayIndexedCollection arrayIndexedCollection;
	
	/**
	 * Empty stack exception message.
	 */
	private static final String EMPTY_STACK_EXCEPTION_MESSAGE = "Stack is empty!";
	
	/**
	 * Creates new instance of "stack".
	 */
	public ObjectStack() {
		arrayIndexedCollection = new ArrayIndexedCollection();
	}
	
	/**
	 * Checks if the stack is empty.
	 * @return whether the stack is empty
	 */
	public boolean isEmpty() {
		return arrayIndexedCollection.isEmpty();
	}
	
	/**
	 * Returns the number of currently stored objects.
	 * @return number of elements on the stack
	 */
	public int size() {
		return arrayIndexedCollection.size();
	}
	
	/**
	 * Pushes given value on the stack.
	 * @param value that will be added on the stack
	 */
	public void push(Object value) {
		arrayIndexedCollection.add(value);
	}
	
	/**
	 * Removes last value pushed on stack from the stack and returns it.
	 * @return last removed value
	 */
	public Object pop() {
		int sizeOfArray = arrayIndexedCollection.size();
		if(sizeOfArray == 0) {
			throw new EmptyStackException(EMPTY_STACK_EXCEPTION_MESSAGE);
		}
		Object value = arrayIndexedCollection.get(sizeOfArray -1);
		arrayIndexedCollection.remove(sizeOfArray -1);
		return value;
	}
	
	/**
	 * Returns last element placed on the stack.
	 * @return last element on the stack
	 */
	public Object peek() {
		int sizeOfArray = arrayIndexedCollection.size();
		if(sizeOfArray == 0 ) {
			throw new EmptyStackException(EMPTY_STACK_EXCEPTION_MESSAGE);
		}
		return arrayIndexedCollection.get(sizeOfArray -1 );
		
	}
	
	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		arrayIndexedCollection.clear();
	}

}
