package demo;

import ui.ServerFrame;
import ui.ServerMainFrame;

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
public class Main {
    public static void main(String[] args) {
        ServerMainFrame frame = new ServerMainFrame();
        frame.setResizable(false);

        frame.setVisible(true);
    }
}
