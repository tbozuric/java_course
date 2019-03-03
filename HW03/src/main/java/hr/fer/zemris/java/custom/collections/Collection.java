package hr.fer.zemris.java.custom.collections;

/**
 * Represents some general collection of objects. It offers various methods for working with collections.
 * @author Tomislav Bozuric
 *
 */
public class Collection {
	
	/**
	 * Default constructor
	 */
	protected Collection() {

	}
	
	/**
	 * Checks if the collection is empty
	 * @return whether the collection is empty
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns size of the collection. In this class the method is implemented to return zero.
	 * The class that extends Collection must offer a correct implementation.
	 * @return 0
	 */
	public int size() {
		return 0;
	}
	/**
	 * Adds object to collection. In this class the method is empty , so it does nothing.
	 * The class that extends Collection must offer a correct implementation.
	 * @param object that will be added to the collection
	 */
	
	public void add(Object object) {

	}
	
	/**
	 * Checks if collection contains given value. In this class the method is implemented to return false.
	 * The class that extends Collection must offer a correct implementation.
	 * @param value that we want to find in linked list collection
	 * @return false
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Removes object from collection if it exists. In this class the method is implemented to return false.
	 * The class that extends Collection must offer a correct implementation.
	 * @param value that we want to remove from collection
	 * @return false
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Converts collection to array of objects. In this class the method is implemented to throw {@link UnsupportedOperationException}.
	  * The class that extends Collection must offer a correct implementation.
	 * @throws UnsupportedOperationException 
	 * @return {@link UnsupportedOperationException}
	 */
	
	public Object[] toArray() {
		throw new UnsupportedOperationException("Operation is unsupported!");
	}

	/**
	 * For each element in collection, the processor processes it. In this class the method is empty, so it does nothing.
	 * The class that extends Collection must offer a correct implementation.
	 * @param processor that processes elements of the array-indexed collection
	 */
	
	public void forEach(Processor processor) {

	}
	
	/**
	 * Adds into the current collection all elements from the given collection. This other collection remains unchanged.
	 * @param other collection
	 */
	public void addAll(Collection other) {
		class CollectionProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new CollectionProcessor());
	}
	
	/**
	 * Removes all elements from the collection. In this class the method is empty, so it does nothing.
	 * The class that extends Collection must offer a correct implementation.
	 */
	public void clear() {
		
	}

	void checkIndex(int index, int upperLimit) {
		if(upperLimit < 0){
			throw new IndexOutOfBoundsException("Can not remove element from empty collection!");
		}
		if (index > upperLimit && upperLimit == 0) {
			throw new IndexOutOfBoundsException("Valid index is only 0. Was  " + index);
		}
		if (index < 0 || index > upperLimit) {
			throw new IndexOutOfBoundsException("Valid indices are 0 to " + upperLimit + ". Was " + index);
		}
	}
	
}
