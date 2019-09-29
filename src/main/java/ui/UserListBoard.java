package ui;

import rmi.IServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    public UserListBoard(String username) {
        this.username = username;
        setLayout(new BorderLayout(5, 5));
        add(new Label("User list: "), BorderLayout.NORTH);
        userList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(userList);

        add(scrollPane, BorderLayout.CENTER);
        add(new Label(), BorderLayout.SOUTH);
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
                //TODO: kick out
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
