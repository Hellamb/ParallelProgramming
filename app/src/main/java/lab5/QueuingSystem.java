package lab5;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class QueuingSystem {
    private final AtomicInteger freeChannels;
    private final AtomicInteger passed = new AtomicInteger(0);
    private final AtomicInteger rejected = new AtomicInteger(0);
    private final AtomicInteger querySizeSum = new AtomicInteger(0);
    private final long processingTime;
    private final ExecutorService executors;
    private final Queue<Runnable> queue;
    private final int maxQueueSize;

    public QueuingSystem(int channels, long processingTime, int queueSize) {
        this.freeChannels = new AtomicInteger(channels);
        this.processingTime = processingTime;
        executors = Executors.newFixedThreadPool(channels);
        queue = new ArrayDeque<>(queueSize);
        maxQueueSize = queueSize;
    }

    public synchronized void addTask() {
        querySizeSum.addAndGet(queue.size());
        if (queue.size() >= maxQueueSize) {
            rejected.incrementAndGet();
            return;
        }

        var task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(processingTime);
                } catch (InterruptedException ignored) {
                }
                taskPassed();
            }
        };

        if (freeChannels.get() > 0) {
            freeChannels.decrementAndGet();
            executors.submit(task);
        } else {
            queue.add(task);
        }
    }

    public void finish() {
        executors.shutdown();
    }

    private synchronized void taskPassed() {
        passed.incrementAndGet();
        if (queue.isEmpty()) {
            freeChannels.incrementAndGet();
        } else {
            executors.submit(queue.poll());
        }
    }

    public String stats() {
        double avgQueue = (double)querySizeSum.get() / (passed.get() + rejected.get());
        return "Passed: " + passed.get() + " | Rejected: " + rejected.get() + " | Average queue: " + avgQueue;
    }
}
