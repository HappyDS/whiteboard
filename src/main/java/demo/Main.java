package demo;

import ui.AdminMainFrame;
import ui.BaseMainFrame;

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
public class Main {
    public static void main(String[] args) {
        BaseMainFrame frame = new AdminMainFrame("Admin");
        frame.setResizable(false);

        frame.setVisible(true);
    }
}
