package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ObjectStackTest {
	
	
	private ObjectStack stack;
	
	@Before
	public void initialize() {
		stack = new ObjectStack();
		stack.push("Zagreb");
		stack.push("New York");
		stack.push("San Francisco");
	}
	
	@Test
	public void isEmpty() {
		Assert.assertFalse(stack.isEmpty());
	}
	
	@Test
	public void push() {
		Assert.assertEquals(3, stack.size());
	}
	
	@Test
	public void peek() {
		Assert.assertTrue(stack.peek().equals("San Francisco"));
		Assert.assertTrue(stack.size() == 3);
	}
	
	@Test(expected = EmptyStackException.class)
	public void peekEmpty() {
		ObjectStack testPeekStack = new ObjectStack();
		testPeekStack.peek();
	}
	
	@Test
	public void pop() {
		Assert.assertEquals("San Francisco", stack.pop());
		Assert.assertTrue(stack.size() == 2);
		
	}
	
	@Test(expected = EmptyStackException.class)
	public void popEmpty() {
		stack.clear();
		stack.pop();
	}

	@Test
	public void clear() {
		stack.clear();
		Assert.assertTrue(stack.isEmpty());
	}
	@Test
	public void size() {
		Assert.assertTrue(stack.size() == 3);
	}
}
