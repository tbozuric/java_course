package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * The program adds unique numbers to the ordered binary tree.
 * You can print the number of a nodes in the tree, check whether the tree contains a certain element,
 * print nodes from the smallest element to the largest and vice versa.
 * @author Tomislav Bozuric
 *
 */
public class UniqueNumbers {
	
	/**
	 * A data structure representing a tree node.
	 * Each node has a "pointer" to its children and value.
	 */
	static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}

	/**
	 * Method invoked when running the program.
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		TreeNode head = null;
		String line;

		while (true) {
			System.out.print("Unesite broj > ");
			line = scanner.next();

			if (line.equals("kraj")) {
				break;
			}

			try {
				int number = Integer.parseInt(line);
				if (!containsValue(head, number)) {
					head = addNode(head, number);
					System.out.println("Dodano.");
				} else {
					System.out.println("Broj već postoji. Preskačem.");
				}
			} catch (NumberFormatException ex) {
				System.out.println("'" + line + "' nije cijeli broj.");
			}
		}
		System.out.print("Ispis od najmanjeg : ");
		printNumbersInAscendingOrder(head);
		System.out.println();
		System.out.print("Ispis od najvećeg : ");
		printNumbersInDescendingOrder(head);
		scanner.close();
	}
	
	/**
	 * Adds a new node to the ordered binary tree(smaller elements left, bigger elements right).
	 * @param root of the tree
	 * @param value that will be added if it does not exist in the tree
	 * @return root of the tree
	 */
	public static TreeNode addNode(TreeNode root, int value) {
		if (root == null) {
			root = new TreeNode();
			root.value = value;
			return root;
		}
		
		if (value == root.value) {
			return root;
		} else if (value < root.value) {
			root.left = addNode(root.left, value);
		} else {
			root.right = addNode(root.right, value);
		}
		
		return root;
	}
	
	/**
	 * Prints the total number of nodes in the ordered binary tree.
	 * @param root of the tree
	 * @return number of nodes
	 */
	public static int treeSize(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return 1 + treeSize(root.left) + treeSize(root.right);
	}
	
	/**
	 * Verifies whether the tree contains a node with a given value.
	 * @param root of the tree
	 * @param value 
	 * @return whether the value appears in the ordered binary tree
	 */
	public static boolean containsValue(TreeNode root, int value) {
		if (root == null) {
			return false;
		}
		if (value == root.value) {
			return true;
		} else if (value < root.value) {
			return containsValue(root.left, value);
		} else {
			return containsValue(root.right, value);
		}
	}
	
	/**
	 * Prints the elements of the tree in ascending order(inorder print).
	 * @param root of the tree
	 */
	public static void printNumbersInAscendingOrder(TreeNode root) {
		if (root != null) {
			printNumbersInAscendingOrder(root.left);
			System.out.print(root.value + " ");
			printNumbersInAscendingOrder(root.right);
		}
	}
	
	/**
	 * Prints the elements of the tree in descending order.
	 * @param root of the tree
	 */
	public static void printNumbersInDescendingOrder(TreeNode root) {
		if (root != null) {
			printNumbersInDescendingOrder(root.right);
			System.out.print(root.value + " ");
			printNumbersInDescendingOrder(root.left);
		}
	}

}
