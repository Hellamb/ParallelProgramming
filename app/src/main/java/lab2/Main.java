package lab2;

import java.awt.*;

import static lab2.MatrixFoxMultiplication.foxMultiply;
import static lab2.MatrixStringMultiplication.stringMultiply;
import static lab2.SafeMatrix.randomMatrix;

public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 1024; i*=2) {
            testBySizeString(i);
            System.out.println("\n\n------------------------------------------\n\n");
        }
        System.out.println("\n\n\n\n\n\n-----------------------------------------------------------\n\n\n\n\n\n");
        for (int i = 1; i <= 1024; i*=2) {
            testBySizeFox(i);
            System.out.println("\n\n------------------------------------------\n\n");
        }
    }

    private static void testBySizeString(int threads) {
        for (int size = 1; size <= 2048; size*=2) {

            long time = System.nanoTime();

            SafeMatrix m1 = randomMatrix(size, size);
            SafeMatrix m2 = randomMatrix(size, size);
            stringMultiply(m1, m2, threads);

            time = System.nanoTime() - time;

            System.out.println("Test 'String' matrix multiply | Threads: "+threads+", Size: "
                    + size + " | Time: " + time + " nano sec.");
        }
    }

    private static void testBySizeFox(int threads) {
        for (int size = 1; size <= 2048; size*=2) {

            long time = System.nanoTime();

            SafeMatrix m1 = randomMatrix(size, size);
            SafeMatrix m2 = randomMatrix(size, size);
            foxMultiply(m1, m2, threads, (int) Math.sqrt(size));

            time = System.nanoTime() - time;

            System.out.println("Test 'Fox's' matrix multiply | Threads: "+threads+", Size: "
                    + size + " | Time: " + time + " nano sec.");
        }
    }
}
