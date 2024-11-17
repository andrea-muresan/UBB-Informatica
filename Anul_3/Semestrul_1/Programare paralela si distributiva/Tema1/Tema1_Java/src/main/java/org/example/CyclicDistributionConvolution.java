package org.example;

public class CyclicDistributionConvolution extends Convolution{
    public CyclicDistributionConvolution(int[][] matrix, int[][] conv, int P) {
        super(matrix, conv, P);
    }

    @Override
    public int[][] apply() {
        int[][] answer = new int[N][M];

        Thread[] threads = new Thread[P];

        int len = N * M;

        for (int threadId = 0; threadId < P; threadId ++) {

            int finalStart = threadId;
            threads[threadId] = new Thread(() -> {
                for (int t = finalStart; t < len; t+=P) {
                    int i = t / M;
                    int j = t % M;
                    answer[i][j] = oneKernel(i, j);

                }
            });
            threads[threadId].start();
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
