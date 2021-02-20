package oop.ex6.main;

import oop.ex6.analysis.ast.Analyzer;
import oop.ex6.analysis.ast.Program;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.exceptions.VerifierIOException;
import oop.ex6.parser.Parser;

/**
 * runner for the sjava verifier
 */
public class Sjavac {
    /** improper usage message */
    private static final String USAGE_MESSAGE = "Usage: oop.ex6.main.sjavac *.sjava";

    /** index of the src file path in args */
    private static final int SRC_FILE = 0;

    /** flag for a valid sjava program */
    private static final int OK = 0;
    /** flag for any IO exception */
    private static final int IO_EXCEPTION = 2;
    /** flag for a invalid sjava program */
    private static final int INVALID_EXCEPTION = 1;

    /**
     * run the sjava verifier on the passed file
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println(USAGE_MESSAGE);
            return;
        }

        final String SRC = args[SRC_FILE];

        try(Parser parser = new Parser(SRC)) {
            Program program = parser.parse();

            Analyzer analyzer = new Analyzer();
            analyzer.analyze(program);

            System.out.println(OK);
        }
        catch (VerifierIOException e) {
            System.out.println(IO_EXCEPTION);
            System.err.println(e.getMessage());
        }
        catch (VerifierException e) {
            System.out.println(INVALID_EXCEPTION);
            System.err.println(e.getMessage());
        }
    }
}
