package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Represents the root node of the document.
 */
public class DocumentNode extends Node {

    @Override
    public void accept(INodeVisitor visitor){
        visitor.visitDocumentNode(this);
    }
}
