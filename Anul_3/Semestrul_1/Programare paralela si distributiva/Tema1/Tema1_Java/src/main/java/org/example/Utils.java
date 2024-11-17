package org.example;

import java.io.*;
import java.util.Scanner;

public class Utils {
    public static final String matrixFileName = "C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela si distributiva\\Tema1\\Tema1_Java\\data.txt";
    public static final String kernelFileName = "C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela si distributiva\\Tema1\\Tema1_Java\\kernel.txt";

    /**
     * Write a matrix to file
     * @param mat matrix to be written
     * @param fileName name of the file where the matrix will be written
     * @throws IOException
     */
    public static void writeMatrix(int[][] mat, String fileName) throws IOException {
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write("");
            fw.append(String.valueOf(mat.length)).append(" ").append(String.valueOf(mat[0].length)).append("\n");

            for (int[] line : mat) {
                for (int elem : line) {
                    fw.append(String.valueOf(elem)).append(" ");
                }
                fw.append("\n");
            }
        }
    }

    /**
     * Reads a matrix from file
     * @param fileName name of the file containing the matrix
     * @return the matrix read from file
     * @throws IOException
     */
    public static int[][] readMatrix(String fileName) throws IOException {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            int N = scanner.nextInt();
            int M = scanner.nextInt();
            int[][] mat = new int[N][M];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    mat[i][j] = scanner.nextInt();
                }
            }
            return mat;
        }
    }

    /**
     * Writes a string to file
     * @param text the string to be written
     * @param fileName name of the file
     * @throws IOException
     */
    public static void writeString(String text, String fileName) throws IOException {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(text);
        }
    }
}
