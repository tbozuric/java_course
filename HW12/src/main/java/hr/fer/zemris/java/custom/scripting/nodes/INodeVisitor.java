package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The interface that represents the node visitor.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Visitor_pattern">Vistor design pattern</a>
 */
public interface INodeVisitor {

    /**
     * Method that visits the text node and performs the relevant actions.
     *
     * @param node text node.
     */
    void visitTextNode(TextNode node);

    /**
     * Method that visits the for loop node and performs the relevant actions.
     *
     * @param node the for loop node.
     */
    void visitForLoopNode(ForLoopNode node);

    /**
     * Method that visits the echo node and performs the relevant actions.
     *
     * @param node the echo node.
     */
    void visitEchoNode(EchoNode node);

    /**
     * Method that visits the document node and performs the relevant actions.
     *
     * @param node the document node.
     */
    void visitDocumentNode(DocumentNode node);
}

