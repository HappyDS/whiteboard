package demo;

import rmi.IServer;
import rmi.ServerImpl;
import ui.AdminMainFrame;
import ui.BaseMainFrame;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Yangzhe Xie
 * @date 21/9/19
 */
public class ServerMain {

    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            BaseMainFrame mainFrame = new AdminMainFrame();
            mainFrame.setResizable(false);
            mainFrame.setVisible(true);
            IServer server = new ServerImpl(mainFrame);
            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Server", server);
            System.out.println("Server ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
