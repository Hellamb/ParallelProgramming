package lab1.task4;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class BounceFrame extends JFrame {
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    public JLabel collectedLabel;
    private final BallCanvas canvas;
    private Thread lastBallThread;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm");
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");
        collectedLabel = new JLabel("Collected balls: 0");
        canvas.setCollectedLabel(collectedLabel);
        buttonStart.addActionListener(event -> {

            Ball b = new Ball(canvas.getPockets(), canvas);
            canvas.add(b);

            BallThread thread = new BallThread(b, Optional.ofNullable(lastBallThread));
            lastBallThread = thread;

            thread.start();
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread name = " +
                    thread.getName());

        });
        buttonStop.addActionListener(event -> System.exit(0));
        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        buttonPanel.add(collectedLabel);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
