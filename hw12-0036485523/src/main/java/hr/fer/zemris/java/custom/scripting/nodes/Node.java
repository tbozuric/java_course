package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Represents a general node in the document.
 */
public abstract class Node {
    /**
     * A field where all nodes are stored.
     */
    protected ArrayIndexedCollection nodes;

    /**
     * Creates an instance and allocate an array for children.
     */
    public Node() {
        this.nodes = new ArrayIndexedCollection();
    }

    /**
     * Adds the child to the current node.
     *
     * @param child to be added.
     */
    public void addChild(Node child) {
        nodes.add(child);
    }

    /**
     * Returns the number of children.
     *
     * @return number of children.
     */
    public int numberOfChildren() {
        return nodes.size();
    }

    /**
     * Returns node on the required index.
     *
     * @param index of the desired child.
     * @return node on the required index.
     */
    public Node getChild(int index) {
        return (Node) nodes.get(index);
    }

    /**
     * Accepts the given node visitor.
     *
     * @param visitor the node visitor.
     */
    public abstract void accept(INodeVisitor visitor);


}
