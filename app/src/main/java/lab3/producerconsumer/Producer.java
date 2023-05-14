package lab3.producerconsumer;

import java.util.Random;

public class Producer implements Runnable {
    private final Drop<Integer> drop;

    public Producer(Drop<Integer> drop) {
        this.drop = drop;
    }

    public void run() {
//        String[] importantInfo = {
//                "Mares eat oats",
//                "Does eat oats",
//                "Little lambs eat ivy",
//                "A kid will eat ivy too"
//        };

//        Random random = new Random();

        for (int i = 0; i < 100 ; i++) {
            drop.put(i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {
            }
        }
        drop.put(0);
    }
}
