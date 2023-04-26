package lab2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static lab2.MatrixStringMultiplication.stringMultiply;
import static lab2.SafeMatrix.emptyMatrix;
import static lab2.SafeMatrix.matrixFrom;

public class MatrixFoxMultiplication {

    public static SafeMatrix foxMultiply(SafeMatrix matrixA, SafeMatrix matrixB, int threads, int blockSize) {
        if (matrixA.colAmount() != matrixB.rowAmount()) {
            throw new IllegalArgumentException("Non-multipliable matrix");
        }

        SafeMatrix resultMatrix = emptyMatrix(matrixA.rowAmount(), matrixB.colAmount());

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        SafeMatrix[][] blocksA = matrixA.splitOnBlocks(blockSize);
        SafeMatrix[][] blocksB = matrixB.splitOnBlocks(blockSize);
        SafeMatrix[][] blocksResult = resultMatrix.splitOnBlocks(blockSize);

        for (int i = 0; i < blocksResult.length; i++) {
            for (int j = 0; j < blocksResult[0].length; j++) {
                executor.execute(new ResultBlockTask(i, j, blocksA, blocksB, blocksResult));
            }
        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return matrixFrom(resultMatrix.rowAmount(), resultMatrix.colAmount(), blocksResult);
    }

    private static class ResultBlockTask implements Runnable {
        private final int row;
        private final int col;
        private final SafeMatrix[][] blocksA;
        private final SafeMatrix[][] blocksB;
        private final SafeMatrix[][] blocksResult;

        public ResultBlockTask(int row, int col, SafeMatrix[][] blocksA, SafeMatrix[][] blocksB, SafeMatrix[][] blocksResult) {
            this.row = row;
            this.col = col;
            this.blocksA = blocksA;
            this.blocksB = blocksB;
            this.blocksResult = blocksResult;
        }

        @Override
        public void run() {
            SafeMatrix block = emptyMatrix(blocksResult[0][0].rowAmount(), blocksResult[0][0].colAmount());
            for (int k = 0; k < blocksResult.length; k++) {
                SafeMatrix multiplyResult = stringMultiply(blocksA[row][k], blocksB[k][col], 1);
                block.addMatrix(multiplyResult);
            }
            blocksResult[row][col].addMatrix(block);
        }
    }
}
