package shape;

import java.awt.*;
import java.util.List;

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
public class Eraser implements IShape {
    private final int type = 6;
    private List<Point> points;
    private int size = 20;

    public void draw(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        Stroke strokeBackup = graphics2D.getStroke();
        Color colorBackup = graphics2D.getColor();

        BasicStroke bs = new BasicStroke(size, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL);
        graphics2D.setStroke(bs);
        graphics2D.setColor(Color.WHITE);
        for (int i = 0; i < points.size() - 1; i++) {
            graphics2D.drawLine(points.get(i).getX(), points.get(i).getY(),
                    points.get(i + 1).getX(), points.get(i + 1).getY());
        }

        graphics2D.setStroke(strokeBackup);
        graphics2D.setColor(colorBackup);
    }

    public Eraser(List<Point> points, int size) {
        this.points = points;
        this.size = size;
    }
}
