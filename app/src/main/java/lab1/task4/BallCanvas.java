package lab1.task4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    private final ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Pocket> pockets;
    private int collectedCounter = 0;
    private JLabel collectedLabel;

    public void add(Ball b) {
        this.balls.add(b);
    }

    public ArrayList<Pocket> getPockets() {
        return pockets;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        pockets = generatePockets();
        paintPockets(g2);
        for (Ball ball : balls) {
            if (ball.isCollected()) {
                collectedCounter++;
            }
        }
        collectedLabel.setText("Collected balls: " + collectedCounter);
        balls.removeIf(Ball::isCollected);
        balls.forEach(ball -> ball.draw(g2));
    }

    private ArrayList<Pocket> generatePockets() {
        ArrayList<Pocket> pockets = new ArrayList<>();
        pockets.add(new Pocket(0, 0, 20));
        pockets.add(new Pocket(0, this.getHeight() - 20, 20));
        pockets.add(new Pocket(this.getWidth() - 20, 0, 20));
        pockets.add(new Pocket(this.getWidth() - 20, this.getHeight() - 20, 20));
        return pockets;
    }

    private void paintPockets(Graphics2D g2) {
        for (Pocket p : pockets) {
            p.draw(g2);
        }
    }

    public void setCollectedLabel(JLabel collectedLabel) {
        this.collectedLabel = collectedLabel;
    }
}
