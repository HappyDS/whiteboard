package demo;

import rmi.ClientImpl;
import rmi.IClient;
import rmi.IServer;
import shape.IShape;
import ui.BaseMainFrame;
import ui.UserMainFrame;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;

/**
 * @author Yangzhe Xie
 * @date 21/9/19
 */
public class UserMain {
    public static void main(String[] args) {
        try {
            BaseMainFrame mainFrame = new UserMainFrame("Demo1");
            mainFrame.setResizable(false);
            mainFrame.setVisible(true);

            LocateRegistry.createRegistry(8888);
            Registry clientRegistry = LocateRegistry.getRegistry("127.0.0.1", 8888);
            IClient client = new ClientImpl(mainFrame);
            clientRegistry.bind("Client", client);

//            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Registry serverRegistry = LocateRegistry.getRegistry("127.0.0.1", 9999);
            IServer server = (IServer) serverRegistry.lookup("Server");
            List<IShape> shapeList = server.addUser(new String[]{"Demo", "127.0.0.1", "8888", "Client"});
            mainFrame.setServer(server);
            mainFrame.initShapes(shapeList);
            System.out.println("UserReady " + new Date().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
