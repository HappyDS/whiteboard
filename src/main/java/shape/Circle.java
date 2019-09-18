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

    public Circle(Point center, int radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.drawArc(center.getX(), center.getY(), radius, radius, 0, 360);
    }
}
