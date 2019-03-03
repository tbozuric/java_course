package hr.fer.zemris.java.custom.scripting.lexer;


import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SmartScriptParserTest {


    private static String document;
    private static SmartScriptParser parser;

    @BeforeClass
    public static void initialization() {
        try {
            document = new String(Files.readAllBytes(Paths.get("examples/doc1.txt")), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Error loading file!");
            System.exit(-1);
        }
        parser = new SmartScriptParser(document);
    }


    @Test
    public void numberOfChildren() {
        Assert.assertEquals(4, parser.getDocumentNode().numberOfChildren());
    }

    @Test
    public void firstLevelOfNodes() {
        Node node = parser.getDocumentNode();
        Assert.assertTrue(node.getChild(0) instanceof TextNode);
        Assert.assertTrue(node.getChild(1) instanceof ForLoopNode);
        Assert.assertTrue(node.getChild(2) instanceof TextNode);
        Assert.assertTrue(node.getChild(3) instanceof ForLoopNode);
    }

    @Test
    public void firstForLoopNode() {
        Node node = parser.getDocumentNode().getChild(1);
        Assert.assertEquals(3, node.numberOfChildren());
        Assert.assertTrue(node.getChild(0) instanceof TextNode);
        Assert.assertTrue(node.getChild(1) instanceof EchoNode);
        Assert.assertTrue(node.getChild(2) instanceof TextNode);

    }


    @Test
    public void testEchoNode(){
        Node node = parser.getDocumentNode().getChild(0);
        Assert.assertTrue(((TextNode)node).getText().equals("This is sample text.\n"));
    }

}
