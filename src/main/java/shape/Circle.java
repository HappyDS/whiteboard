package shape;

import java.awt.*;

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
public class Circle implements IShape {

    private final int type = 3;
    private Point center;
    private int radius;
    private Color color;
    private int size;

    public Circle(Point center, int radius, Color color, int size) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.size = size;
    }

    public void draw(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        BasicStroke bs = new BasicStroke(size, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL);
        graphics2D.setStroke(bs);
        g.setColor(color);
        g.drawArc(center.getX(), center.getY(), radius, radius, 0, 360);
    }
}
