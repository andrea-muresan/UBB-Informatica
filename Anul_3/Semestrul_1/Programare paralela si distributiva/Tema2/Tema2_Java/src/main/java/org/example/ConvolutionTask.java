package org.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ConvolutionTask extends Thread {
    private final int[][] image;
    private final int[][] kernel;
    private final int start;
    private final int end;
    private final CyclicBarrier barrier;
    private final ConvolutionOperation convolution = ConvolutionOperation.getInstance();

    public ConvolutionTask(int[][] image, int[][] kernel, int start, int end, CyclicBarrier barrier) {
        this.image = image;
        this.kernel = kernel;
        this.start = start;
        this.end = end;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        int M = image[0].length;
        int [] buffFirstLine = new int[M]; // = last line - previous thread
        int [] buffCurrentLine = new int[M];
        int [] buffLastLine = new int[M]; // = first line - next thread

        // first line
        if (start == 0) {
            System.arraycopy(image[0], 0, buffFirstLine, 0, M);
        } else {
            System.arraycopy(image[start - 1], 0, buffFirstLine, 0, M);
        }

        // last line
        if (end == image.length) {
            System.arraycopy(image[image.length - 1], 0, buffLastLine, 0, M);
        } else {
            System.arraycopy(image[end], 0, buffLastLine, 0, M);
        }

        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        // current line
        System.arraycopy(image[start], 0, buffCurrentLine, 0, M);


        int[][] buffer = new int[3][M];
        System.arraycopy(buffFirstLine, 0, buffer[0], 0, M);
        System.arraycopy(buffCurrentLine, 0, buffer[1], 0, M);
        System.arraycopy(image[start + 1], 0, buffer[2], 0, M);

        for (int i = start; i < end - 1; ++i) {
            for (int j = 0; j < M; ++j) {
                image[i][j] = convolution.oneKernel(buffer, kernel, 1, j);
            }
            System.arraycopy(buffer[1], 0, buffer[0], 0, M);
            System.arraycopy(buffer[2], 0, buffer[1], 0, M);
            if (i + 2 == image.length || i + 2 == end)
                System.arraycopy(buffLastLine, 0, buffer[2], 0, M);
            else
                System.arraycopy(image[i + 2], 0, buffer[2], 0, M);
        }

        for (int j = 0; j < M; ++j) {
            image[end - 1][j] = convolution.oneKernel(buffer, kernel, 1, j);
        }
    }
}
