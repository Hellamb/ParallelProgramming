package lab4.matrixmultiply;

import lab2.SafeMatrix;

import java.util.concurrent.ForkJoinPool;

import static lab2.MatrixFoxMultiplication.foxMultiply;

public class Main {
    public static void main(String[] args) {
        var m1 = SafeMatrix.randomMatrix(200, 200);
        var m2 = SafeMatrix.randomMatrix(200, 200);
        var multiplication = new ForkFoxMultiplication(m1, m2, 50);
        var forkJoinPool = new ForkJoinPool();


//        forkJoinPool.invoke(multiplication);
//        var result = multiplication.join();
//        System.out.println(m1);
//        System.out.println(m2);
//        System.out.println(result);

        var start = System.nanoTime();
        forkJoinPool.invoke(multiplication);
        multiplication.join();
        var end = System.nanoTime();
        System.out.println("ForkJoin result: " + (end - start));

        start = System.nanoTime();
        foxMultiply(m1, m2, 12, 100);
        end = System.nanoTime();
        System.out.println("Executors result: " + (end - start));
    }
}
