package ui;

import util.NumberUtil;
import util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

/**
 * @author Yangzhe Xie
 * @date 24/9/19
 */
public class AdminStarterFrame extends SizeFitter {

    private OnAdminConnectClickListener onAdminConnectClickListener;
    private JPanel panel;

    public AdminStarterFrame() {
        super("Admin Starter");

        fitSize(320, 240);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        panel = new JPanel();
        add(panel);
        placeComponents();
    }

    private void placeComponents() {

        panel.setLayout(null);
        JLabel usernameLabel = new JLabel("Username: ");
        fitBounds(usernameLabel, 25, 40, 150, 25);

        panel.add(usernameLabel);

        JTextField usernameText = new JTextField(20);
        fitBounds(usernameText, 125, 40, 165, 25);
        panel.add(usernameText);

        JLabel portLabel = new JLabel("Port: ");
        fitBounds(portLabel, 25, 80, 150, 25);
        panel.add(portLabel);

        JTextField portText = new JTextField(20);
        fitBounds(portText, 125, 80, 165, 25);
        panel.add(portText);

        JLabel passwordLabel = new JLabel("Password: ");
        fitBounds(passwordLabel, 25, 120, 150, 25);
        panel.add(passwordLabel);

        JTextField passwordText = new JTextField(20);
        fitBounds(passwordText, 125, 120, 165, 25);
        panel.add(passwordText);

        JButton cancelButton = new JButton("Exit");
        cancelButton.addActionListener(e -> {
            System.exit(0);
        });
        fitBounds(cancelButton, 25, 160, 120, 25);

        panel.add(cancelButton);

        JButton connectButton = new JButton("Start");
        fitBounds(connectButton, 185, 160, 120, 25);

        panel.add(connectButton);

        /* add click acton of connect button */
        connectButton.addActionListener(e -> {
            String username = usernameText.getText();
            int port = NumberUtil.convertToint(portText.getText(), -1);
            String password = passwordText.getText();

            if (StringUtil.isEmpty(username)) {
                JOptionPane.showMessageDialog(panel,
                        "Please input a usernmae", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (port < 0 || port > 65535) {
                JOptionPane.showMessageDialog(panel,
                        "Please input a valid porn number (0 ~ 65535)", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (onAdminConnectClickListener != null) {
                onAdminConnectClickListener.onAdminConnectClick(port, username, password);
            }
        });
    }

    public void setOnAdminConnectClickListener(OnAdminConnectClickListener onAdminConnectClickListener) {
        this.onAdminConnectClickListener = onAdminConnectClickListener;
    }

    public interface OnAdminConnectClickListener {
        void onAdminConnectClick(Integer port, String username, String password);
    }
}
