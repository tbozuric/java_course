package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Represents the print node.
 */
public class EchoNode extends Node {
    /**
     * Echo node elements.
     */
    private Element[] elements;

    /**
     * Creates an instance with elements set to given elements.
     *
     * @param elements belonging to the node.
     */
    public EchoNode(Element[] elements) {
        this.elements = elements;
    }

    /**
     * Returns the elements belonging to the echo node.
     *
     * @return elements belonging to the echo node.
     */
    public Element[] getElements() {
        return elements;
    }

    @Override
    public void accept(INodeVisitor visitor){
        visitor.visitEchoNode(this);
    }
}
