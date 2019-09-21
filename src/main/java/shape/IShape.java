package shape;

import java.awt.*;
import java.io.Serializable;

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
public interface IShape extends Serializable {
    void draw(Graphics2D g);
}
