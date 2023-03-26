package lab1.task6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Not synchronized: ");
        notSync();

        System.out.println("Synchronized method: ");
        syncMethod();

        System.out.println("Synchronized block: ");
        syncBlock();

        System.out.println("Synchronizing object: ");
        syncObject();
    }

    private static void notSync() throws InterruptedException {
        Counter counter = new Counter();

        Thread incThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.increment();
            }
        });

        Thread decThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.decrement();
            }
        });

        incThread.start();
        decThread.start();

        incThread.join();
        decThread.join();

        System.out.println("Counter value: " + counter.getValue());
    }

    private static void syncMethod() throws InterruptedException {
        Counter counter = new Counter();

        Thread incThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.syncMethodIncrement();
            }
        });

        Thread decThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.syncMethodDecrement();
            }
        });

        incThread.start();
        decThread.start();

        incThread.join();
        decThread.join();

        System.out.println("Counter value: " + counter.getValue());
    }

    private static void syncBlock() throws InterruptedException {
        Counter counter = new Counter();

        Thread incThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.syncBlockIncrement();
            }
        });

        Thread decThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.syncBlocDecrement();
            }
        });

        incThread.start();
        decThread.start();

        incThread.join();
        decThread.join();

        System.out.println("Counter value: " + counter.getValue());
    }

    private static void syncObject() throws InterruptedException {
        Counter counter = new Counter();
        final Lock lock = new ReentrantLock();

        Thread incThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                lock.lock();
                try {
                    counter.increment();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread decThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                lock.lock();
                try {
                    counter.decrement();
                } finally {
                    lock.unlock();
                }
            }
        });

        incThread.start();
        decThread.start();

        incThread.join();
        decThread.join();

        System.out.println("Counter value: " + counter.getValue());
    }

}
