package org.example;

import java.util.List;
import java.util.Arrays;

public class LexicalAnalyzer {

    // Define separators, operators, and keywords as lists
    private static final List<String> SEPARATORS = Arrays.asList(";", "(", ")", "{", "}", ",","[", "]");
    private static final List<String> OPERATORS = Arrays.asList("!=", "==", ">>", "<<", ">", "<", "=", "-", "*", "+", "%");
    private static final List<String> KEYWORDS = Arrays.asList(
            "#include",
            "<iostream>",
            "using",
            "namespace",
            "std",
            "int",
            "main",
            "cin",
            "while",
            "cout",
            "float",
            "if",
            "else",
            "typedef",
            "struct"
    );

    // Load finite state machines (FSMs) from file via Utils class
    private static final FiniteStateMachine idFSM = Utils.readFromFile(Utils.ID_AF);
    private static final FiniteStateMachine constIntFSM = Utils.readFromFile(Utils.CONST_INT_AF);
    private static final FiniteStateMachine constFloatFSM = Utils.readFromFile(Utils.CONST_FLOAT_AF);

    /**
     * Checks if a string is an operator.
     */
    public static boolean isOperator(String str) {
        return OPERATORS.contains(str);
    }

    /**
     * Checks if a string is a separator.
     */
    public static boolean isSeparator(String str) {
        return SEPARATORS.contains(str);
    }

    /**
     * Checks if a string is a keyword.
     */
    public static boolean isKeyword(String str) {
        return KEYWORDS.contains(str);
    }

    /**
     * Checks if a string is a constant integer.
     */
    public static boolean isConstInt(String str) {
        return constIntFSM.isAccepted(str);
    }

    /**
     * Checks if a string is a constant float.
     */
    public static boolean isConstFloat(String str) {
        return constFloatFSM.isAccepted(str);
    }

    /**
     * Checks if a string is an identifier.
     */
    public static boolean isId(String str) {
        return idFSM.isAccepted(str);
    }
}
