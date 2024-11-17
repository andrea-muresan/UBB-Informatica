package org.example;

import java.io.IOException;
import java.util.concurrent.CyclicBarrier;

public class ParallelMain {
    public static void main(String[] args) throws IOException {
//        int P = 2;
        int P = Integer.parseInt(args[0]);
        CyclicBarrier barrier = new CyclicBarrier(P);

        int[][] image = Utils.readMatrix("C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela si distributiva\\Tema2\\Tema2_Java\\data.txt");
        int[][] kernel = Utils.readMatrix("C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela si distributiva\\Tema2\\Tema2_Java\\filter.txt");
        int N = image.length;

        int start = 0, end;
        int linesPerThread = N / P;
        int linesLeft = N % P;

        long startTime = System.nanoTime();

        ConvolutionTask[] threads = new ConvolutionTask[P];
        for (int i = 0; i < P; i++) {
            end = start + linesPerThread + (i < linesLeft ? 1 : 0);
            threads[i] = new ConvolutionTask(image, kernel, start, end, barrier);
            threads[i].start();
            start = end;
        }

        try {
            for (int i = 0; i < P; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        long endTime = System.nanoTime();
        System.out.println((double)(endTime - startTime)/1E6);//ms
//        Utils.writeMatrix(image, "result1.txt");
        Utils.validate(image);
    }
}
