package org.example;

class ColumnsBatchConvolution extends Convolution {
    public ColumnsBatchConvolution(int[][] matrix, int[][] conv, int P) {
        super(matrix, conv, P);
    }

    /**
     * Filters an image by applying a convolution kernel across the matrix in parallel,
     * with each thread processing a subset of columns.
     * @return a 2D array of the filtered image
     */
    @Override
    public int[][] apply() {
        int[][] answer = new int[N][M];
        Thread[] threads = new Thread[P];
        int threadId = 0;

        int columnsPerThread = M / P;
        int columnsLeft = M % P;

        for (int k = 0; k < M; k += columnsPerThread) {
            int start = k;
            if (columnsLeft > 0) {
                --columnsLeft;
                ++k;
            }
            int end = k + columnsPerThread - 1;

            threads[threadId] = new Thread(() -> {
                for (int j = start; j <= end; j++) {
                    for (int i = 0; i < N; i++) {
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
