package hr.fer.zemris.java.custom.scripting.lexer;


import hr.fer.zemris.java.hw03.prob1.LexerException;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class SmartScriptLexerTest {


    @Test
    public void testNotNull() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        assertNotNull("Token was expected but null was returned.", lexer.nextToken());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testNullInput() {
        // must throw!
        new SmartScriptLexer(null);
    }


    @Test
    public void testEmpty() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
    }


    @Test
    public void testGetReturnsLastNext() {
        // Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
        SmartScriptLexer lexer = new SmartScriptLexer("");

        Token token = lexer.nextToken();
        assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
        assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
    }


    @Test(expected = LexerException.class)
    public void testRadAfterEOF() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        // will obtain EOF
        lexer.nextToken();
        // will throw!
        lexer.nextToken();
    }

    @Test
    public void testBlankContent() {
        // When input is only of spaces, tabs, newlines, etc...
        SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");
        Token token = lexer.nextToken();
        checkToken(token, new Token(TokenType.TEXT, "   \r\n\t    "));
        //assertEquals("Input had no content. Lexer should generated only EOF token.", TokenType.TEXT, lexer.nextToken().getType());
    }


    @Test
    public void testTextWithBracketEscape() {
        // Lets check for several words...
        SmartScriptLexer lexer = new SmartScriptLexer("  Štefanija\r\n\t Automobil \\{  ");

        Token token = lexer.nextToken();

        checkToken(token, new Token(TokenType.TEXT, "  Štefanija\r\n\t Automobil {  "));
    }

    @Test
    public void testTextWithEscape() {
        // Lets check for several words...
        SmartScriptLexer lexer = new SmartScriptLexer("  Štefanija\r\n\t Automobil9 2 1. \\\\  ");

        Token token = lexer.nextToken();

        checkToken(token, new Token(TokenType.TEXT, "  Štefanija\r\n\t Automobil9 2 1. \\  "));
    }


    @Test
    public void testTextWithOpenTag() {
        // Lets check for several words...
        SmartScriptLexer lexer = new SmartScriptLexer("  Štefanija\r\n\t Automobil9 2 1. \\\\ \\{  {$");

        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "  Štefanija\r\n\t Automobil9 2 1. \\ {  "));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));


    }


    @Test
    public void testInputWithTextAndForTag() {
        // Lets check for several words...
        SmartScriptLexer lexer = new SmartScriptLexer("  Štefanija\r\n\t Automobil9 2 1. \\\\ \\{  \n{$  For i 1 0 10 $}");

        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "  Štefanija\r\n\t Automobil9 2 1. \\ {  \n"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "For"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 1));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 0));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 10));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSE, "$}"));
        lexer.setState(LexerState.BASIC);
        checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));

    }


    @Test
    public void testForTag() {
        // Lets check for several words...
        SmartScriptLexer lexer = new SmartScriptLexer("{$      FOR year +1 - -1 -1.53 last_year $}-darinka i darinko \\{");

        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "FOR"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "year"));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 1));
        checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, "-"));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, -1));
        checkToken(lexer.nextToken(), new Token(TokenType.DOUBLE, -1.53));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "last_year"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSE, "$}"));
        lexer.setState(LexerState.BASIC);
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "-darinka i darinko {"));
        checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));

    }

    @Test
    public void testForTagWithString() {
        // Lets check for several words...
        SmartScriptLexer lexer = new SmartScriptLexer("{$      FOR year \"10\"-1.5644 1 last_year \"mar\\\\k\\\"o\" $}-darinka i darinko \\{");

        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "FOR"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "year"));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING, "\"10\""));
        checkToken(lexer.nextToken(), new Token(TokenType.DOUBLE, -1.5644));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 1));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "last_year"));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING, "\"mar\\k\"o\""));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSE, "$}"));
        lexer.setState(LexerState.BASIC);
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "-darinka i darinko {"));
        checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
    }

    @Test(expected = LexerException.class)
    public void testWrongNestedTags() {
        SmartScriptLexer lexer = new SmartScriptLexer("{$ = {$ = i $} $}");
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "="));
        lexer.nextToken();
    }

    @Test(expected = LexerException.class)
    public void testForTagWithoutCloseTag() {
        SmartScriptLexer lexer = new SmartScriptLexer("{$ For i 1 10 1 } tekst je ovo ");
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "For"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 1));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 10));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 1));
        lexer.nextToken();
    }

    @Test
    public void emptyEchoTag() {
        SmartScriptLexer lexer = new SmartScriptLexer("{$=$}");
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "="));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSE, "$}"));
        lexer.setState(LexerState.BASIC);
        checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));

    }

    @Test
    public void testTextWithBracket() {
        SmartScriptLexer lexer = new SmartScriptLexer("xxxx { dokument {$ FOR i 1 2 1 $} xxxx tekst {$ END $}");
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "xxxx { dokument "));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "FOR"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 1));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 2));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 1));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSE, "$}"));
        lexer.setState(LexerState.BASIC);
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, " xxxx tekst "));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "END"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSE, "$}"));
        checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));


    }

    @Test
    public void testWholeInputDocument() {
        SmartScriptLexer lexer = new SmartScriptLexer("This is sample text.\r\n{$ FOR i 1 10 1 $}\r\n This is {$= i $}-th time " +
                "this message is generated.\r\n{$END$}\r\n{$FOR i 0 10 2 $}\r\n sin({$=i$}^2) = {$= i i * @sin \"0.00\\n 0\"123 @decfmt $}\r\n{$END$}");

        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "This is sample text.\r\n"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "FOR"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 1));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 10));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 1));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSE, "$}"));
        lexer.setState(LexerState.BASIC);
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\r\n This is "));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "="));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSE, "$}"));
        lexer.setState(LexerState.BASIC);
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "-th time this message is generated.\r\n"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "END"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSE, "$}"));
        lexer.setState(LexerState.BASIC);
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\r\n"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "FOR"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 0));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 10));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 2));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSE, "$}"));
        lexer.setState(LexerState.BASIC);
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\r\n sin("));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "="));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSE, "$}"));
        lexer.setState(LexerState.BASIC);
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "^2) = "));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "="));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, "*"));
        checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION, "@sin"));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING, "\"0.00\n 0\""));
        checkToken(lexer.nextToken(), new Token(TokenType.INT, 123));
        checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION, "@decfmt"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSE, "$}"));
        lexer.setState(LexerState.BASIC);
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\r\n"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPEN, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME, "END"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSE, "$}"));
        lexer.setState(LexerState.BASIC);
        checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
    }

    private void checkToken(Token actual, Token expected) {
        String msg = "Tokens are not equal.";
        assertEquals(msg, expected.getType(), actual.getType());
        assertEquals(msg, expected.getValue(), actual.getValue());
    }
}