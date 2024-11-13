package org.example;

import java.io.IOException;

public class SequentialMain {
    private static final ConvolutionOperation convolution = ConvolutionOperation.getInstance();

    public static void main(String[] args) throws IOException {
//        MatrixGenerator.generate(5, 5, "image");
//        MatrixGenerator.generate(3, 3, "kernel");

        int[][] image = Utils.readMatrix("C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela si distributiva\\Tema2\\Tema2_Java\\data.txt");
        int[][] kernel = Utils.readMatrix("C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela si distributiva\\Tema2\\Tema2_Java\\filter.txt");
        int N = image.length;
        int M = image[0].length;

        long startTime = System.nanoTime();

        int[] buffer1 = new int[M];
        int[] buffer2 = new int[M];

        for (int j = 0; j < M; ++j) {
            buffer1[j] = convolution.oneKernel(image, kernel,0,j);
        }

        for (int i = 1; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                buffer2[j] = convolution.oneKernel(image, kernel, i, j);
            }

            System.arraycopy(buffer1, 0, image[i - 1], 0, M);
            System.arraycopy(buffer2, 0, buffer1, 0, M);
        }

        System.arraycopy(buffer2, 0, image[N - 1], 0, M);

        long endTime = System.nanoTime();
        System.out.println((double)(endTime - startTime)/1E6);//ms
        Utils.writeMatrix(image, "result.txt");
    }
}
