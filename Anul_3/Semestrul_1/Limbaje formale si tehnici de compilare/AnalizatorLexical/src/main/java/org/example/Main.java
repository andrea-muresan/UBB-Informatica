package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private static final ArrayList<String> separators = new ArrayList<>(Arrays.asList(";", "(", ")", "{", "}", ","));
    private static final ArrayList<String> firstTable = new ArrayList<>();
    private static final ArrayList<ArrayList<String>> secondTable = new ArrayList<>();
    /**
     * Entry point of the program
     */
    public static void main(String[] args) {
        firstTable.add("ID");
        firstTable.add("CONST");

        try {
            processFile();
        } catch (Exception e) {
            System.out.println(ANSI_YELLOW
                    + e.getLocalizedMessage()
                    + ANSI_RESET);
        } finally {
            printFirstTable();
            printSecondTable();
        }
    }


    /**
     * Reads from file line by line and processes word by word for lexical analysis
     * @throws Exception if file is not found or if an atom is invalid
     */
    private static void processFile() throws Exception {
        int lineNumber = 0;
        try (Scanner in = new Scanner(new File("src/input.txt"))) {
            while (in.hasNext()) {
                lineNumber++;
                String line = in.nextLine();
                String[] words = line.split(" ");

                int finalLineNumber = lineNumber;
                Arrays.stream(words).forEach(word -> {
                    try {
                        processWord(word, finalLineNumber);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Splits a word in prefix, word and suffix and processes it
     * @param word the word to be split
     * @param lineNumber line number of the word
     * @throws Exception if a part of the word is invalid
     */
    private static void processWord(String word, int lineNumber) throws Exception {
        String prefix = null, suffix = null;

        for (String separator : separators) {
            // verify if atom starts with a separator
            if (word.startsWith(separator) && prefix == null) {
                word = word.replace(separator, "");
                prefix = separator;
            }
            // verify if atom ends with a separator
            if (word.endsWith(separator) && suffix == null) {
                word = word.replace(separator, "");
                suffix = separator;
            }
        }

        if (prefix != null) {
            processAtom(prefix, lineNumber);
        }

        word = word.trim();
        if (!word.isEmpty()) {
            processAtom(word, lineNumber);
        }

        if (suffix != null) {
            processAtom(suffix, lineNumber);
        }
    }

    /**
     * Verifies if a string is keyword, separator, operator, id or constant
     * @param atom the string to be verified
     * @param lineNumber the line that contains the atom
     * @throws Exception if the atom in not in a category
     */
    private static void processAtom(String atom, int lineNumber) throws Exception {
        int indexInFirstTable;

        // First Table
        if (firstTable.contains(atom)) {
            indexInFirstTable = firstTable.indexOf(atom);
        } else if (MyRegex.isKeyWord(atom) || MyRegex.isOperator(atom) || MyRegex.isSeparator(atom)) {
            firstTable.add(atom);
            indexInFirstTable = firstTable.indexOf(atom);
        } else if (MyRegex.isConstant(atom)) {
            indexInFirstTable = firstTable.indexOf("CONST");
        } else if (MyRegex.isId(atom)) {
            indexInFirstTable = firstTable.indexOf("ID");
        } else {
            throw new Exception("Something went wrong on line: " + lineNumber + "\n Atom: << " + atom + " >> incorrect");
        }
        // add the atom to the Second Table
        ArrayList<String> lineInSecondTable = new ArrayList<>();
        lineInSecondTable.add(String.valueOf(indexInFirstTable));
        lineInSecondTable.add(atom);

        // Second Table
        if (MyRegex.isKeyWord(atom)) {
            lineInSecondTable.add("keyword");
        } else if (MyRegex.isOperator(atom)){
            lineInSecondTable.add("operator");
        } else if (MyRegex.isSeparator(atom)){
            lineInSecondTable.add("separator");
        } else if (MyRegex.isConstant(atom)){
            lineInSecondTable.add("CONST");
        } else if (MyRegex.isId(atom)){
            lineInSecondTable.add("ID");
        }

        secondTable.add(lineInSecondTable);
    }

    /**
     * Print the first table
     */
    private static void printFirstTable() {
        System.out.println("\n");
        System.out.println("******* First Table *******");
        System.out.println("Cod \tAtom lexical");
        for (int i = 0; i < firstTable.size(); i++) {
            System.out.println(i + "\t\t" + firstTable.get(i));
        }
        System.out.println("\n\n\n");
    }

    /**
     * Print Second Table
     */
    private static void printSecondTable() {
        System.out.println("******* Second Table *******");
        System.out.println("Cod atom \tAtom ");
        for (ArrayList<String> line : secondTable) {
            System.out.println(line.get(0) + "\t" + line.get(1) + "  -  " + line.get(2));
        }
        System.out.println("\n\n\n");
    }

}
