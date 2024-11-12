package org.example;

public class SequentialConvolution extends Convolution {
    public SequentialConvolution(int[][] matrix, int[][] conv) {
        super(matrix, conv, 0);
    }

    /**
     * Filters an image by applying a convolution kernel across the matrix sequentially,
     * processing each element.
     * @return a 2D array of the filtered image
     */
    @Override
    public int[][] apply() {
        int[][] answer = new int[N][M];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j){
                answer[i][j] = oneKernel(i, j);
            }
        }
        return answer;
    }
}
