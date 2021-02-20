package oop.ex6.parser;

import oop.ex6.analysis.ast.Program;
import oop.ex6.exceptions.VerifierIOException;
import oop.ex6.lexer.InvalidTokenException;
import oop.ex6.lexer.OutOfLineBoundsException;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.lexer.Lexer;
import oop.ex6.lexer.LexerEOFException;
import oop.ex6.lexer.LexerIOException;
import oop.ex6.lexer.line.GrammarGroups;
import oop.ex6.lexer.line.Line;
import oop.ex6.lexer.line.LineTypes;
import oop.ex6.lexer.line.InvalidLineGrammarException;
import oop.ex6.lexer.token.Token;
import oop.ex6.lexer.token.TokenTypes;

/**
 * a oop.ex6.parser class to handle and be used for parsing
 */
public class Parser implements AutoCloseable {

    /** oop.ex6.lexer used to extract tokens and lines */
    private final Lexer lexer;
    private Line peek;
    private Line line;
    private Token[] tokens;
    private int lineIndex;

    /**
     * construct a new oop.ex6.parser for the given sjava file
     * @param path path to a sjava file
     * @throws LexerIOException in case oop.ex6.lexer threw an IO exception opening the file
     */
    public Parser(String path) throws LexerIOException {
        this.lexer = new Lexer(path);
        this.peek = null;
    }

    /**
     * parse the source file into a Program object
     * @return Program object representing the sjava code
     * @throws VerifierException in case parsing failed (could be IO or grammatical errors in the code)
     * @throws VerifierIOException in case an IO exception is thrown
     */
    public Program parse() throws VerifierException, VerifierIOException {
        Program program = new Program();

        while (this.nextLine() != null) {
            this.use(this.line.getParseStrategy()).parse(program);
        }

        return program;
    }

    /**
     * autocloseable close implementation for try-with-resources
     * @throws LexerIOException in case of an IO error while closing the file
     */
    @Override
    public void close() throws LexerIOException {
        this.lexer.close();
    }

    // ====================================================================
    // ----------------------- Package Privates ---------------------------
    // =============== providing all the oop.ex6.parser functionality =============
    // ====================================================================

    /**
     * use the provided parsing strategy to continue parsing
     * @param parseStrategy parsing strategy to use
     * @throws VerifierException if invalid strategy passed
     */
    ParseStrategy use(ParseStrategy parseStrategy) throws VerifierException {
        if (parseStrategy == null) {
            throw new InvalidLineGrammarException(this.line.getLineNumber());
        }
        parseStrategy.setParser(this);
        return parseStrategy;
    }

    // ----------- entire lines ------------

    /**
     * fetch the next line
     * @return the next lime object
     * @throws LexerIOException upon any IO exception
     * @throws InvalidTokenException upon any invalid tokens
     * @throws InvalidLineGrammarException upon an invalid line grammar
     */
    Line nextLine() throws LexerIOException, InvalidTokenException, InvalidLineGrammarException {
        this.lineIndex = 0;

        if (this.peek != null) {
            this.line = this.peek;
            this.tokens = this.line.getTokens();
            this.peek = null;
        }
        else if ((this.line = this.lexer.next()) != null) {
            this.tokens = this.line.getTokens();
        }

        return this.line;
    }

    /**
     * peek next line without moving to it
     * @return the next line object
     * @throws LexerIOException upon any IO exception
     * @throws InvalidTokenException upon any invalid tokens
     * @throws InvalidLineGrammarException upon an invalid line grammar
     */
    Line peekNextLine() throws LexerIOException, InvalidTokenException, InvalidLineGrammarException {
        if (this.peek != null ||
                (this.peek = this.lexer.next()) != null) {
            return this.peek;
        }
        throw new LexerEOFException();
    }

    /**
     * peek next line without moving to it and validate it's  oop.ex6.lexer.line type
     * @param lineTypes the expected line type
     * @return true if as expected false otherwise
     * @throws LexerIOException upon any IO exception
     * @throws InvalidTokenException upon any invalid tokens
     * @throws InvalidLineGrammarException upon an invalid line grammar
     */
    boolean peekNextLine(LineTypes lineTypes)
            throws LexerIOException, InvalidTokenException, InvalidLineGrammarException {
        return this.peekNextLine().getLineType() == lineTypes;
    }

    // ----------- oop.ex6.lexer.token-by-oop.ex6.lexer.token ------------

    /**
     * peek current oop.ex6.lexer.token and check for its type
     * @param type the required type
     * @return true if current oop.ex6.lexer.token fits the required type
     */
    boolean peek(TokenTypes type) {
        Token token = this.tokens[this.lineIndex];
        return token.getType() == type;
    }

    /**
     * peek offset tokens ahead and check for its type
     * @param type the required type
     * @return rue if oop.ex6.lexer.token fits the required type
     */
    boolean peek(int offset, TokenTypes type) {
        Token token = this.tokens[this.lineIndex+offset];
        return token.getType() == type;
    }

