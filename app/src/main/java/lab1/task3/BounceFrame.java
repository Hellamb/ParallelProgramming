package lab1.task3;

import javax.swing.*;
import java.awt.*;

public class BounceFrame extends JFrame {
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    private static final int LOW_PRIORITY = 1;
    private static final int HIGH_PRIORITY = 10;
    private final BallCanvas canvas;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program");
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton spawnBlue = new JButton("Spawn 100 blue balls");
        JButton spawnRed = new JButton("Spawn 1 red ball");
        JButton buttonStop = new JButton("Stop");
        spawnBlue.addActionListener(e -> {

            for (int i = 0; i < 100; i++) {
                Ball b = new Ball(canvas, Color.BLUE);
                canvas.add(b);

                BallThread thread = new BallThread(b, LOW_PRIORITY);
                thread.start();
                System.out.println("Thread name = " +
                        thread.getName());
            }
        });
        spawnRed.addActionListener(e -> {

            Ball b = new Ball(canvas, Color.RED);
            canvas.add(b);

            BallThread thread = new BallThread(b, HIGH_PRIORITY);
            thread.start();
            System.out.println("Thread name = " +
                    thread.getName());
        });
        buttonStop.addActionListener(e -> System.exit(0));
        buttonPanel.add(spawnBlue);
        buttonPanel.add(spawnRed);
        buttonPanel.add(buttonStop);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
