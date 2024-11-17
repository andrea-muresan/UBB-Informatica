package org.example;

public class ConvolutionOperation {
    private static ConvolutionOperation instance = null;
    private ConvolutionOperation() {
    }
    public static ConvolutionOperation getInstance() {
        if (instance == null) {
            instance = new ConvolutionOperation();
        }
        return instance;
    }

    /**
     * Retrieves the matrix element at the specified position,
     * adjusting out-of-bounds indices to the nearest valid values for safe access.
     * @param i - the row index of th element
     * @param j - the column index of the element
     * @param image - the matrix representing the image
     * @return the value of the element
     */
    private int matrixElemExtended(int i, int j, int[][] image) {
        int N = image.length;
        int M = image[0].length;

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
     * @param image - the matrix representing the image
     * @param kernel - the matrix representing the kernel
     * @return the new value of the element
     */
    public int oneKernel(int[][] image, int[][] kernel, int i, int j) {
        int K = kernel.length;

        int newValue = 0;
        int offset = K / 2;
        // Apply the kernel to the current position
        for (int ki = -offset; ki <= offset; ki++) {
            for (int kj = -offset; kj <= offset; kj++) {
                int mi = i + ki;
                int mj = j + kj;
                newValue += matrixElemExtended(mi, mj, image) * kernel[ki + offset][kj + offset];
            }
        }

        return newValue;
    }
}