    /**
     * peek the next oop.ex6.lexer.token and check for its type
     * @param type the required type
     * @return true if oop.ex6.lexer.token fits the required type
     */
    boolean peekNext(TokenTypes type) {
        return this.peek(1, type);
    }

    /**
     * peek current oop.ex6.lexer.token and check for its type against any number of grammar groups
     * @param grammarTypes the required grammar groups
     * @return true if current oop.ex6.lexer.token is part of the required grammar groups
     */
    boolean peek(GrammarGroups... grammarTypes) {
        Token token = this.tokens[this.lineIndex];
        for (GrammarGroups grammarType : grammarTypes) {
            if (grammarType.getOptions().contains(token.getType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * consume the current oop.ex6.lexer.token and check for its type against a grammar group
     * @param grammarType the required grammar group
     * @return the oop.ex6.lexer.token's value if as expected
     * @throws InvalidTokenException if the token didn't match the required group
     * @throws OutOfLineBoundsException if the end of the line was reached
     */
    String consume(GrammarGroups grammarType) throws InvalidTokenException, OutOfLineBoundsException {
        if (this.tokens.length <= this.lineIndex ) {
            throw new OutOfLineBoundsException();
        }
        Token token = this.tokens[this.lineIndex++];
        for (TokenTypes type : grammarType.getOptions()) {
            if (token.getType() == type) {
                return token.getValue();
            }
        }
        throw new InvalidTokenException(token);
    }

    /**
     * consume the current oop.ex6.lexer.token and check for its type against a oop.ex6.lexer.token type
     * @param type the required oop.ex6.lexer.token type
     * @return the oop.ex6.lexer.token's value if as expected
     * @throws InvalidTokenException if the token didn't match the required type
     * @throws OutOfLineBoundsException if the end of the line was reached
     */
    String consume(TokenTypes type) throws InvalidTokenException, OutOfLineBoundsException {
        if (this.tokens.length <= this.lineIndex ) {
            throw new OutOfLineBoundsException();
        }
        Token token = this.tokens[this.lineIndex++];
        if (token.getType() != type) {
            throw new InvalidTokenException(token);
        }
        return token.getValue();
    }

    /**
     * go back one oop.ex6.lexer.token within the same line
     * @throws OutOfLineBoundsException if the reverse cannot be preformed
     */
    void back() throws OutOfLineBoundsException {
        this.lineIndex--;
        if (this.lineIndex < 0) {
            throw new OutOfLineBoundsException();
        }
    }

    /**
     * get the next oop.ex6.lexer.token
     * @param grammarType a grammar group the oop.ex6.lexer.token must be a part of
     * @return the next oop.ex6.lexer.token
     * @throws InvalidTokenException if the token didn't match expected grammar group
     * @throws OutOfLineBoundsException if the end of the line was reached
     */
    Token nextToken(GrammarGroups grammarType) throws InvalidTokenException, OutOfLineBoundsException {
        if (this.tokens.length <= this.lineIndex ) {
            throw new OutOfLineBoundsException();
        }
        Token token = this.tokens[this.lineIndex++];
        for (TokenTypes type : grammarType.getOptions()) {
            if (token.getType() == type) {
                return token;
            }
        }
        throw new InvalidTokenException(token);
    }

    /**
     * get the next oop.ex6.lexer.token
     * @return the next oop.ex6.lexer.token
     * @throws OutOfLineBoundsException if the end of the line was reached
     */
    Token nextToken() throws OutOfLineBoundsException {
        if (this.tokens.length <= this.lineIndex ) {
            throw new OutOfLineBoundsException();
        }
        return this.tokens[this.lineIndex++];
    }

    /**
     * consume the next oop.ex6.lexer.token iff it matches the expected grammar group
     * @param grammarType a grammar group the oop.ex6.lexer.token must be a part of
     * @return true if consumed, false otherwise
     * @throws OutOfLineBoundsException if the end of the line was reached
     */
    boolean consumeIf(GrammarGroups grammarType) throws OutOfLineBoundsException {
        if (this.tokens.length <= this.lineIndex ) {
            throw new OutOfLineBoundsException();
        }
        Token token = this.tokens[this.lineIndex];
        for (TokenTypes type : grammarType.getOptions()) {
            if (token.getType() == type) {
                this.lineIndex++;
                return true;
            }
        }
        return false;
    }

    /**
     * consume the next oop.ex6.lexer.token iff it matches the expected oop.ex6.lexer.token type
     * @param expected a oop.ex6.lexer.token type the oop.ex6.lexer.token must have
     * @return true if consumed, false otherwise
     * @throws OutOfLineBoundsException if the end of the line was reached
     */
    boolean consumeIf(TokenTypes expected) throws OutOfLineBoundsException {
        if (this.tokens.length <= this.lineIndex ) {
            throw new OutOfLineBoundsException();
        }
        Token token = this.tokens[this.lineIndex];
        if  (token.getType() == expected) {
            this.lineIndex++;
            return true;
        }
        return false;
    }
}
