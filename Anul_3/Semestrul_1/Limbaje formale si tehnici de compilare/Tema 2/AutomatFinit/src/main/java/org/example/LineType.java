package org.example;

/**
 * Enum for defining type for a line from a file
 */
public enum LineType {
    Q, SIGMA, S, F, TRANSITION;

    /**
     * Converts a string to a LineType
     */
    public static LineType fromString(String str) {
        return switch (str) {
            case "Q" -> Q;
            case "SIGMA" -> SIGMA;
            case "S" -> S;
            case "F" -> F;
            default -> TRANSITION;
        };
    }
}

