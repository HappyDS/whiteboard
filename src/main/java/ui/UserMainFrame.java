package ui;

import demo.AdminMain;
import demo.UserMain;

import javax.swing.*;

/**
 * @author Yangzhe Xie
 * @date 20/9/19
 */
public class UserMainFrame extends BaseMainFrame {
    public UserMainFrame(String username) {
        super(username);
    }

    @Override
    public void initMenuBar() {
        //TODO
    }

    @Override
    protected void onWindowClosing() {
        //TODO
    }

    @Override
    public void onServerDisconnected() {
        int res = JOptionPane.showConfirmDialog(this,
                "Server closed", "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        if (res == 0) {
            dispose();
            UserMain.main(new String[]{});
        }
    }
}
