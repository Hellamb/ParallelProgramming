package lab1.task4;

import java.util.Optional;

public class BallThread extends Thread {
    private final Ball b;
    private final Optional<Thread> prevBall;

    public BallThread(Ball ball, Optional<Thread> prevBall) {
        b = ball;
        this.prevBall = prevBall;
    }

    @Override
    public void run() {
        try {
            if (prevBall.isPresent()) {
                prevBall.get().join();
            }
            for (int i = 1; i < 10000; i++) {
                if (b.isCollected()) {
                    return;
                }
                b.move();
                Thread.sleep(5);

            }
        } catch (InterruptedException ignored) {
        }
    }
}
