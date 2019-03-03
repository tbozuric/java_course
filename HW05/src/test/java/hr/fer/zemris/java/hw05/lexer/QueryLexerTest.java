package hr.fer.zemris.java.hw05.lexer;

import hr.fer.zemris.java.hw05.db.lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.lexer.QueryLexerException;
import hr.fer.zemris.java.hw05.db.lexer.Token;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;
import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class QueryLexerTest {

    @Test
    public void testNotNull() {
        QueryLexer lexer = new QueryLexer("");
        assertNotNull("Token was expected but null was returned.", lexer.nextToken());
    }


    @Test(expected=IllegalArgumentException.class)
    public void testNullInput() {
        // must throw!
        new QueryLexer(null);
    }



    @Test
    public void testEmpty() {
        QueryLexer lexer = new QueryLexer("");
        assertEquals("Empty input must generate only END token.", TokenType.END, lexer.nextToken().getType());
    }


    @Test
    public void testGetReturnsLastNext() {
        // Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
        QueryLexer lexer = new QueryLexer("");

        Token token = lexer.nextToken();
        assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
        assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
    }


    @Test(expected=QueryLexerException.class)
    public void testRadAfterEOF() {
        QueryLexer lexer = new QueryLexer("");

        // will obtain EOF
        lexer.nextToken();
        // will throw!
        lexer.nextToken();
    }

    @Test
    public void testQueryWithSpaces(){
        QueryLexer lexer = new QueryLexer("jmbag      \t =\"0123456789\"     ");
        checkToken(lexer.nextToken() , new Token(TokenType.FIELD_NAME , "jmbag"));
        checkToken(lexer.nextToken() , new Token(TokenType.OPERATOR , "="));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING_LITERAL , "0123456789"));
        checkToken(lexer.nextToken(),new Token(TokenType.END , null));
    }

    @Test
    public void testQueryWithSpacesAndANDOperator(){
        QueryLexer lexer = new QueryLexer("jmbag      \t =\"0123456789\"     aNd lastName    >       \"J\"");
        checkToken(lexer.nextToken() , new Token(TokenType.FIELD_NAME , "jmbag"));
        checkToken(lexer.nextToken() , new Token(TokenType.OPERATOR , "="));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING_LITERAL , "0123456789"));
        checkToken(lexer.nextToken(), new Token(TokenType.LOGICAL_OPERATOR , "AND"));
        checkToken(lexer.nextToken() , new Token(TokenType.FIELD_NAME , "lastName"));
        checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR , ">"));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING_LITERAL , "J"));
        checkToken(lexer.nextToken(),new Token(TokenType.END , null));
    }


    @Test
    public void testQueryWithTabsAndLikeOperator(){
        QueryLexer lexer = new QueryLexer("jmbag      \t =\"0123456789\" \r\t\n\n    aNd lastName    >=  \t\t   \"J\" AND jmbag LIKE \"Bor*\"");
        checkToken(lexer.nextToken() , new Token(TokenType.FIELD_NAME , "jmbag"));
        checkToken(lexer.nextToken() , new Token(TokenType.OPERATOR , "="));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING_LITERAL , "0123456789"));
        checkToken(lexer.nextToken(), new Token(TokenType.LOGICAL_OPERATOR , "AND"));
        checkToken(lexer.nextToken() , new Token(TokenType.FIELD_NAME , "lastName"));
        checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR , ">="));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING_LITERAL , "J"));
        checkToken(lexer.nextToken(), new Token(TokenType.LOGICAL_OPERATOR , "AND"));
        checkToken(lexer.nextToken() , new Token(TokenType.FIELD_NAME , "jmbag"));
        checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR , "LIKE"));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING_LITERAL , "Bor*"));
        checkToken(lexer.nextToken(),new Token(TokenType.END , null));
    }


    @Test
    public void testQueryWithoutSpaces(){
        QueryLexer lexer = new QueryLexer("jmbag=\"0123456789\"aNdlastName>=\"J\"ANDjmbagLIKE\"Bor*\"");
        checkToken(lexer.nextToken() , new Token(TokenType.FIELD_NAME , "jmbag"));
        checkToken(lexer.nextToken() , new Token(TokenType.OPERATOR , "="));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING_LITERAL , "0123456789"));
        checkToken(lexer.nextToken(), new Token(TokenType.LOGICAL_OPERATOR , "AND"));
        checkToken(lexer.nextToken() , new Token(TokenType.FIELD_NAME , "lastName"));
        checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR , ">="));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING_LITERAL , "J"));
        checkToken(lexer.nextToken(), new Token(TokenType.LOGICAL_OPERATOR , "AND"));
        checkToken(lexer.nextToken() , new Token(TokenType.FIELD_NAME , "jmbag"));
        checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR , "LIKE"));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING_LITERAL , "Bor*"));
        checkToken(lexer.nextToken(),new Token(TokenType.END , null));
    }



    private void checkToken(Token actual, Token expected) {
        String msg = "Tokens are not equal.";
        TestCase.assertEquals(msg, expected.getType(), actual.getType());
        TestCase.assertEquals(msg, expected.getValue(), actual.getValue());
    }


}
