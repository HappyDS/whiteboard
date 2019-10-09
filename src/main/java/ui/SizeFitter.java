package ui;

import javax.swing.*;
import java.awt.*;


public class SizeFitter extends JFrame {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Dimension defaultSize = new Dimension(2800, 1800);

    //    public static int W(double v) {
//    }
    SizeFitter(String title) {
        super(title);
    }

    private int H(float height) {
        return (int) (height * screenSize.height);
    }

    private int W(float width) {
        return (int) (width * screenSize.width);
    }

    public void fitSize(float w, float h) {
        setSize(W(w), H(h));
    }
//    public int X(float x) {
//        return x / defaultSize.width * screenSize.width;
//    }
//
//    public int Y(float y) {
//        return y / defaultSize.width * screenSize.width;
//    }
}
