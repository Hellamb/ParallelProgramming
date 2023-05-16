package lab4.matrixmultiply;

import lab2.SafeMatrix;

import java.util.concurrent.RecursiveTask;

import static lab2.SafeMatrix.emptyMatrix;
import static lab2.SafeMatrix.matrixFrom;

public class ForkFoxMultiplication extends RecursiveTask<SafeMatrix> {

    private SafeMatrix matrixA;
    private SafeMatrix matrixB;
    private int minBlockSize;

    public ForkFoxMultiplication(SafeMatrix matrixA, SafeMatrix matrixB, int minBlockSize) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.minBlockSize = minBlockSize;
    }

    @Override
    protected SafeMatrix compute() {
        if (matrixA.colAmount() != matrixB.rowAmount()) {
            throw new IllegalArgumentException("Non-multipliable matrix");
        }

        int blockSize = matrixA.colAmount() / 2;

        if (blockSize <= minBlockSize) {
            return multiply(matrixA, matrixB);
        }

        SafeMatrix resultMatrix = emptyMatrix(matrixA.rowAmount(), matrixB.colAmount());

        SafeMatrix[][] blocksA = matrixA.splitOnBlocks(blockSize);
        SafeMatrix[][] blocksB = matrixB.splitOnBlocks(blockSize);
        SafeMatrix[][] blocksResult = resultMatrix.splitOnBlocks(blockSize);
        ForkFoxMultiplication[][][] forkMultiplications = new ForkFoxMultiplication[blocksA.length][blocksA[0].length][blocksResult.length];

        for (int i = 0; i < blocksResult.length; i++) {
            for (int j = 0; j < blocksResult[0].length; j++) {
                for (int k = 0; k < blocksResult.length; k++) {
                    forkMultiplications[i][j][k] = new ForkFoxMultiplication(blocksA[i][k], blocksB[k][j], minBlockSize);
                    invokeAll(forkMultiplications[i][j][k]);
                }
            }
        }

        for (int i = 0; i < blocksResult.length; i++) {
            for (int j = 0; j < blocksResult[0].length; j++) {

                SafeMatrix block = emptyMatrix(blocksResult[0][0].rowAmount(), blocksResult[0][0].colAmount());
                for (int k = 0; k < blocksResult.length; k++) {
                    block.addMatrix(forkMultiplications[i][j][k].join());
                }
                blocksResult[i][j].addMatrix(block);

            }
        }

        return matrixFrom(resultMatrix.rowAmount(), resultMatrix.colAmount(), blocksResult);
    }

    public static SafeMatrix multiply(SafeMatrix a, SafeMatrix b) {
        var result = emptyMatrix(b.colAmount(), a.rowAmount());
        for (int i = 0; i < a.rowAmount(); i++) {
            for (int j = 0; j < b.colAmount(); j++) {
                for (int k = 0; k < a.colAmount(); k++) {
                    result.set(i, j, result.get(i, j) + a.get(i, k) * b.get(k, j));
                }
            }
        }
        return result;
    }
}
