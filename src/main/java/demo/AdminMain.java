package demo;

import rmi.ClientImpl;
import rmi.IClient;
import rmi.IServer;
import rmi.ServerImpl;
import ui.AdminMainFrame;
import ui.AdminStarterFrame;
import ui.BaseMainFrame;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

/**
 * @author Yangzhe Xie
 * @date 21/9/19
 */
public class AdminMain {

    public static void main(String[] args) {
        AdminStarterFrame starterFrame = new AdminStarterFrame();
        starterFrame.setVisible(true);

        starterFrame.setOnAdminConnectClickListener((port, username) -> {
            try {
//                InetAddress inetAddress = InetAddress.getLocalHost();
                LocateRegistry.createRegistry(port);
                Registry serverRegistry = LocateRegistry.getRegistry("127.0.0.1", port);
                IServer server = new ServerImpl();
                serverRegistry.bind("Server", server);
                System.out.println("Server ready");
                startClient(port, username);
                starterFrame.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void startClient(int remotePort, String username) {
        try {
            BaseMainFrame mainFrame = new AdminMainFrame(username);
            mainFrame.setResizable(false);

            ServerSocket s = new ServerSocket(0);
            int localPort = s.getLocalPort();
            s.close();

            LocateRegistry.createRegistry(localPort);
            Registry clientRegistry = LocateRegistry.getRegistry("127.0.0.1", localPort);

            IClient client = new ClientImpl(mainFrame);
            clientRegistry.bind("Client", client);

            Registry serverRegistry = LocateRegistry.getRegistry("127.0.0.1", remotePort);
            IServer server = (IServer) serverRegistry.lookup("Server");
            server.addUser(new String[]{username, "127.0.0.1", String.valueOf(localPort), "Client"});
            mainFrame.setServer(server);
            System.out.println("UserReady " + new Date().getTime());

            mainFrame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
