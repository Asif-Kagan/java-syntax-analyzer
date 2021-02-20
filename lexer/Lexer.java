package oop.ex6.lexer;

import oop.ex6.lexer.line.Line;
import oop.ex6.lexer.line.LineFactory;
import oop.ex6.lexer.line.LineTypes;
import oop.ex6.lexer.line.InvalidLineGrammarException;
import oop.ex6.lexer.token.Token;
import oop.ex6.lexer.token.TokenFactory;

import java.io.*;

/**
 * class defining a generator of SJAVA tokens for a given file
 */
public class Lexer implements AutoCloseable {

    /** text file reader */
    private final BufferedReader reader;
    /** current oop.ex6.lexer.line's oop.ex6.lexer.line number */
    private int lineNumber;

    /**
     *
     * @param path path to a sjava code file
     * @throws LexerIOException in case any IOException was thrown
     */
    public Lexer(String path) throws LexerIOException {
        try {
            this.reader = new BufferedReader(new FileReader(new File(path)));
            this.lineNumber = 0;
        }
        catch (IOException e) {
            throw new LexerIOException(e.getMessage());
        }
    }

    /**
     * gets the next oop.ex6.lexer.line of code
     * @return a Line object
     * @throws InvalidTokenException if an invalid oop.ex6.lexer.token was encountered
     * @throws LexerIOException if any IOException was thrown during the reading (or EOF)
     * @throws InvalidLineGrammarException if the line grammar is invalid
     */
    public Line next() throws InvalidTokenException, LexerIOException, InvalidLineGrammarException {
        Token[] tokens = this.nextTokens();
        if (tokens != null) {
            return LineFactory.getLine(tokens, this.lineNumber);
        }
        return null;
    }

    /**
     * autocloseable close implementation for try-with-resources
     * @throws LexerIOException in case of an IO error while closing the file
     */
    @Override
    public void close() throws LexerIOException {
        try {
            this.reader.close();
        }
        catch (IOException e) {
            throw new LexerIOException(e.getMessage());
        }
    }

    // ====================================================================
    // ---------------------------- Privates ------------------------------
    // ====================================================================

    /**
     * gets the next oop.ex6.lexer.line of code in it's tokens representation
     * @return an array of tokens which consist the current oop.ex6.lexer.line
     * @throws InvalidTokenException if an invalid oop.ex6.lexer.token was encountered
     * @throws LexerIOException if any IOException was thrown during the reading (or EOF)
     */
    private Token[] nextTokens() throws InvalidTokenException, LexerIOException {
        try {
            Token[] tokens = this.getTokens();
            while (tokens != null && tokens.length == 0) {  // skip "empty" lines (of only tokens which are
                tokens = this.getTokens();                  // listed as SKIP)
            }
            return tokens;
        }
        catch (IOException e) {
            throw new LexerIOException(e.getMessage());
        }
    }

    /**
     * private method to extract tokens for the currently read oop.ex6.lexer.line of the file
     * @return an array of tokens which consist the current oop.ex6.lexer.line
     * @throws IOException if any IOException was thrown during the reading
     * @throws InvalidTokenException if an invalid oop.ex6.lexer.token was encountered
     */
    private Token[] getTokens() throws IOException, InvalidTokenException {
        String line = this.reader.readLine();
        this.lineNumber += 1;
        if (line != null) {
            return TokenFactory.getTokens(line, this.lineNumber);
        }
        return null;
    }
}
