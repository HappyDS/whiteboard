package ui;

import util.DateUtil;
import util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public ChatBoard() {
        setLayout(new BorderLayout());

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
                sb.append(DateUtil.getFormattedDate())
                        .append("\n")
                        .append(text)
                        .append("\n");
                msgBox.append(sb.toString());
                msgBox.append("\n");
            }
        });
    }

    /**
     * Other threads can use this message to append messages
     * @param msg
     */
    public synchronized void appendMessage(String msg) {
        msgBox.append(msg);
        msgBox.append("\n");
    }
}
