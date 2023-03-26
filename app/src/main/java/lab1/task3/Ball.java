package lab1.task3;

import java.awt.*;
import java.awt.geom.Ellipse2D;

class Ball {
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private final Component canvas;
    private int x = 0;
    private int y = 0;
    private int dx = 1;
    private int dy = 1;
    private final Color color;


    public Ball(Component c, Color color) {
        this.canvas = c;
        this.color = color;
        x = this.canvas.getWidth() / 2;
        y = this.canvas.getHeight() / 2;
    }

    public static void f() {
        int a = 0;
    }

    public void draw(Graphics2D g2) {
        if (color == Color.blue) g2.setColor(Color.blue);
        if (color == Color.red) g2.setColor(Color.red);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));

    }

    public void move() {
        x += dx;
        y += dy;
        if (x < 0) {
            x = 0;
            dx = -dx;
        }
        if (x + XSIZE >= this.canvas.getWidth()) {
            x = this.canvas.getWidth() - XSIZE;
            dx = -dx;
        }
        if (y < 0) {
            y = 0;
            dy = -dy;
        }
        if (y + YSIZE >= this.canvas.getHeight()) {
            y = this.canvas.getHeight() - YSIZE;
            dy = -dy;
        }
        this.canvas.repaint();
    }
}
