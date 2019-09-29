package ui;

import demo.UserMain;

import javax.swing.*;
import java.rmi.RemoteException;

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
        try {
            server.onClientClosed(username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
