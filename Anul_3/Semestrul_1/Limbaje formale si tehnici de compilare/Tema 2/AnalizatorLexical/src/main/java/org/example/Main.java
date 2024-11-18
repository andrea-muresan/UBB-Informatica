package org.example;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
    private static final List<String> firstTable = new ArrayList<>();
    private static final List<ArrayList<String>> FIP = new ArrayList<>();
    private static final HashTable TS_ID = new HashTable(20);
    private static final HashTable TS_CONST = new HashTable(20);
    private static final List<String> separators = Arrays.asList("[",";","(",")","{","}",",","]","!=",">>","<<",">","<","==","*","+","%","=");

    /**
     * Entry point of the program
     */
    public static void main(String[] args) {
        firstTable.add("ID");
        firstTable.add("CONST");
        String filename = "src/input.txt";

        try {
            processFile(filename);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            printFirstTable();
            printFIP();
            printTS();
        }
    }

    /**
     * Reads from file line by line and processes word by word for lexical analysis
     * @throws RuntimeException if file is not found or if an atom is invalid
     */
    private static void processFile(String filename) {
        int lineNumber = 0;
        try (Scanner in = new Scanner(new File("src/input.txt"))) {
            while (in.hasNext()) {
                lineNumber++;
                String line = in.nextLine();
                    String[] words = line.split(" ");

                    int finalLineNumber = lineNumber;
                    Arrays.stream(words).forEach(word -> {
                        try {
                            if (!word.isEmpty()) {
                                processWord(word, finalLineNumber);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + filename, e);
        }
    }

    /**
     * Splits a word in prefix, word and suffix and processes it
     * @param word the word to be split
     * @param lineNumber line number of the word
     * @throws RuntimeException if a part of the word is invalid
     */
    private static void processWord(String word, int lineNumber) {
        List<String> parts = new ArrayList<>();
        parts.add(word);

        if (word.equals("<iostream>")) {
            processAtom(word, lineNumber);
        } else if (word.equals("#include<iostream>")) {
            processAtom("#include", lineNumber);
            processAtom("<iostream>", lineNumber);
        } else {
            // Split the word by each separator, if present
            for (String separator : separators) {
                List<String> newParts = new ArrayList<>();
                for (String part : parts) {
                    if (part.contains(separator) && !LexicalAnalyzer.isSeparator(part) && !LexicalAnalyzer.isOperator(part)) {
                        // Split by the separator and retain it as a token
                        String[] splitParts = part.split(Pattern.quote(separator), -1);
                        for (int i = 0; i < splitParts.length; i++) {
                            if (!splitParts[i].isEmpty()) {
                                newParts.add(splitParts[i]);
                            }
                            if (i < splitParts.length - 1) {
                                newParts.add(separator);
                            }
                        }
                    } else {
                        newParts.add(part);
                    }
                }
                parts = newParts;
            }

            // Process each part separately
            for (String part : parts) {
                part = part.trim(); // Clean up any leading or trailing spaces
                if (!part.isEmpty()) {
                    processAtom(part, lineNumber); // Process each part, including separators
                }
            }
        }
    }

    /**
     * Verifies if a string is keyword, separator, operator, id or constant
     * @param atom the string to be verified
     * @param lineNumber the line that contains the atom
     * @throws RuntimeException if the atom in not in a category
     */
    private static void processAtom(String atom, int lineNumber) {
        int indexInFirstTable;
        int indexInTS = -1;

        if (firstTable.contains(atom)) {
            indexInFirstTable = firstTable.indexOf(atom);
        } else if (LexicalAnalyzer.isKeyword(atom) || LexicalAnalyzer.isOperator(atom) || LexicalAnalyzer.isSeparator(atom)) {
            firstTable.add(atom);
            indexInFirstTable = firstTable.indexOf(atom);
        } else if (LexicalAnalyzer.isConstInt(atom) || LexicalAnalyzer.isConstFloat(atom)) {
            TS_CONST.addEntry(atom);
            indexInFirstTable = firstTable.indexOf("CONST");
            indexInTS = TS_CONST.getEntry(atom);
        } else if (LexicalAnalyzer.isId(atom)) {
            TS_ID.addEntry(atom);
            indexInFirstTable = firstTable.indexOf("ID");
            indexInTS = TS_ID.getEntry(atom);
        } else {
            throw new RuntimeException("Invalid atom on line: " + lineNumber);
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
        System.out.println("\n------- First Table -------");
        System.out.println("Cod \tAtom lexical");
        for (int i = 0; i < firstTable.size(); i++) {
            System.out.println(i + "\t\t" + firstTable.get(i));
        }
        System.out.println();
    }

    /**
     * Print FIP
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
        TS_ID.printTable("outputTS_ID.txt");
        TS_CONST.printTable("outputTS_CONST.txt");
    }
}
