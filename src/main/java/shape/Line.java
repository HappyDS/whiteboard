package shape;

import java.awt.*;

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
public class Line implements IShape {
    private final int type = 1;
    private Point start;
    private Point end;
    private Color color;

    public Line(Point start, Point end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
    }
}
