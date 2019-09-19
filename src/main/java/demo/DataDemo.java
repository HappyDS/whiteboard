package demo;

import com.google.gson.Gson;
import shape.Circle;
import shape.IShape;
import shape.Point;
import shape.Rectangle;
import util.MsgJsonFactory;

import java.awt.*;

/**
 * @author Yangzhe Xie
 * @date 19/9/19
 */
public class DataDemo {
    public static void main(String[] args) {
        IShape shape = new Rectangle(new Point(1, 2), 10, 10, Color.BLACK, 10);
        System.out.println(MsgJsonFactory.toJson(shape));
        
        String jsonData = "{\"type\":3,\"center\":{\"x\":365,\"y\":2},\"radius\":0,\"color\":{\"value\":-16777216,\"falpha\":0.0},\"size\":1}";
        Circle circle = (Circle) MsgJsonFactory.fromJson(jsonData);
        System.out.println(new Gson().toJson(circle));
    }
}
