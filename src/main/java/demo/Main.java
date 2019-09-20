package demo;

import ui.AdminMainFrame;
import ui.BaseMainFrame;
import ui.UserMainFrame;

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
public class Main {
    public static void main(String[] args) {
        BaseMainFrame frame = new AdminMainFrame();
        frame.setResizable(false);

        frame.setVisible(true);
    }
}
