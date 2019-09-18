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

    public Rectangle(Point leftTop, int width, int height, Color color) {
        this.leftTop = leftTop;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(leftTop.getX(), leftTop.getY(), width, height);
    }
}
