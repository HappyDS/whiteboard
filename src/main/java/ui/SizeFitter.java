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
        fitFont(l);
    }

    //https://stackoverflow.com/questions/2715118/how-to-change-the-size-of-the-font-of-a-jlabel-to-take-the-maximum-size
    public void fitFont(JComponent label) {
        try {
            Font labelFont = label.getFont();
            String labelText = "label.getText()";
            int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
            int componentWidth = label.getWidth();

            // Find out how much the font can grow in width.
            double widthRatio = (double) componentWidth / (double) stringWidth;

            int newFontSize = (int) (labelFont.getSize() * widthRatio);
            int componentHeight = label.getHeight();

            // Pick a new font size so it will not be larger than the height of label.
            int fontSizeToUse = Math.min(newFontSize, componentHeight);

            // Set the label's font size to the newly determined size.
            label.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
        } catch (Exception e) {

        }

    }
}
