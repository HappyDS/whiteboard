package shape;

import java.awt.*;

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
public class Text implements IShape {

    private Point position;
    private String string;
    private Font font;


    public Text(Point position, String string, Font font) {
        this.position = position;
        this.string = string;
        this.font = font;
    }

    public void draw(Graphics g) {
        g.setFont(font);
        g.drawString(string, position.getX(), position.getY());
    }
}
