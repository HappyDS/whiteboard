package demo;

import rmi.ClientImpl;
import rmi.IClient;
import rmi.IServer;
import rmi.ServerImpl;
import ui.BaseMainFrame;
import ui.UserMainFrame;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

/**
 * @author Yangzhe Xie
 * @date 21/9/19
 */
public class AdminMain {

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(9999);
            Registry serverRegistry = LocateRegistry.getRegistry("127.0.0.1", 9999);
            IServer server = new ServerImpl();
            serverRegistry.bind("Server", server);
            System.out.println("Server ready");

//            Registry clientRegistry = LocateRegistry.getRegistry("127.0.0.1", 8888);
//            IClient client = (IClient) clientRegistry.lookup("Client");
            startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startClient() {
        try {
            BaseMainFrame mainFrame = new UserMainFrame("Demo2");
            mainFrame.setResizable(false);
            mainFrame.setVisible(true);

            LocateRegistry.createRegistry(7777);
            Registry clientRegistry = LocateRegistry.getRegistry("127.0.0.1", 7777);
            IClient client = new ClientImpl(mainFrame);
            clientRegistry.bind("Client", client);

//            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Registry serverRegistry = LocateRegistry.getRegistry("127.0.0.1", 9999);
            IServer server = (IServer) serverRegistry.lookup("Server");
            server.addUser(new String[]{"Demo2", "127.0.0.1", "7777", "Client"});
            mainFrame.setServer(server);
            System.out.println("UserReady " + new Date().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
