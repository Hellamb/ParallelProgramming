package lab2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static lab2.SafeMatrix.emptyMatrix;

public class MatrixStringMultiplication {
    public static SafeMatrix stringMultiply(SafeMatrix matrixA, SafeMatrix matrixB, int threads) {
        if (matrixA.colAmount() != matrixB.rowAmount()) {
            throw new IllegalArgumentException("Non-multipliable matrix");
        }

        SafeMatrix resultMatrix = emptyMatrix(matrixA.rowAmount(), matrixB.colAmount());

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int row = 0; row < matrixA.colAmount(); row++) {
            for (int col = 0; col < matrixB.rowAmount(); col++) {
                executor.execute(new MatrixMultiplierTask(row, col, matrixA, matrixB, resultMatrix));
            }
        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultMatrix;
    }

    private static class MatrixMultiplierTask implements Runnable {
        private final int row;
        private final int col;
        private final SafeMatrix matrixA;
        private final SafeMatrix matrixB;
        private final SafeMatrix resultMatrix;

        public MatrixMultiplierTask(int row, int col, SafeMatrix matrixA, SafeMatrix matrixB, SafeMatrix resultMatrix) {
            this.row = row;
            this.col = col;
            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.resultMatrix = resultMatrix;
        }

        @Override
        public void run() {
            int sum = 0;

            for (int i = 0; i < matrixA.colAmount(); i++) {
                sum += matrixA.get(row, i) * matrixB.get(i, col);
            }

            resultMatrix.set(row, col, sum);
        }
    }
}
