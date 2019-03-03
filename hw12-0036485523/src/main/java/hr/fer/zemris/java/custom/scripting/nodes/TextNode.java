package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Represents a text node.
 */
public class TextNode extends Node {
    /**
     * Text within text node.
     */
    private String text;

    /**
     * Creates an instance with text set to given text.
     *
     * @param text within text node.
     */
    public TextNode(String text) {
        this.text = text;
    }

    /**
     * Returns text within text node.
     *
     * @return text within text node.
     */
    public String getText() {
        return text;
    }

    @Override
    public void accept(INodeVisitor visitor){
        visitor.visitTextNode(this);
    }
}
