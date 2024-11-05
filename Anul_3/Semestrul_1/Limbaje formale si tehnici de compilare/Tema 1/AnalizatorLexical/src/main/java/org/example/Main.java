package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private static final ArrayList<String> separators = new ArrayList<>(Arrays.asList(";", "(", ")", "{", "}", ","));
    private static final ArrayList<String> firstTable = new ArrayList<>();
    private static final ArrayList<ArrayList<String>> FIP = new ArrayList<>();
    private static final HashTable TS_ID = new HashTable(15);
    private static final HashTable TS_CONST = new HashTable(15);

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
            printFIP();
            printTS();
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
        int indexInTS = -1;
        if (firstTable.contains(atom)) {
            indexInFirstTable = firstTable.indexOf(atom);
        } else if (MyRegex.isKeyWord(atom) || MyRegex.isOperator(atom) || MyRegex.isSeparator(atom)) {
            firstTable.add(atom);
            indexInFirstTable = firstTable.indexOf(atom);
        } else if (MyRegex.isConstant(atom)) {
            Object constant;
            if (atom.contains("."))
                constant = Float.parseFloat(atom);
            else constant = Integer.parseInt(atom);
            TS_CONST.addEntry(constant);
            indexInFirstTable = firstTable.indexOf("CONST");
            indexInTS = TS_CONST.getEntry(constant);
            atom = "CONST : " + atom;
        } else if (MyRegex.isId(atom)) {
            TS_ID.addEntry(atom);
            indexInFirstTable = firstTable.indexOf("ID");
            indexInTS = TS_ID.getEntry(atom);
            atom = "ID : " + atom;
        } else {
            throw new RuntimeException("Something went wrong on line: " + lineNumber);
        }

        ArrayList<String> lineInFip = new ArrayList<>();
        lineInFip.add(String.valueOf(indexInFirstTable));
        lineInFip.add(atom);
        if (indexInTS != -1) lineInFip.add(String.valueOf(indexInTS));
        FIP.add(lineInFip);
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
     * Print FIP to console
     */
    private static void printFIP() {
        try {
            FileWriter fileWriter = new FileWriter("outputFIP.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Write header to the file
            printWriter.println("Cod atom \tPoz TS");

            // Write each line from FIP to the file
            for (ArrayList<String> line : FIP) {
                if (line.get(0).equals("0") || line.get(0).equals("1")) {
                    printWriter.println(line.get(0) + "\t\t\t" + line.get(2));
                } else {
                    printWriter.println(line.get(0));
                }
                printWriter.println("\t\t\t\t\t\t\t\t" + line.get(1));
            }

            // Close the writer
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print TS_ID and TS_CONST to console
     */
    private static void printTS() {
//        System.out.println("------- TS_ID -------");
        TS_ID.printTable("outputTS_ID.txt");
//        System.out.println("\n");
//        System.out.println("------- TS_CONST -------");
        TS_CONST.printTable("outputTS_CONST");
    }

}
