package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Represents a loop node.
 */
public class ForLoopNode extends Node {
    /**
     * Variable in for loop node.
     */
    private ElementVariable variable;

    /**
     * Initial variable value.
     */
    private Element startExpression;

    /**
     * The final value of the variable.
     */
    private Element endExpression;

    /**
     * Step for the variable.
     */
    private Element stepExpression;

    /**
     * Creates an instance of for loop node.
     *
     * @param variable        in for loop node.
     * @param startExpression initial variable value.
     * @param endExpression   final value of the variable.
     * @param stepExpression  step.
     */
    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * Returns variable.
     *
     * @return variable
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * Returns start expression.
     *
     * @return initial value of the variable.
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Returns final value of the variable.
     *
     * @return final value of the variable.
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * Returns step in for loop.
     *
     * @return step.
     */
    public Element getStepExpression() {
        return stepExpression;
    }

    @Override
    public void accept(INodeVisitor visitor){
        visitor.visitForLoopNode(this);
    }
}
