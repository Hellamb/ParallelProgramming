package lab5;

import java.util.concurrent.atomic.AtomicBoolean;

public class Observer {
    private final QueuingSystem queuingSystem;
    private Thread thread;
    private AtomicBoolean isFinished = new AtomicBoolean(false);
    private int observingInterval;

    public Observer(QueuingSystem queuingSystem, int observingInterval) {
        this.queuingSystem = queuingSystem;
        this.observingInterval = observingInterval;
    }

    public void start() {
        thread = new Thread(() -> {
            while(!isFinished.get()) {
                System.out.println(Thread.currentThread().getName() + " " + queuingSystem.stats());
                try {
                    Thread.sleep(observingInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void stop() throws InterruptedException {
        isFinished.set(true);
        thread.join();
    }
}
