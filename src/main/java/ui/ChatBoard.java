package ui;

import rmi.IServer;
import util.DateUtil;
import util.Looper;
import util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

/**
 * @author Yangzhe Xie
 * @date 19/9/19
 */
public class ChatBoard extends JPanel {

    private JTextArea msgBox;
    private JScrollPane msgScrollPane;
    private JTextField inputBox;
    private JPanel optionPanel;
    private JButton sendButton;
    private String username;
    private IServer server;

    public ChatBoard(String username, Looper looper) {

        this.username = username;
        setLayout(new BorderLayout(5, 5));

        msgBox = new JTextArea();
        inputBox = new JTextField();
        optionPanel = new JPanel();
        sendButton = new JButton("Send");
        msgScrollPane = new JScrollPane(msgBox);

        msgBox.setEditable(false);
        msgBox.setLineWrap(true);
        msgBox.setMargin(new Insets(5, 5, 5, 5));

        optionPanel.setLayout(new BorderLayout());
        optionPanel.add(inputBox, BorderLayout.CENTER);
        optionPanel.add(sendButton, BorderLayout.EAST);

        add(msgScrollPane, BorderLayout.CENTER);
        add(optionPanel, BorderLayout.SOUTH);
        add(new JLabel("let's chat!"), BorderLayout.NORTH);

        sendButton.addActionListener(e -> {
            String text = inputBox.getText();
            if (!StringUtil.isEmpty(text)) {
                StringBuilder sb = new StringBuilder();
                sb.append(username)
                        .append("\n")
                        .append(DateUtil.getFormattedDate())
                        .append("\n")
                        .append(text)
                        .append("\n");
                msgBox.append(sb.toString());
                msgBox.append("\n");
                looper.post(() -> {
                    try {
                        server.sendMessage(text, username);
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        });
    }

    /**
     * Other threads can use this message to append messages
     *
     * @param msg
     */
    public synchronized void appendMessage(String msg) {
        msgBox.append(msg);
        msgBox.append("\n");
    }

    public void setServer(IServer server) {
        this.server = server;
    }
}
