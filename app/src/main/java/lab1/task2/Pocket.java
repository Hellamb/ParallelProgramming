package lab1.task2;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Pocket {
    private final int x;
    private final int y;
    private final int size;
    private final Rectangle2D rectangle;

    public Pocket(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        rectangle = new Rectangle2D.Double(x, y, size, size);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        g2.fill(rectangle);
    }

    public boolean isBallCollected(int x, int y, int ballRadius) {
        return rectangle.intersects(x, y, ballRadius, ballRadius);
    }
}
