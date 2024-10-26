package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class HashTable {
    private int size;
    private Object[] table;

    public HashTable(int size) {
        this.size = size;
        this.table = new Object[size];
    }

    /**
     * Hash function for strings (first character's ASCII value mod size) or numbers (value mod size)
     * @param key -
     * @return the hash value
     */
    private int hash(Object key) {
        if (key instanceof String) {
            return ((String) key).charAt(0) % size;
        } else if (key instanceof Integer || key instanceof Float) {
            return Math.abs(key.hashCode()) % size;
        } else {
            throw new IllegalArgumentException("Invalid key type");
        }
    }

    /**
     * Add a key to the hash table, handling collisions with linear probing
     * @param key - the key we need to add
     */
    public void addEntry(Object key) {
        if (getEntry(key) != null) return;

        int index = hash(key);
        while (table[index] != null) {  // Linear probing
            index = (index + 1) % size;
        }
        table[index] = key;  // Store only the key
    }

    /**
     * Retrieve the index of a key in the hash table
     * @param key - the key we are searching for
     * @return - the key's index in the hashtable
     */
    public Integer getEntry(Object key) {
        int index = hash(key);
        int initialIndex = index;
        while (table[index] != null) {
            if (table[index].equals(key)) {
                return index;
            }
            index = (index + 1) % size;
            if (index == initialIndex) {
                return null;  // Key not found
            }
        }
        return null;  // Key not found
    }

    /**
     * Print the hash table
     */
    public void printTable(String filename) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (int i = 0; i < size; i++) {
//                if (table[i] != null) {
                    printWriter.println(i + "\t\t" + table[i]);
//                }
            }

            // Close the writer
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
