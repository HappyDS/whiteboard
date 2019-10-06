package ui;

import rmi.IServer;
import util.Looper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Yangzhe Xie
 * @date 21/9/19
 */
public class UserListBoard extends JPanel {

    private String username;
    private IServer server;
    private JList<String> userList;
    private String[] usernameList;
    private Looper looper;

    public UserListBoard(String username, Looper looper) {
        this.username = username;
        setLayout(new BorderLayout(5, 5));
        add(new Label("User list: "), BorderLayout.NORTH);
        userList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(userList);

        add(scrollPane, BorderLayout.CENTER);
        add(new Label(), BorderLayout.SOUTH);
        this.looper = looper;
    }

    public void initKickOutOption() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("disconnect");
        popupMenu.add(deleteItem);
        deleteItem.addActionListener(e -> {
            int res = JOptionPane.showConfirmDialog(null,
                    "Are you sure to kick out this user? ",
                    "Message",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (res == 0) {
                String toKickOut = usernameList[userList.getSelectedIndex()];
                looper.post(() -> {
                    try {
                        server.removeUser(toKickOut);
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        });

        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (e.getButton() == 3 && usernameList.length > 1
                            && !usernameList[userList.getSelectedIndex()]
                            .replace(" (me)", "").equals(username)) {
                        popupMenu.show(userList, e.getX(), e.getY());
                    }
                } catch (Exception ex) {

                }
            }
        });
    }

    public void setServer(IServer server) {
        this.server = server;
    }

    public synchronized void setUserList(List<String> list) {
        usernameList = list.toArray(new String[list.size()]);
        for (int i = 0; i < list.size(); i++) {
            if (usernameList[i].equals(username)) {
                usernameList[i] = username + " (me)";
            }
        }
        userList.setListData(usernameList);
    }
}
