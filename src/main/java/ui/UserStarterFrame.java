package ui;

import util.NumberUtil;
import util.StringUtil;

import javax.swing.*;
import java.net.InetAddress;

/**
 * @author Yangzhe Xie
 * @date 24/9/19
 */
public class UserStarterFrame extends JFrame {

    private OnClientConnectClickListener onClientConnectClickListener;
    private JPanel panel;

    public UserStarterFrame() {
        super("Client Starter");
        setSize(320, 240);
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

        addressLabel.setBounds(25, 40, 100, 25);
        panel.add(addressLabel);

        JTextField addressText = new JTextField(20);
        addressText.setBounds(125, 40, 165, 25);
        panel.add(addressText);

        JLabel portLabel = new JLabel("Port: ");
        portLabel.setBounds(25, 80, 80, 25);
        panel.add(portLabel);

        JTextField portText = new JTextField(20);
        portText.setBounds(125, 80, 165, 25);
        panel.add(portText);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(25, 120, 80, 25);
        panel.add(usernameLabel);

        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(125, 120, 165, 25);
        panel.add(usernameText);

        JButton cancelButton = new JButton("Exit");
        cancelButton.addActionListener(e -> {
            System.exit(0);
        });
        cancelButton.setBounds(25, 170, 100, 25);
        panel.add(cancelButton);

        JButton connectButton = new JButton("Connect");
        connectButton.setBounds(185, 170, 100, 25);
        panel.add(connectButton);

        /* add click acton of connect button */
        connectButton.addActionListener(e -> {
            String ip = addressText.getText();
            String username = usernameText.getText();
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
                //TODO username may exist
                JOptionPane.showMessageDialog(panel,
                        "Please input a usernmae", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (onClientConnectClickListener != null) {
                onClientConnectClickListener.onClientConnectClick(ip, port, username);
            }
        });
    }

    public void setOnClientConnectClickListener(OnClientConnectClickListener onClientConnectClickListener) {
        this.onClientConnectClickListener = onClientConnectClickListener;
    }

    public interface OnClientConnectClickListener {
        void onClientConnectClick(String ip, Integer port, String username);
    }
}
