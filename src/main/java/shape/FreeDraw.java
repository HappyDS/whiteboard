package shape;

import msg.IMessage;

import java.awt.*;
import java.util.List;

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
public class FreeDraw implements IShape, IMessage {

    private final int type = 5;
    private List<Point> points;
    private Color color;
    private int size;

    public FreeDraw(List<Point> points, Color color, int size) {
        this.points = points;
        this.color = color;
        this.size = size;
    }

    public void draw(Graphics2D g) {
        BasicStroke bs = new BasicStroke(size, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL);
        g.setStroke(bs);
        g.setColor(color);
        for (int i = 0; i < points.size() - 1; i++) {
            g.drawLine(points.get(i).getX(), points.get(i).getY(),
                    points.get(i + 1).getX(), points.get(i + 1).getY());
        }
    }

    @Override
    public int getType() {
        return type;
    }
}
