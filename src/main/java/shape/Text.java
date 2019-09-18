package shape;

import java.awt.*;

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
public class Text implements IShape {

    private final int type = 6;
    private Point position;
    private String string;
    private int size;
    private Color color;

    public Text(Point position, String string, int size, Color color) {
        this.position = position;
        this.string = string;
        this.size = size;
        this.color = color;
    }

    public void draw(Graphics2D g) {
        if (string != null) {
            Font font = new Font(Font.SERIF, Font.PLAIN, size);
            g.setFont(font);
            g.setColor(color);
            g.drawString(string, position.getX(), position.getY());
        }
    }
}
