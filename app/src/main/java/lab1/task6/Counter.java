package lab1.task6;

public class Counter {
    private int value = 0;
    private final Object locker = new Object();

    public int getValue() {
        return value;
    }

    public void increment() {
        value++;
    }

    public void decrement() {
        value--;
    }

    public synchronized void syncMethodIncrement() {
        value++;
    }

    public synchronized void syncMethodDecrement() {
        value--;
    }

    public void syncBlockIncrement() {
        synchronized (locker) {
            value++;
        }
    }

    public void syncBlocDecrement() {
        synchronized (locker) {
            value--;
        }
    }
}
