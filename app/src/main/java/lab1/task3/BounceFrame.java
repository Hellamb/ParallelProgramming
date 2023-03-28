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
        JButton spawn100Blue = new JButton("Spawn 100 blue balls");
        JButton spawn500Blue = new JButton("Spawn 500 blue balls");
        JButton spawn1000Blue = new JButton("Spawn 1000 blue balls");
        JButton spawnRed = new JButton("Spawn 1 red ball");
        JButton buttonStop = new JButton("Stop");
        spawn100Blue.addActionListener(e -> {spawnBlue(100);});
        spawn500Blue.addActionListener(e -> {spawnBlue(500);});
        spawn1000Blue.addActionListener(e -> {spawnBlue(1000);});
        spawnRed.addActionListener(e -> {

            Ball b = new Ball(canvas, Color.RED);
            canvas.add(b);

            BallThread thread = new BallThread(b, HIGH_PRIORITY);
            thread.start();
            System.out.println("Thread name = " +
                    thread.getName());
        });
        buttonStop.addActionListener(e -> System.exit(0));
        buttonPanel.add(spawn100Blue);
        buttonPanel.add(spawn500Blue);
        buttonPanel.add(spawn1000Blue);
        buttonPanel.add(spawnRed);
        buttonPanel.add(buttonStop);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void spawnBlue(int count) {
        for (int i = 0; i < count; i++) {
            Ball b = new Ball(canvas, Color.BLUE);
            canvas.add(b);

            BallThread thread = new BallThread(b, LOW_PRIORITY);
            thread.start();
            System.out.println("Thread name = " +
                    thread.getName());
        }
    }
}
