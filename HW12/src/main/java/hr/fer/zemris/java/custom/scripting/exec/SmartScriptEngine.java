package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.exec.Actions.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Represents smart script engine. Its job is to actually execute the document whose parsed tree in obtains.
 * Supported functions are:
 * <p>
 * <code>sin(x)</code> ; calculates sinus from given argument and stores the result back to stack.
 * </p>
 * <p> <code>decfmt(x,f)</code> ; formats decimal number using given format f which is compatible with
 * {@link java.text.DecimalFormat};
 * </p>
 * <p> <code>dup() </code>; duplicates current top value from stack.</p>
 * <p> <code>swap()</code> ; replaces the order of two topmost items on stack.</p>
 * <p> <code>setMimeType(x)</code> ; takes string x and calls {@link RequestContext#setMimeType(String)}.
 * Does not produce any result.</p>
 * <p> <code>paramGet(name, defValue)</code> ; Obtains from requestContext parameters map a value mapped for
 * name and pushes it onto stack. If there is no such mapping, it pushes instead defValue onto stack.</p>
 * <p><code>pparamGet(name, defValue)</code> ; same as paramGet but reads from {@link RequestContext#persistentParameters}
 * .</p>
 * <p><code>pparamSet(value, name) </code>; stores a value into {@link RequestContext#persistentParameters} map.
 * </p>
 * <p><code>pparamDel(name) </code>; removes association for name from {@link RequestContext#persistentParameters}
 * map.</p>
 * <p><code>tparamGet(name, defValue) </code>; same as paramGet but reads from
 * {@link RequestContext#temporaryParameters} map.</p>
 * <p><code>tparamSet(value, name) </code>; stores a value into {@link RequestContext#temporaryParameters} map. </p>
 * <p><code>tparamDel(name) </code>; removes association for name from {@link RequestContext#temporaryParameters}
 * map.</p>
 */
public class SmartScriptEngine {

    /**
     * The document node.
     */
    private DocumentNode documentNode;

    /**
     * The request context.
     *
     * @see RequestContext
     */
    private RequestContext requestContext;

    /**
     * The object multistack.
     */
    private ObjectMultistack multistack = new ObjectMultistack();

    /**
     * The map  containing "more complex" actions that can be executed.
     */
    private Map<String, Action> actions = new HashMap<>();

    /**
     * Represents a visitor who visits the generated tree of the parsed input text.
     */
    private INodeVisitor visitor = new INodeVisitor() {
        @Override
        public void visitTextNode(TextNode node) {
            try {
                requestContext.write(node.getText());
            } catch (IOException e) {
                System.out.println("An error occurred while writing.");
            }
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            String name = node.getVariable().getName();
            multistack.push(name, new ValueWrapper(Integer.valueOf(node.getStartExpression().asText())));
            int end = Integer.parseInt(node.getEndExpression().asText());
            while ((Integer) multistack.peek(name).getValue()
                    <= end) {
                for (int i = 0; i < node.numberOfChildren(); i++) {
                    node.getChild(i).accept(this);
                }
                ValueWrapper temp = multistack.pop(name);
                temp.add(Integer.parseInt(node.getStepExpression().asText()));
                multistack.push(name, temp);
            }
            multistack.pop(name);
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            Stack<Object> temporary = new Stack<>();
            for (Element elem : node.getElements()) {
                if (elem instanceof ElementString) {
                    String str = elem.asText();
                    str = str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\""));
                    temporary.push(str);
                } else if (elem instanceof ElementVariable) {
                    temporary.push(multistack.peek(elem.asText()).getValue());
                } else if (elem instanceof ElementOperator) {
                    performOperation(temporary, (ElementOperator) elem);
                } else if (elem instanceof ElementFunction) {
                    processFunction(elem, temporary);
                } else {
                    temporary.push(elem.asText());
                }
            }
            writeData(temporary);
        }

        /**
         * Writes data to the {@link RequestContext#outputStream}.
         * @param temporary the temporary stack.
         */
        private void writeData(Stack<Object> temporary) {
            Stack<Object> reversed = new Stack<>();
            while (!temporary.isEmpty())
                reversed.push(temporary.pop());
            while (!reversed.isEmpty()) {
                try {
                    requestContext.write(String.valueOf(reversed.pop()));
                } catch (IOException e) {
                    System.out.println("An error occurred while writing.");
                }
            }
        }

        /**
         * Performs an operation between operands. Possible actions are multiplication, division, addition and subtraction
         * @param temporary the temporary stack.
         * @param elem the operator between operands.
         * @throws IllegalArgumentException if operands are not null , Integer , Double or String.
         */
        private void performOperation(Stack<Object> temporary, ElementOperator elem) {
            Object secondOperand = temporary.pop();
            Object firstOperand = temporary.pop();

            ValueWrapper first = new ValueWrapper(firstOperand);
            ValueWrapper second = new ValueWrapper(secondOperand);
            ValueWrapper result;

            switch (elem.getSymbol()) {
                case "+":
                    first.add(second.getValue());
                    break;
                case "-":
                    first.subtract(second.getValue());
                    break;
                case "*":
                    first.multiply(second.getValue());
                    break;
                case "/":
                    first.divide(second.getValue());
                    break;
            }
            result = new ValueWrapper(first.getValue());
            Object res = result.getValue();
            temporary.push(res);
        }

        /**
         * Method performs one of the possible action options.
         *
         * @param elem      the element.
         * @param temporary the temporary stack.
         */
        private void processFunction(Element elem, Stack<Object> temporary) {
            ElementFunction el = (ElementFunction) elem;
            switch (el.getName()) {
                case "@sin":
                    actions.get("sin").execute(temporary);
                    break;

                case "@decfmt":
                    actions.get("decfmt").execute(temporary);
                    break;

                case "@dup":
                    actions.get("dup").execute(temporary);
                    break;
                case "@swap":
                    actions.get("swap").execute(temporary);
                    break;
                case "@setMimeType":
                    requestContext.setMimeType(String.valueOf(temporary.pop()));
                    break;
                case "@paramGet":
                    temporary.push(requestContext.getParameters());
                    actions.get("paramGet").execute(temporary);
                    break;
                case "@pparamGet":
                    temporary.push(requestContext.getPersistentParameters());
                    actions.get("paramGet").execute(temporary);
                    break;

                case "@tparamGet":
                    temporary.push(requestContext.getTemporaryParameters());
                    actions.get("paramGet").execute(temporary);
                    break;

                case "@pparamSet":
                    requestContext.setPersistentParameter(String.valueOf(temporary.pop()),
                            String.valueOf(temporary.pop()));
                    break;


                case "@pparamDel":
                    requestContext.removePersistentParameter(String.valueOf(temporary.pop()));
                    break;


                case "@tparamSet":
                    requestContext.setTemporaryParameter(String.valueOf(temporary.pop()),
                            String.valueOf(temporary.pop()));
                    break;

                case "@tparamDel":
                    requestContext.removeTemporaryParameter(String.valueOf(temporary.pop()));
                    break;

            }

        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            for (int i = 0; i < node.numberOfChildren(); i++) {
                node.getChild(i).accept(this);
            }
        }
    };

    /**
     * Creates an instance of {@link SmartScriptEngine}.
     *
     * @param documentNode   the document node.
     * @param requestContext the request context.
     */
    public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
        this.documentNode = documentNode;
        this.requestContext = requestContext;
        initActions();
    }

    /**
     * Initiates map of "more complex" actions.
     */
    private void initActions() {
        actions.put("sin", new SinusAction());
        actions.put("decfmt", new DecimalFormatAction());
        actions.put("dup", new DuplicateAction());
        actions.put("swap", new SwapAction());
        actions.put("paramGet", new ParameterGetAction());
    }

    /**
     * Method begins to tour the tree.
     */
    public void execute() {
        documentNode.accept(visitor);
    }
}
