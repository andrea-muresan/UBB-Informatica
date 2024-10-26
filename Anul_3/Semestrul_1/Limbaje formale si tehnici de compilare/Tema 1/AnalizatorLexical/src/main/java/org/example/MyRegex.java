package org.example;

import java.util.regex.Pattern;

public class MyRegex {
    private static Pattern pattern;
    private static final String idRegex = "^[a-zA-Z]([a-zA-Z0-9])*$";
    private static final String keywordRegex = "^(#include|<iostream>|using|namespace|std|int|float|main|cin|cout|while|if|typedef|struct)$";
    private static final String constantRegex = "^(0|[1-9]\\d*$|0\\.\\d*[1-9]|[1-9]\\d*\\.\\d*[1-9])$";
    private static final String operatorRegex = "^(!=|>>|<<|>|<|=|-|\\*|\\+|%|==)$";
    private static final String separatorRegex = "^([;(){},])$";

    /**
     * Check if a string is id
     * @param atom - string to be checked
     * @return  true - the string is id
     *          false - otherwise
     */
    static boolean isId(String atom) {
        pattern = Pattern.compile(idRegex);
        return pattern.matcher(atom).find();
    }

    /**
     * Check if a string is keyword
     * @param atom - string to be checked
     * @return  true -  the string is keyword
     *          false - otherwise
     */
    static boolean isKeyWord(String atom) {
        pattern = Pattern.compile(keywordRegex);
        return pattern.matcher(atom).find();
    }

    /**
     * Check if a string is constant
     * @param atom - string to be checked
     * @return  true - the string is constant
     *          false - otherwise
     */
    static boolean isConstant(String atom) {
        pattern = Pattern.compile(constantRegex);
        return pattern.matcher(atom).find();
    }

    /**
     * Check if a string is operator
     * @param atom - string to be checked
     * @return  true - the string is operator
     *          false - otherwise
     */
    static boolean isOperator(String atom) {
        pattern = Pattern.compile(operatorRegex);
        return pattern.matcher(atom).find();
    }

    /**
     * Check if a string is separator
     * @param atom - string to be checked
     * @return  true - the string is separator
     *          false - otherwise
     */
    static boolean isSeparator(String atom) {
        pattern = Pattern.compile(separatorRegex);
        return pattern.matcher(atom).find();
    }
}
