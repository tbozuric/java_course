package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A simple program that checks the correctness of parser operation.
 */
public class SmartScriptTester {
    /**
     * Close tag in document.
     */
    private static final String TAG_CLOSE = "$}";
    /**
     * Open tag in document.
     */
    private static final String TAG_OPEN = "{$";
    /**
     * Tag end in document.
     */
    private static final String TAG_END = "{$END$}";
    /**
     * For tag in document.
     */
    private static final String TAG_FOR = "{$ FOR ";

    /**
     * Method invoked when running the program.
     *
     * @param args path to document(must be examples/doc1.txt or examples/doc2.txt).
     * @throws SmartScriptParserException if the document can not be parsed because it is invalid.
     * @throws IllegalArgumentException   if path to the document is not provided.
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            throw new IllegalArgumentException("You must provide path to the document");
        }

        Path filePath = Paths.get(args[0]);
        if (!filePath.toFile().exists()) {
            throw new IllegalArgumentException("File does not exist.");
        }

        SmartScriptParser parser = null;
        try {
            String docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
            parser = new SmartScriptParser(docBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class.");
            System.exit(-1);
        }
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = createOriginalDocumentBody(document, "");
        System.out.println(originalDocumentBody); // should write something like original

        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
        System.out.println("--------------------------------------");
        String documentBody = createOriginalDocumentBody(document2, "");
        System.out.println(documentBody);
    }

    /**
     * Creates original document body.
     *
     * @param document is a node inheriting the Node class. {@link Node}
     * @param text     to which the body of the document is added.
     * @return original document body.
     */
    private static String createOriginalDocumentBody(Node document, String text) {
        for (int i = 0; i < document.numberOfChildren(); i++) {
            if (document instanceof ForLoopNode && i == 0) {
                text = createForLoopBody((ForLoopNode) document, text);
            }
            text = createOriginalDocumentBody(document.getChild(i), text);
        }


        if (document instanceof TextNode) {
            String textNodeValue = ((TextNode) document).getText();
            textNodeValue = textNodeValue.replaceAll("(\\\\)", "\\\\\\\\");
            //iz sigurnosnih razloga dodaj escape na svim pozicijama u tekstu s {....
            textNodeValue = textNodeValue.replaceAll("\\{", "\\\\{");
            text += textNodeValue + " ";
            return text;
        }

        if (document instanceof EchoNode) {
            Element[] elements = ((EchoNode) document).getElements();
            text += TAG_OPEN + "= ";
            StringBuilder textBuilder = new StringBuilder(text);
            for (Element element : elements) {
                textBuilder.append(element.asText()).append(" ");
            }
            text = textBuilder.toString();
            return text + TAG_CLOSE;
        }
        if (document instanceof ForLoopNode) {
            text += TAG_END;
        }
        return text;
    }

    /**
     * Creates body for ForLoopNode {@link ForLoopNode}
     *
     * @param document {@link ForLoopNode}
     * @param text     currently body of the document.
     * @return body of for loop node.
     */
    private static String createForLoopBody(ForLoopNode document, String text) {
        text += TAG_FOR;

        String variable = document.getVariable().asText();
        String startExpression = document.getStartExpression().asText();
        String endExpression = document.getEndExpression().asText();
        Element stepExpression = document.getStepExpression();

        if (stepExpression == null) {
            text += variable + " " + startExpression + " " + endExpression + " ";
        } else {
            text += variable + " " + startExpression + " " + endExpression + " " + stepExpression.asText() + " ";
        }
        return text + TAG_CLOSE;
    }

}
