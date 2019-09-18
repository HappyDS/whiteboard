package shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
public class FreeDraw implements IShape {

    private final int type = 5;
    private List<Point> points;
    private Color color;

    public void draw(Graphics g) {
        g.setColor(color);
        for (int i = 0; i < points.size() - 1; i++) {
            g.drawLine(points.get(i).getX(), points.get(i).getY(),
                    points.get(i + 1).getX(), points.get(i + 1).getY());
        }
    }

    public FreeDraw(List<Point> points, Color color) {
        this.points = points;
        this.color = color;
    }
}
