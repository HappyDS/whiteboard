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
    private int size;

    public Line(Point start, Point end, Color color, int size) {
        this.start = start;
        this.end = end;
        this.color = color;
        this.size = size;
    }

    public void draw(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        BasicStroke bs = new BasicStroke(size, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL);
        graphics2D.setStroke(bs);
        g.setColor(color);
        g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
    }
}
