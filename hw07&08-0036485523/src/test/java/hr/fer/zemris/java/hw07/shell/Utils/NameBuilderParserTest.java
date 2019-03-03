package hr.fer.zemris.java.hw07.shell.Utils;

import org.junit.Test;

/**
 * Tested only for "defective" inputs due to lack of time...
 */
public class NameBuilderParserTest {

    private static NameBuilderParser parser;

    @Test(expected = ParserException.class)
    public void testSimpleInvalidExpression() {
        parser = new NameBuilderParser("gradovi-${2");
    }

    @Test(expected = ParserException.class)
    public void testCharactersInSubstitutionSubCommand() {
        parser = new NameBuilderParser("gradovi-${2,a}");
    }

    @Test(expected = ParserException.class)
    public void testEmptyExpression() {
        parser = new NameBuilderParser("");
    }

    @Test(expected = ParserException.class)
    public void testInvalidAdditionalClarificationSubCommand() {
        parser = new NameBuilderParser("gradovi${2, 01 2}");
    }

    @Test(expected = ParserException.class)
    public void testInvalidSequenceInSubCommand() {
        parser = new NameBuilderParser("gradovi-${$1}");
    }

    @Test(expected = ParserException.class)
    public void testComplicatedExpression(){
        parser = new NameBuilderParser("gradovi-${ 1  , 01 ${1}}");
    }

}
