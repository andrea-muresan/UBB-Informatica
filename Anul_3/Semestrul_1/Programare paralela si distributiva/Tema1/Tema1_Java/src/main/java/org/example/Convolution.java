package org.example;

public abstract class Convolution {
    protected final int N; // Number of rows
    protected final int M; // Number of columns
    protected final int K; // Kernel size
    protected final int P; // Number of threads
    protected final int[][] image;
    protected final int[][] kernel;

    public Convolution(int[][] image, int[][] kernel, int P) {
        N = image.length;
        if (N > 0) {
            M = image[0].length;
        } else {
            M = 0;
        }
        this.image = image;
        K = kernel.length;
        this.kernel = kernel;
        this.P = P;
    }


    /**
     * Retrieves the matrix element at the specified position,
     * adjusting out-of-bounds indices to the nearest valid values for safe access.
     * @param i - the row index of th element
     * @param j - the column index of the element
     * @return the value of the element
     */
    private int matrixElemExtended(int i, int j) {
        if (i < 0) {
            i = 0;
        } else if (i >= N) {
            i = N - 1;
        }
        if (j < 0) {
            j = 0;
        } else if (j >= M) {
            j = M - 1;
        }
        return image[i][j];
    }


    /**
     * Applies the convolution kernel to a specific element in the matrix at position (i, j)
     * @param i - the row index of the element
     * @param j - the column index of the element
     * @return the new value of the element
     */
    protected int oneKernel(int i, int j) {
        int newValue = 0;
        int offset = K / 2;
        // Apply the kernel to the current position
        for (int ki = -offset; ki <= offset; ki++) {
            for (int kj = -offset; kj <= offset; kj++) {
                int mi = i + ki;
                int mj = j + kj;
                newValue += matrixElemExtended(mi, mj) * kernel[ki + offset][kj + offset];
            }
        }

        return newValue;
    }

    public abstract int[][] apply();
}
