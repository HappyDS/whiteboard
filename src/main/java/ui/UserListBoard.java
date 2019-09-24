package ui;

import rmi.IServer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Yangzhe Xie
 * @date 21/9/19
 */
public class UserListBoard extends JPanel {

    private String username;
    private IServer server;
    private JList<String> userList;

    public UserListBoard(String username) {
        this.username = username;
        setLayout(new BorderLayout(5, 5));
        add(new Label("User list: "), BorderLayout.NORTH);
        userList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(userList);

        add(scrollPane, BorderLayout.CENTER);
        add(new Label(), BorderLayout.SOUTH);
    }

    public void setServer(IServer server) {
        this.server = server;
    }

    public synchronized void setUserList(List<String> list) {
        String[] strings = list.toArray(new String[list.size()]);
        for (int i = 0; i < list.size(); i++) {
            if (strings[i].equals(username)) {
                System.out.println(username);
                strings[i] = username + " (me)";
            }
        }
        userList.setListData(strings);
    }
}
