package ui;

import util.NumberUtil;
import util.StringUtil;

import javax.swing.*;
import java.net.InetAddress;

/**
 * @author Yangzhe Xie
 * @date 24/9/19
 */
public class UserStarterFrame extends SizeFitter {

    private OnClientConnectClickListener onClientConnectClickListener;
    private JPanel panel;

    public UserStarterFrame() {
        super("Client Starter");
        fitSize(320, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        panel = new JPanel();
        add(panel);
        placeComponents();
    }

    private void placeComponents() {

        panel.setLayout(null);
        JLabel addressLabel = new JLabel("Server address: ");
        fitBounds(addressLabel, 25, 40, 100, 25);
//        addressLabel.setBounds(25, 40, 100, 25);
        panel.add(addressLabel);

        JTextField addressText = new JTextField(20);
        fitBounds(addressText, 125, 40, 165, 25);
//        addressText.setBounds(125, 40, 165, 25);
        panel.add(addressText);

        JLabel portLabel = new JLabel("Port: ");
        fitBounds(portLabel, 25, 80, 100, 25);
//        portLabel.setBounds(25, 80, 80, 25);
        panel.add(portLabel);

        JTextField portText = new JTextField(20);
        fitBounds(portText, 125, 80, 165, 25);
//        portText.setBounds(125, 80, 165, 25);
        panel.add(portText);

        JLabel usernameLabel = new JLabel("Username: ");
        fitBounds(usernameLabel, 25, 120, 100, 25);
//        usernameLabel.setBounds(25, 120, 80, 25);
        panel.add(usernameLabel);

        JTextField usernameText = new JTextField(20);
        fitBounds(usernameText, 125, 120, 165, 25);
//        usernameText.setBounds(125, 120, 165, 25);
        panel.add(usernameText);

        JLabel passwordLabel = new JLabel("Access key: ");
        fitBounds(passwordLabel, 25, 160, 100, 25);
//        usernameLabel.setBounds(25, 120, 80, 25);
        panel.add(passwordLabel);

        JTextField passwordText = new JTextField(20);
        fitBounds(passwordText, 125, 160, 165, 25);
//        usernameText.setBounds(125, 120, 165, 25);
        panel.add(passwordText);

        JButton cancelButton = new JButton("Exit");
        cancelButton.addActionListener(e -> {
            System.exit(0);
        });
        fitBounds(cancelButton, 25, 210, 100, 25);
//        cancelButton.setBounds(25, 170, 100, 25);
        panel.add(cancelButton);

        JButton connectButton = new JButton("Connect");
        fitBounds(connectButton, 185, 210, 100, 25);
//        connectButton.setBounds(185, 170, 100, 25);
        panel.add(connectButton);

        /* add click acton of connect button */
        connectButton.addActionListener(e -> {
            String ip = addressText.getText();
            String username = usernameText.getText();
            String password = passwordText.getText();
            int port = NumberUtil.convertToint(portText.getText(), -1);

            if (!StringUtil.isIP(ip)) {
                try {
                    ip = InetAddress.getByName(ip).getHostAddress();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel,
                            "Invalid host", "Message", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (port < 0 || port > 65535) {
                JOptionPane.showMessageDialog(panel,
                        "Please input a valid porn number (0 ~ 65535)", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (StringUtil.isEmpty(username)) {
                JOptionPane.showMessageDialog(panel,
                        "Please input a usernmae", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (onClientConnectClickListener != null) {
                onClientConnectClickListener.onClientConnectClick(ip, port, username, password);
            }
        });
    }

    public void setOnClientConnectClickListener(OnClientConnectClickListener onClientConnectClickListener) {
        this.onClientConnectClickListener = onClientConnectClickListener;
    }

    public interface OnClientConnectClickListener {
        void onClientConnectClick(String ip, Integer port, String username, String password);
    }
}
