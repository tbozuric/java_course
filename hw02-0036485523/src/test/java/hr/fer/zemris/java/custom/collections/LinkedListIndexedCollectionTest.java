package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LinkedListIndexedCollectionTest {

	private LinkedListIndexedCollection collection;
	
	@Before 
	public void initialize() {
		collection = new LinkedListIndexedCollection();
		collection.add("Zagreb");
		collection.add("New York");
		collection.add("San Francisco"); 
	}
	
	@Test
	public void add() {
		Assert.assertTrue(collection.size() == 3);
		Assert.assertEquals("San Francisco", collection.get(2));
	}
	
	@Test
	public void getAtPosition() {
		Assert.assertTrue(collection.get(0).equals("Zagreb"));
		Assert.assertTrue(collection.get(2).equals("San Francisco"));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void getAtWrongPosition() {
		collection.get(5);	
	}
	
	@Test
	public void clear() {
		collection.clear();
		Assert.assertEquals(0, collection.size());
		Assert.assertTrue(collection.isEmpty());
	}
	
	@Test
	public void insertAtPosition() {
		collection.insert("Zagreb",0);
		collection.insert("Los Angeles", 1);
		collection.insert("Osijek", 0);
		Assert.assertEquals(collection.get(0), "Osijek");
		Assert.assertEquals(collection.get(2), "Los Angeles");
	}
	
	@Test(expected= IndexOutOfBoundsException.class)
	public void insertAtWrongPosition() {
		collection.insert("Split", 6);
	}
	
	@Test
	public void indexOfItemInCollection() {
		Assert.assertEquals(0, collection.indexOf("Zagreb"));
		Assert.assertEquals(2,collection.indexOf("San Francisco"));
	}
	
	@Test
	public void indexOfNonExistsItemInCollection() {
		Assert.assertEquals(-1, collection.indexOf("Novi Marof"));
	}
	
	@Test
	public void removeAtPosition(){
		collection.remove(0);
		Assert.assertEquals("New York", collection.get(0));
		collection.remove(0);
		collection.remove(0);
		Assert.assertTrue(collection.isEmpty());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void removeAtWrongPosition() {
		collection.remove(3);
	}
	
	@Test
	public void isEmpty() {
		Assert.assertFalse(collection.isEmpty());
	}
	
	@Test
	public void addAll() {
		LinkedListIndexedCollection other = new LinkedListIndexedCollection();
		other.add("Milan");
		collection.addAll(other);
		Assert.assertTrue(collection.size() == 4);
	}
}
