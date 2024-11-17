package org.example;

public class LinearDistributionConvolution extends Convolution{
    public LinearDistributionConvolution(int[][] matrix, int[][] conv, int P) {
        super(matrix, conv, P);
    }

    @Override
    public int[][] apply() {
        int[][] answer = new int[N][M];

        Thread[] threads = new Thread[P];

        int len = N * M;
        int lenPerThread = len / P;
        int lenLeft = len % P;

        int start = 0, end = lenPerThread;

        for (int threadId = 0; threadId < P; threadId ++) {
            if (lenLeft > 0) {
                lenLeft--;
                end++;
            }

            int finalEnd = end;
            int finalStart = start;
            threads[threadId] = new Thread(() -> {
                for (int t = finalStart; t < finalEnd; t++) {
                    int i = t / M;
                    int j = t % M;
                    answer[i][j] = oneKernel(i, j);

                }
            });
            threads[threadId].start();

            start = end;
            end = start + lenPerThread;
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
