package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

import java.util.Arrays;

/**
 * Represents parser for a "smart script".
 */
public class SmartScriptParser {

private static final String PARSE_ERROR = "Unable to parse document.";
/**
 * Reference to smart script lexer.
 */
private SmartScriptLexer lexer;
/**
 * Root node of the document.
 */
private DocumentNode documentNode;
/**
 * Stack used for parsing the expression.
 */
private ObjectStack stack;


/**
 * Creates an instance of smart script parser.Initiates the lexer and invites the parsing method.
 *
 * @param text for parsing.
 */
public SmartScriptParser(String text) {
    lexer = new SmartScriptLexer(text);
    documentNode = new DocumentNode();
    stack = new ObjectStack();
    parse();

}

/**
 * Returns the root node of the document.
 *
 * @return root document node.
 * @throws SmartScriptParserException if it is not possible to parse the input text.
 */
public DocumentNode getDocumentNode() {
    return documentNode;
}

/**
 * Parses the entire input document, or stops working if it finds a mistake.
 *
 * @throws SmartScriptParserException if it is not possible to parse the document.
 */
private void parse() {
    stack.push(documentNode);
    try {
        Token token = lexer.nextToken();
        boolean tagOpened = false;

        while (token.getType() != TokenType.EOF) {

            if (token.getType() == TokenType.TEXT) {
                TextNode textNode = new TextNode((String) token.getValue());
                ((Node) stack.peek()).addChild(textNode);

            } else if (token.getType() == TokenType.TAG_OPEN) {
                if (tagOpened) {
                    throw new SmartScriptParserException("The tag was previously open but is not yet closed.");
                }
                tagOpened = true;
                lexer.setState(LexerState.TAG);

            } else if (token.getType() == TokenType.TAG_NAME && token.getValue().equals("=")) {
                parseEchoTag(tagOpened);
                tagOpened = false;

            } else if (token.getType() == TokenType.TAG_NAME && ((String) token.getValue()).matches("[Ff][Oo][Rr]")) {
                parseForTag(tagOpened);
                tagOpened = false;

            } else if (token.getType() == TokenType.TAG_NAME && ((String) token.getValue()).matches("[Ee][Nn][Dd]")) {
                parseEndTag();
                tagOpened = false;
            } else {
                throw new SmartScriptParserException(PARSE_ERROR);
            }
            token = lexer.nextToken();
        }
        if (!(stack.peek() instanceof DocumentNode)) {
            throw new SmartScriptParserException(PARSE_ERROR);
        }
    } catch (LexerException | EmptyStackException exc) {
        throw new SmartScriptParserException(PARSE_ERROR);
    }
}

/**
 *
 * Parses the end tag that marks the end of the for loop.
 *
 * @throws SmartScriptParserException if there is no closing tag or if there are too many end tags in the document.
 */
private void parseEndTag() {
    Token token;
    try {
        stack.pop();
        token = lexer.nextToken();
        if (token.getType() != TokenType.TAG_CLOSE) {
            throw new SmartScriptParserException("There is no close tag!");
        }
        lexer.setState(LexerState.BASIC);
    } catch (EmptyStackException ex) {
        throw new SmartScriptParserException("There are too many \"END\" tags.");
    }
}

/**
 * Parses the echo tag in the input document.
 * The echo tag must contain numbers, functions, variables, strings, or operators.
 *
 * @param tagOpened indicates whether the tag("{$") was previously open.
 * @throws SmartScriptParserException if the echo tag is not valid.
 */
private void parseEchoTag(boolean tagOpened) {
    Token token;
    if (!tagOpened) {
        throw new SmartScriptParserException("There is no previously open tag.");
    }


    token = lexer.nextToken();
    ArrayIndexedCollection collection = new ArrayIndexedCollection();

    while (token.getType() != TokenType.TAG_CLOSE || token.getType() == TokenType.EOF) {

        switch (token.getType()) {
            case INT:
                ElementConstantInteger integer = new ElementConstantInteger((int) token.getValue());
                collection.add(integer);
                break;
            case DOUBLE:
                ElementConstantDouble elementConstantDouble = new ElementConstantDouble((double) token.getValue());
                collection.add(elementConstantDouble);
                break;
            case FUNCTION:
                ElementFunction elementFunction = new ElementFunction((String) token.getValue());
                collection.add(elementFunction);
                break;
            case VARIABLE:
                ElementVariable elementVariable = new ElementVariable((String) token.getValue());
                collection.add(elementVariable);
                break;
            case STRING:
                ElementString elementString = new ElementString((String) token.getValue());
                collection.add(elementString);
                break;
            case OPERATOR:
                ElementOperator elementOperator = new ElementOperator((String) token.getValue());
                collection.add(elementOperator);
                break;
            default:
                throw new SmartScriptParserException("Illegal argument in the empty-tag");
        }
        token = lexer.nextToken();
    }
    if (token.getType() == TokenType.EOF) {
        throw new SmartScriptParserException("There is no previously close tag!");
    }


    Element[] elements = Arrays.copyOf(collection.toArray(), collection.size(), Element[].class);
    EchoNode echoNode = new EchoNode(elements);

    ((Node) stack.peek()).addChild(echoNode);
    lexer.setState(LexerState.BASIC);

}

/**
 * Parses the for tag in the input document.ForLoopNode can have three or four parameters (as specified by user): first it must
 * have one ElementVariable and after that two or three Element s of type variable, number or string.
 *
 * @param tagOpened indicates whether the tag("{$") was previously open.
 * @throws SmartScriptParserException if the for tag is in the wrong format.
 */
private void parseForTag(boolean tagOpened) {

    Token token;
    if (!tagOpened) {
        throw new SmartScriptParserException("There is no previously open tag.");
    }
    token = lexer.nextToken();
    if (token.getType() != TokenType.VARIABLE) {
        throw new SmartScriptParserException("First element in the  for tag must be variable!");
    }

    ElementVariable elementVariable = new ElementVariable((String) token.getValue());
    token = lexer.nextToken();

    Element starExpression = getNumberOrVariableOrStringElement(token);
    token = lexer.nextToken();

    Element endExpression = getNumberOrVariableOrStringElement(token);
    token = lexer.nextToken();

    ForLoopNode forLoopNode;

    if (token.getType() == TokenType.TAG_CLOSE) {
        forLoopNode = new ForLoopNode(elementVariable, starExpression, endExpression, null);
        ((Node) stack.peek()).addChild(forLoopNode);
        stack.push(forLoopNode);
        lexer.setState(LexerState.BASIC);
    } else {
        Element stepExpression = getNumberOrVariableOrStringElement(token);
        forLoopNode = new ForLoopNode(elementVariable, starExpression, endExpression, stepExpression);
        token = lexer.nextToken();

        if (token.getType() != TokenType.TAG_CLOSE) {
            throw new SmartScriptParserException("Invalid format of for tag.");
        }

        ((Node) stack.peek()).addChild(forLoopNode);
        stack.push(forLoopNode);

        lexer.setState(LexerState.BASIC);
    }

}

/**
 * Returns a new element if it is type integer, double, variable  or string
 *
 * @param token whose value we want to "unpack"
 * @return the valid element
 * @throws SmartScriptParserException if the argument inside the for tag is not valid.
 */
private Element getNumberOrVariableOrStringElement(Token token) {
    switch (token.getType()) {
        case INT:
            return new ElementConstantInteger((int) token.getValue());
        case DOUBLE:
            return new ElementConstantDouble((double) token.getValue());
        case VARIABLE:
            return new ElementVariable((String) token.getValue());
        case STRING:
            return new ElementString((String) token.getValue());
        default:
            throw new SmartScriptParserException("Invalid argument inside for tag.");
    }
}

}
