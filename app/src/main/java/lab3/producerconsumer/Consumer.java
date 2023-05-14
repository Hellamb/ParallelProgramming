package lab3.producerconsumer;

import java.util.Random;

public class Consumer<T> implements Runnable {
    private final Drop<T> drop;

    public Consumer(Drop<T> drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();
        for (T message = drop.take();
             !message.equals("DONE");
             message = drop.take()) {
            System.out.format("MESSAGE RECEIVED: %s%n", message);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException ignored) {
            }
        }
    }
}
