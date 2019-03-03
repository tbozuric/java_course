package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

/**
 * Simple tests to check the methods that work with a ordered binary tree.
 * Tests include checking the size of the tree and whether the tree contains some value.
 * @author Tomislav Bozuric
 *
 */

public class UniqueNumbersTest {

	@Test
	public void sizeOfTreeWithNodes() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 42);
		head = UniqueNumbers.addNode(head, 76);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 76);
		Assert.assertEquals(3, UniqueNumbers.treeSize(head));
	}

	@Test
	public void sizeOfTreeWithoutNodes() {
		TreeNode head = null;
		Assert.assertEquals(0, UniqueNumbers.treeSize(head));
	}

	@Test
	public void treeContainsValue() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 42);
		head = UniqueNumbers.addNode(head, 76);
		Assert.assertEquals(true, UniqueNumbers.containsValue(head, 42));
	}

	@Test
	public void treeNotContainsValue() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 55);
		Assert.assertEquals(false, UniqueNumbers.containsValue(head, 20));
	}

}
