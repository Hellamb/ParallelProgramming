package lab5;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var threads = new ArrayList<Thread>();

        for (int i = 0; i < 5; i++) {
            var thread = new Thread(() -> {
                var queuingSystem = new QueuingSystem(5, 100, 10);
                var taskProvider = new TaskProvider(1000, queuingSystem, 10, 20);
                var observer = new Observer(queuingSystem, 500);
                observer.start();
                try {
                    taskProvider.run();
                    observer.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queuingSystem.finish();
            });
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }
}
