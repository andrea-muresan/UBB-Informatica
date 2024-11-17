package org.example;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
//        generate();

        int[][] image = Utils.readMatrix(Utils.matrixFileName);
        int[][] kernel = Utils.readMatrix(Utils.kernelFileName);
        int P = 4;
//        int P = Integer.parseInt(args[0]);

        long startTime = System.nanoTime();
        if (P == 1) {
            sequential(image, kernel);
        } else {
//            parallelLines(image, kernel, P);
//            parallelColumns(image, kernel, P);
        parallelBlocks(image, kernel, P);
//        parallelLinearDistribution(image, kernel, P);
//        parallelCyclicDistribution(image, kernel, P);
        }
        long endTime = System.nanoTime();

        System.out.println((double)(endTime - startTime)/1E6);//ms
    }

    private static void parallelCyclicDistribution(int[][] image, int[][] kernel, int P) throws IOException {
        Convolution conv = new LinearDistributionConvolution(image, kernel, P);
        int[][] answer = conv.apply();

        verify(answer);
    }

    private static void generate() throws IOException {
        MatrixGenerator.generate(1000, 1000, "image");
        MatrixGenerator.generate(5, 5, "filter");

        int[][] image = Utils.readMatrix(Utils.matrixFileName);
        int[][] kernel = Utils.readMatrix(Utils.kernelFileName);

        Convolution conv = new SequentialConvolution(image, kernel);
        int[][] answer = conv.apply();

        Utils.writeMatrix(answer, "result.txt");
    }

    private static void sequential(int[][] image, int[][] kernel) throws IOException {
        Convolution conv = new SequentialConvolution(image, kernel);
        int[][] answer = conv.apply();

        verify(answer);
    }

    private static void parallelBlocks(int[][] image, int[][] kernel, int P) throws IOException {
        Convolution conv = new BlocksConvolution(image, kernel, P);
        int[][] answer = conv.apply();

        verify(answer);
    }

    private static void parallelLines(int[][] image, int[][] kernel, int P) throws IOException {
        Convolution conv = new LinesBatchConvolution(image, kernel, P);
        int[][] answer = conv.apply();

        verify(answer);
    }

    private static void parallelColumns(int[][] image, int[][] kernel, int P) throws IOException {
        Convolution conv = new ColumnsBatchConvolution(image, kernel, P);
        int[][] answer = conv.apply();

        verify(answer);
    }

    private static void parallelLinearDistribution(int[][] image, int[][] kernel, int P) throws IOException {
        Convolution conv = new LinearDistributionConvolution(image, kernel, P);
        int[][] answer = conv.apply();

        verify(answer);
    }

    public static void verify(int[][] answer) throws IOException {
        int[][] answerFile = Utils.readMatrix("C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela si distributiva\\Tema1\\Tema1_Java\\result.txt");

        if (!Arrays.deepEquals(answer, answerFile)) {
            throw new RuntimeException("Rezultatele nu coincide.");
        };
    }

}
