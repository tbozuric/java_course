package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class represents a tree writer. The program  opens a vila given via the argument,
 * parses it into a tree and then reproduce its
 * (approximation) original form onto standard output.
 */
public class TreeWriter {

    /**
     * Close tag in document.
     */
    private static final String TAG_CLOSE = "$}";

    /**
     * Open tag in document.
     */
    private static final String TAG_OPEN = "{$";

    /**
     * For tag in document.
     */
    private static final String TAG_FOR = "{$ FOR ";

    /**
     * Method invoked when running the program.
     *
     * @param args command-line arguments, the method expects one parameter, the path to the file we want to parse.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please enter the path of the file as an argument.");
            return;
        }

        SmartScriptParser parser = null;
        try {
            String docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
            parser = new SmartScriptParser(docBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(-1);
        } catch (IOException ex) {
            System.out.println("Unable to read file.");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class or path is not correct!");
            System.exit(-1);
        }
        WriterVisitor visitor = new WriterVisitor();
        DocumentNode document = parser.getDocumentNode();
        document.accept(visitor);
    }

    /**
     * Represents a visitor  who visits the generated tree of the parsed input text and prints it to a standard output.
     */
    public static class WriterVisitor implements INodeVisitor {


        @Override
        public void visitTextNode(TextNode node) {
            String textNodeValue = node.getText();
            textNodeValue = textNodeValue.replaceAll("(\\\\)", "\\\\\\\\");
            textNodeValue = textNodeValue.replaceAll("\\{", "\\\\{");
            System.out.print(textNodeValue + " ");
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            System.out.print(createForLoopBody(node));
            for (int i = 0; i < node.numberOfChildren(); i++) {
                node.getChild(i).accept(this);
            }
            System.out.print("{$END$}");
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            Element[] elements = node.getElements();
            StringBuilder text = new StringBuilder();
            text.append(TAG_OPEN + "= ");
            for (Element element : elements) {
                text.append(element.asText()).append(" ");
            }
            System.out.print(text.toString() + TAG_CLOSE);
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            for (int i = 0; i < node.numberOfChildren(); i++) {
                node.getChild(i).accept(this);
            }
        }

        /**
         * Generates  the body of {@link ForLoopNode}
         *
         * @param forLoopNode the for loop node.
         * @return the body of for loop node.
         */
        private static String createForLoopBody(ForLoopNode forLoopNode) {
            String text = "";
            text += TAG_FOR;
            String variable = forLoopNode.getVariable().asText();
            String startExpression = forLoopNode.getStartExpression().asText();
            String endExpression = forLoopNode.getEndExpression().asText();
            Element stepExpression = forLoopNode.getStepExpression();

            if (stepExpression == null) {
                text += variable + " " + startExpression + " " + endExpression + " ";
            } else {
                text += variable + " " + startExpression + " " + endExpression + " " + stepExpression.asText() + " ";
            }
            return text + TAG_CLOSE;
        }
    }
}
