package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlocksConvolution extends Convolution {

    public BlocksConvolution(int[][] matrix, int[][] conv, int P) {
        super(matrix, conv, P);
    }

    public static Block[] slice2d(int i, int j, int n, int m, int p) {
        if (p == 1) {
            return new Block[] { new Block(i, j, n, m) };
        }

        List<Block> blocks = new ArrayList<>();

        if (n - i > m - j) {
            Block[] s1 = slice2d(i, j, n / 2, m, p / 2);
            Block[] s2 = slice2d(i + n / 2, j, n, m, p / 2 + p % 2);

            Collections.addAll(blocks, s1);
            Collections.addAll(blocks, s2);
        } else {
            Block[] s1 = slice2d(i, j, n, m / 2, p / 2 + p % 2);
            Block[] s2 = slice2d(i, j + m / 2, n, m, p / 2);

            Collections.addAll(blocks, s1);
            Collections.addAll(blocks, s2);
        }

        return blocks.toArray(new Block[0]);
    }

    @Override
    public int[][] apply() {
        int[][] answer = new int[N][M];

        // Partition the matrix for `p` threads
        Block[] blocks = slice2d(0, 0, N, M, P);

        // Create threads to process each partition
        Thread[] threads = new Thread[P];
        for (int t = 0; t < P; t++) {
            Block block = blocks[t];
            threads[t] = new Thread(() -> {
                for (int i = block.firstLine; i < block.lastLine; i++) {
                    for (int j = block.firstColumn; j < block.lastColumn; j++) {
                        answer[i][j] = oneKernel(i, j);
                    }
                }
            });
            threads[t].start();
        }

        for (int t = 0; t < P; t++) {
            try {
                threads[t].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return answer;
    }

    public static class Block {
        final int firstLine, firstColumn, lastLine, lastColumn;

        public Block(int firstLine, int firstColumn, int lastLine, int lastColumn) {
            this.firstLine = firstLine;
            this.firstColumn = firstColumn;
            this.lastLine = lastLine;
            this.lastColumn = lastColumn;
        }
    }

}
