package shape;

import java.awt.*;

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
public class Rectangle implements IShape {
    private final int type = 2;
    private Point leftTop;
    private int width;
    private int height;
    private Color color;
    private int size;

    public Rectangle(Point leftTop, int width, int height, Color color, int size) {
        this.leftTop = leftTop;
        this.width = width;
        this.height = height;
        this.color = color;
        this.size = size;
    }

    public void draw(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        BasicStroke bs = new BasicStroke(size, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL);
        graphics2D.setStroke(bs);
        g.setColor(color);
        g.drawRect(leftTop.getX(), leftTop.getY(), width, height);
    }
}
