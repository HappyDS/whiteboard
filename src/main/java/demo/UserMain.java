package demo;

import data.AllData;
import rmi.ClientImpl;
import rmi.IClient;
import rmi.IServer;
import ui.BaseMainFrame;
import ui.UserMainFrame;

import java.net.ServerSocket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

/**
 * @author Yangzhe Xie
 * @date 21/9/19
 */
public class UserMain {
    public static void main(String[] args) {
        try {
            BaseMainFrame mainFrame = new UserMainFrame("Demo1");
            mainFrame.setResizable(false);

            ServerSocket s = new ServerSocket(0);
            int localPort = s.getLocalPort();
            s.close();

            LocateRegistry.createRegistry(localPort);
            Registry clientRegistry = LocateRegistry.getRegistry("127.0.0.1", localPort);
            IClient client = new ClientImpl(mainFrame);
            clientRegistry.bind("Client", client);

            Registry serverRegistry = LocateRegistry.getRegistry("127.0.0.1", 9999);
            IServer server = (IServer) serverRegistry.lookup("Server");
            AllData allData = server.addUser(new String[]{"Demo1", "127.0.0.1", String.valueOf(localPort), "Client"});
            mainFrame.setServer(server);
            mainFrame.initShapes(allData.getShapeList());
            mainFrame.initMessages(allData.getMessageList());
            System.out.println("UserReady " + new Date().getTime());

            mainFrame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
