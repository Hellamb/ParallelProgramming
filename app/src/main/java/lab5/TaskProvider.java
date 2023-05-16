package lab5;

import java.util.Random;

public class TaskProvider {
    private final int tasksAmount;
    private final QueuingSystem queuingSystem;
    private final int minDelay;
    private final int maxDelay;

    public TaskProvider(int tasksAmount, QueuingSystem queuingSystem, int minDelay, int maxDelay) {
        this.tasksAmount = tasksAmount;
        this.queuingSystem = queuingSystem;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
    }

    public void run() throws InterruptedException {
        var random = new Random();
        for (int i = 0; i < tasksAmount; i++) {
            queuingSystem.addTask();
            var delay = random.nextInt(minDelay, maxDelay);
            Thread.sleep(delay);
        }
    }
}
