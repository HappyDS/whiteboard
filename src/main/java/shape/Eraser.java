package shape;

import msg.IMessage;

import java.awt.*;
import java.util.List;

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
public class Eraser implements IShape, IMessage {
    private final int type = 7;
    private List<Point> points;
    private int size;

    public Eraser(List<Point> points, int size) {
        this.points = points;
        this.size = size;
    }

    public void draw(Graphics2D g) {
        Stroke strokeBackup = g.getStroke();
        Color colorBackup = g.getColor();

        BasicStroke bs = new BasicStroke(size, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL);
        g.setStroke(bs);
        g.setColor(Color.WHITE);
        for (int i = 0; i < points.size() - 1; i++) {
            g.drawLine(points.get(i).getX(), points.get(i).getY(),
                    points.get(i + 1).getX(), points.get(i + 1).getY());
        }

        g.setStroke(strokeBackup);
        g.setColor(colorBackup);
    }

    @Override
    public int getType() {
        return type;
    }
}
