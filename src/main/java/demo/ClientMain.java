package demo;

import rmi.IServer;
import shape.Circle;
import shape.IShape;
import shape.Point;
import shape.Text;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

/**
 * @author Yangzhe Xie
 * @date 21/9/19
 */
public class ClientMain {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Registry registry = LocateRegistry.getRegistry("127.0.0.1");

            IServer server = (IServer) registry.lookup("Server");
            System.out.println("Ready " + new Date().getTime());

//            for (int i = 0; i < 10; i++) {
////                server.sendShape(new Circle(new Point(100 + 10 * i, 100), 50, Color.BLACK, 5));
////                Thread.sleep(3000);
////            }
            JFrame frame = new JFrame();
            frame.setSize(100, 100);
            JButton button = new JButton("TEST");
            frame.add(button);
            final int[] cnt = {0};
            frame.setVisible(true);
            button.addActionListener(e -> {
                Point point = new Point(cnt[0], cnt[0]);
                IShape text = new Text(point, "" + new Date().getTime(), 14, Color.BLACK);
                try {
                    server.sendShape(text);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
                cnt[0]+= 10;
            });
            System.out.println("Finished " + new Date().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
