package org.example;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class MatrixGenerator {
    /**
     * Generates a matrix with given number of rows and columns and writes it to file
     * @param N number of rows
     * @param M number of columns
     * @param matrixName type of matrix to generate
     * @throws IOException
     */
    static void generate(int N, int M, String matrixName) throws IOException {
        Random random = new Random();
        int bound;
        String fileName;

        if(Objects.equals(matrixName, "image")) {
            bound = 5;
            fileName = Utils.matrixFileName;
        }
        else{
            bound = 3;
            fileName = Utils.kernelFileName;
        }
        int[][] mat = new int[N][M];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                mat[i][j] = random.nextInt(bound);
            }
        }

        Utils.writeMatrix(mat, fileName);
    }
}
