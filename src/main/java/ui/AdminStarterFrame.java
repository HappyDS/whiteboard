package ui;

import util.NumberUtil;
import util.StringUtil;

import javax.swing.*;
import java.net.InetAddress;

/**
 * @author Yangzhe Xie
 * @date 24/9/19
 */
public class AdminStarterFrame extends JFrame {

    private OnAdminConnectClickListener onAdminConnectClickListener;
    private JPanel panel;

    public AdminStarterFrame() {
        super("Admin Starter");
        setSize(320, 200);
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

        usernameLabel.setBounds(25, 40, 100, 25);
        panel.add(usernameLabel);

        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(125, 40, 165, 25);
        panel.add(usernameText);

        JLabel portLabel = new JLabel("Port: ");
        portLabel.setBounds(25, 80, 80, 25);
        panel.add(portLabel);

        JTextField portText = new JTextField(20);
        portText.setBounds(125, 80, 165, 25);
        panel.add(portText);

        JButton cancelButton = new JButton("Exit");
        cancelButton.addActionListener(e -> {
            System.exit(0);
        });
        cancelButton.setBounds(25, 120, 100, 25);
        panel.add(cancelButton);

        JButton connectButton = new JButton("Connect");
        connectButton.setBounds(185, 120, 100, 25);
        panel.add(connectButton);

        /* add click acton of connect button */
        connectButton.addActionListener(e -> {
            String username = usernameText.getText();
            int port = NumberUtil.convertToint(portText.getText(), -1);

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
                onAdminConnectClickListener.onAdminConnectClick(port, username);
            }
        });
    }

    public void setOnAdminConnectClickListener(OnAdminConnectClickListener onAdminConnectClickListener) {
        this.onAdminConnectClickListener = onAdminConnectClickListener;
    }

    public interface OnAdminConnectClickListener {
        void onAdminConnectClick(Integer port, String username);
    }
}
