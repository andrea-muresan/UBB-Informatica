package org.example;

class LinesBatchConvolution extends Convolution {
    public LinesBatchConvolution(int[][] matrix, int[][] conv, int P) {
        super(matrix, conv, P);
    }

    /**
     * Filters an image by applying a convolution kernel across the matrix in parallel,
     * with each thread processing a subset of rows.
     * @return a 2D array of the filtered image
     */
    @Override
    public int[][] apply() {
        int[][] answer = new int[N][M];

        Thread[] threads = new Thread[P];
        int threadId = 0;

        int linesPerThread = N / P;
        int linesLeft = N % P;

        for (int k = 0; k < N; k += linesPerThread) {
            int start = k;
            if (linesLeft > 0) {
                linesLeft--;
                k++;
            }
            int end = k + linesPerThread - 1;

            threads[threadId] = new Thread(() -> {
                for (int i = start; i <= end; i++) {
                    for (int j = 0; j < M; j++) {
                        answer[i][j] = oneKernel(i, j);
                    }
                }
            });
            threads[threadId++].start();
        }

        for (Thread thread : threads) {
            try {
                if (thread != null) {
                    thread.join();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return answer;
    }
}
