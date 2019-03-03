package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Represents resizable array-backed collection of objects.
 * Duplicate elements are allowed, storage of null references is not allowed.
 * @author Tomislav Bozuric
 *
 */

public class ArrayIndexedCollection extends Collection {

	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	/**
	 * Current size of collection.
	 */
	private int size;
	
	/**
	 * An array of object references
	 */
	private Object[] elements;

	/**
	 * Default constructor creates an instance with capacity set to 16.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_INITIAL_CAPACITY);
	}
	
	/**
	 * Creates an instance with capacity set to initial capacity.
	 * @param initialCapacity initial capacity of array-backed collection
	 * @throws IllegalArgumentException if the given initial capacity is less than 1.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity <= 0){
			throw new IllegalArgumentException("Initial capacity can not be less than 1. Was " + initialCapacity);
		}
		elements = new Object[initialCapacity];
	}
	
	/**
	 * Creates an instance with capacity set to 16 and copies elements from another collection.
	 * @param other collection which elements are copied into this newly created collection
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_INITIAL_CAPACITY);
	}
	
	/**
	 * Creates an instance with capacity set to initial capacity and copies elements from another collection.
	 * @param other collection which elements are copied into this newly created collection
	 * @param initialCapacity initial capacity of array_backed collection
	 * @throws NullPointerException if the given collection is null
	 * @throws IllegalArgumentException if the given initial capacity is less than 1.
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (other == null){
			throw new NullPointerException("Collection must not be null!");
		}

		if (initialCapacity <= 0){
			throw new IllegalArgumentException("Initial capacity can not be less than 1. Was " + initialCapacity);
		}

		final int collectionSize = other.size();
		initialCapacity = collectionSize > initialCapacity ? collectionSize : initialCapacity;
		
		this.elements = new Object[initialCapacity];
		addAll(other);

	}

	/**
	 * Returns the number of currently stored objects.
	 * @return size of array-backed collection
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Adds a new object at the end of array-indexed collection. 
	 * @param object that will be added to the collection
	 * @throws NullPointerException if the given object is null
	 */
	@Override
	public void add(Object object) {
		if (object == null) {
			throw new NullPointerException("Collection  must not have null elements!");
		}
		if (size == elements.length) {
			resize();
		}
		elements[size] = object;
		size++;
	}
	
	/**
	 * Checks if array-indexed collection contains given value.
	 * @param value we want to find in array-indexed collection
	 * @return whether the object appears in array-indexed collection
	 */
	@Override
	public boolean contains(Object value) {
		if (value == null) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes object from array-indexed collection if it exists
	 * @param value we want to remove from array-indexed collection
	 * @return whether the object has been removed
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if (index != -1) {
			remove(index);
			return true;
		}
		return false;
	}
	
	/**
	 * Converts the array-indexed collection to array of objects.
	 * @return array of objects that are in the collection 
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}

	/**
	 * For each element in the collection, the processor processes it.
	 * @param processor that processes elements of the array-indexed collection
	 */
	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Removes all elements from the collection.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Returns the object that is stored in backing array at position index.
	 * @param index of element 
	 * @throws IndexOutOfBoundsException if the  index is not in range [0 , size -1 ]
	 * @return object at given position
	 */
	public Object get(int index) {
		checkIndex(index, size - 1);
		return elements[index];
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in array.
	 * @param value that will be added to the collection
	 * @param position to which the given value will be added
	 * @throws IndexOutOfBoundsException if the index is not in range [0 , size]
	 */
	public void insert(Object value, int position) {
		checkIndex(position, size);

		if (size == elements.length) {
			resize();
		}

		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}
		elements[position] = value;
		size++;
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is not found.
	 * @param value whose index we are looking for
	 * @return index if object exists in the collection or -1
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Removes element from specified index from collection and shifts collection to the left
	 * @param index of object that we want to remove
	 * @throws IndexOutOfBoundsException if the given index is not in range [0, size - 1]
	 */
	public void remove(int index) {
		int upperLimit = size - 1;
		checkIndex(index, upperLimit);

		for (int i = index; i < upperLimit; i++) {
			elements[i] = elements[i + 1];
		}
		elements[upperLimit] = null;
		size--;
	}
	
	/**
	 * Increases the capacity of collection (capacity * 2)
	 */
	private void resize() {
		elements = Arrays.copyOf(elements, elements.length * 2);
	}

}
