package ui;

import javax.swing.*;
import java.awt.*;


public class SizeFitter extends JFrame {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Dimension defaultSize = new Dimension(1680, 1050);

    //    public static int W(double v) {
//    }
    SizeFitter(String title) {
        super(title);
    }

    private int H(int height) {
        return (height * screenSize.height / defaultSize.height);
    }

    private int W(int width) {
        return (width * screenSize.width / defaultSize.width);
    }

    public void fitSize(int w, int h) {
        setSize(W(w), H(h));
    }

    public void fitBounds(JComponent l, int x, int y, int w, int h) {
        l.setBounds(W(x), H(y), W(w), H(h));
    }
//    public int X(float x) {
//        return x / defaultSize.width * screenSize.width;
//    }
//
//    public int Y(float y) {
//        return y / defaultSize.width * screenSize.width;
//    }
}
