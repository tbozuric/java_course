package hr.fer.zemris.java.custom.collections;

/**
 * Represents linked list indexed collection of objects.
 * Duplicate elements are allowed, storage of null references is not allowed.
 * @author Tomislav Bozuric
 *
 */

public class LinkedListIndexedCollection extends Collection{
	
	/**
	 * A data structure representing a list node.
	 * Each node has a "pointer" to previous and next node, and value.
	 */
	private static class ListNode{
		ListNode next;
		ListNode previous;
		Object value;
	}
	
	/**
	 * Current size of collection.
	 */
	private int size;
	
	/**
	 * Reference to the first node of the linked list.
	 */
	private ListNode first;
	
	/**
	 * Reference to the last node of the linked list.
	 */
	private ListNode last;
	
	
	/**
	 * Default constructor.
	 */
	public LinkedListIndexedCollection() {
		
	}
	
	/**
	 * Creates an instance of linked list and copies elements from another collection.
	 * @param other collection which elements are copied into this newly created linked list
	 */
	public LinkedListIndexedCollection(Collection other) {
		if(other == null) {
			throw new NullPointerException("Collection must not have to be null!");
		}
		addAll(other);
	}
	
	/**
	 * Returns the number of currently stored objects.
	 * @return size of linked list indexed collection
	 */
	@Override
	public int size() {
		return size;
	}

	
	/**
	 * Adds the given object into this collection at the end of collection.
	 * @param object that will be added to the collection
	 * @throws NullPointerException if the given object is null
	 */
	@Override
	public void add(Object object) {
		if(object == null) {
			throw new NullPointerException("Object must not have to be null!");
		}
		ListNode node = new ListNode();
		node.value = object;
		if(size == 0) {
			first = node;
			last = node;
		}else {
			last.next = node;
			node.previous = last;
			last = node;
		}
		size++;
	}
	
	/**
	 * Checks if linked list contains given value.
	 * @param value that we want to find in linked list collection
	 * @return whether the value appears in collection
	 */
	@Override
	public boolean contains(Object value) {
		for(ListNode node  = first ; node != null ; node = node.next) {
			if(node.value.equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes object from linked list if it exists
	 * @param value  that we want to remove from collection
	 * @return whether the object has been successfully removed
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if(index != -1) {
			remove(index);
			return true;
		}
		return false;
	}
	
	/**
	 * Converts the linked list collection  to array of objects.
	 * @return array of objects that are in the collection 
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		int index = 0;
		for(ListNode node = first ; node != null ; node = node.next ) {
			array[index] = node.value;
			index++;
		}
		return array;
	}

	/**
	 * For each element in the collection, the processor processes it.
	 * @param processor that processes elements of the linked list collection
	 */
	@Override
	public void forEach(Processor processor) {
		for (ListNode node = first ; node != null ; node = node.next) {
			processor.process(node.value);
		}
	}
	
	/**
	 * Removes all elements from the collection.
	 */
	@Override
	public void clear() {
		first = last = null;
		size = 0;
	}
	
	/**
	 * Returns the object that is stored in linked list at position index.
	 * Method never has the complexity bigger than n/2 + 1.
	 * @param index of element
	 * @throws IndexOutOfBoundsException if the  index is not in range [0 , size -1 ] 
	 * @return object at given index
	 */
	public Object get(int index) {
		checkIndex(index, size - 1);
		return getListNode(index).value;
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in linked-list.
	 * @param value that will be added to the collection
	 * @param position to which the given value will be added
	 * @throws IndexOutOfBoundsException if the index is not in range [0 , size]
	 */
	public void insert(Object value, int position) {
		checkIndex(position, size);

		ListNode newNode = new ListNode();
		newNode.value = value;

		if (first == null) {
			first = last = newNode;
			size++;
			return;
		}

		int index = 0;
		ListNode node = first;

		while (index < position) {
			node = node.next;
			index++;
		}
		//position = size
		if (node == null) {
			add(newNode.value);
			return;
		} else {
			if (node.previous != null) {
				ListNode previousNode = node.previous;
				previousNode.next = newNode;
				newNode.previous = previousNode;
			} else {
				first = newNode;
			}
			newNode.next = node;
			node.previous = newNode;
		}
		size++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the given value or -1.
	 * @param value whose index we are looking for
	 * @return index of value if it exists or -1
	 */
	public int indexOf(Object value) {
		int index = 0;
		for(ListNode node = first ; node != null ; node = node.next) {
			if(node.value.equals(value)) {
				return index; 
			}
			index++;
		}
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection.
	 * @param index of object that we want to remove
	 * @throws IndexOutOfBoundsException if the given index is not in range [0 , size -1] 
	 */
	public void remove(int index) {
		checkIndex(index, size - 1);
		ListNode node = getListNode(index);
		ListNode previousNode = node.previous;
		ListNode nextNode = node.next;

		if (previousNode == null) {
			if (nextNode != null) {
				nextNode.previous = null;
				first = nextNode;
			} else {
				first = last = null;
			}
		} else {
			if (nextNode != null) {
				previousNode.next = nextNode;
				nextNode.previous = previousNode;
			} else {
				last = previousNode;
				last.next = null;
			}
		}
		size--;
	}
	
	/**
	 * Returns list node in linked-list collection at specified index
	 * @param index of list node 
	 * @return list node
	 */
	private ListNode getListNode(int index) {
		int i = 0;
		ListNode node;

		if (index < size / 2) {
			node = first;
			while (i < index) {
				node = node.next;
				i++;
			}
		} else {
			node = last;
			while (i < (size - 1) - index) {
				node = node.previous;
				i++;
			}
		}
		return node;
	}
	
}
