package lab2;

import java.util.Arrays;
import java.util.Random;

public class SafeMatrix {
    private final int[][] matrix;

    private SafeMatrix(int[][] matrix) {
        this.matrix = matrix.clone();
    }

    public static SafeMatrix randomMatrix(int rowSize, int colSize) {
        Random random = new Random();
        int[][] matrix = new int[rowSize][colSize];
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                matrix[i][j] = random.nextInt(1000);
            }
        }
        return new SafeMatrix(matrix);
    }

    public static SafeMatrix emptyMatrix(int rowSize, int colSize) {
        int[][] matrix = new int[rowSize][colSize];
        return new SafeMatrix(matrix);
    }

    public static SafeMatrix matrixFrom(int rowAmount, int colAmount, SafeMatrix[][] blocks) {
        if (blocks[0][0].rowAmount() * blocks.length < rowAmount ||
                blocks[0][0].colAmount() * blocks[0].length < colAmount) {
            throw new IllegalStateException("Matrix can't be created from blocks");
        }
        int[][] newMatrix = new int[rowAmount][colAmount];
        for (int i = 0; i < rowAmount; i++) {
            for (int j = 0; j < colAmount; j++) {
                int blockRow = i / blocks[0][0].rowAmount();
                int blockCol = j / blocks[0][0].colAmount();
                int inBlockRow = i % blocks[0][0].rowAmount();
                int inBlockCol = j % blocks[0][0].colAmount();
                newMatrix[i][j] = blocks[blockRow][blockCol].get(inBlockRow, inBlockCol);
            }
        }
        return new SafeMatrix(newMatrix);
    }

    public int rowAmount() {
        return matrix.length;
    }

    public int colAmount() {
        return matrix[0].length;
    }

    public int get(int row, int col) {
        return matrix[row][col];
    }

    public synchronized void set(int row, int col, int value) {
        matrix[row][col] = value;
    }

    /**
     * Adds provided matrix to the current.
     * If provided matrix has more rows or columns, they will be ignored.
     * If provided matrix has fewer rows or columns, {@link IllegalStateException} will be thrown.
     */
    public synchronized void addMatrix(SafeMatrix matrixToAdd) {
        if (matrixToAdd.rowAmount() < rowAmount() || matrixToAdd.colAmount() < colAmount()) {
            throw new IllegalStateException("Matrix can't be added");
        }

        for (int i = 0; i < rowAmount(); i++) {
            for (int j = 0; j < colAmount(); j++) {
                matrix[i][j] += matrixToAdd.get(i, j);
            }
        }
    }

    public SafeMatrix[][] splitOnBlocks(int blockSize) {
        int rowBlockAmount = rowAmount() % blockSize == 0 ? rowAmount() / blockSize : rowAmount() / blockSize + 1;
        int colBlockAmount = colAmount() % blockSize == 0 ? colAmount() / blockSize : colAmount() / blockSize + 1;
        SafeMatrix[][] blocks = new SafeMatrix[rowBlockAmount][colBlockAmount];

        for (int i = 0; i < rowBlockAmount; i++) {
            for (int j = 0; j < colBlockAmount; j++) {
                int[][] singleBlock = new int[blockSize][blockSize];
                for (int m = 0; m < blockSize; m++) {
                    for (int n = 0; n < blockSize; n++) {
                        int row = i * blockSize + m;
                        int col = j * blockSize + n;
                        if (row >= rowAmount() || col >= colAmount()) {
                            singleBlock[m][n] = 0;
                        } else {
                            singleBlock[m][n] = matrix[row][col];
                        }
                    }
                }
                blocks[i][j] = new SafeMatrix(singleBlock);
            }
        }
        return blocks;
    }

    @Override
    public String toString() {
        return "SafeMatrix{" +
                "matrix=" + Arrays.deepToString(matrix) +
                '}';
    }
}
