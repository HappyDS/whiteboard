package rmi;

import com.google.gson.Gson;
import shape.IShape;
import ui.BaseMainFrame;
import util.NumberUtil;
import util.StringUtil;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yangzhe Xie
 * @date 21/9/19
 */
public class ServerImpl extends UnicastRemoteObject implements IServer {
    private List<IClient> clientList = new ArrayList<>();
    private List<IShape> shapeList = new ArrayList<>();

    public ServerImpl() throws RemoteException {

    }

    @Override
    public void sendShape(IShape shape, String username) {
//        baseMainFrame.addShape(shape);
        System.out.println(new Gson().toJson(shape));
        shapeList.add(shape);
        for (IClient client : clientList) {
            try {
                if (!StringUtil.equals(client.getName(), username)) {
                    try {
                        client.shapeFromServer(shape);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<IShape> addUser(String[] info) {
        try {
            for (String s : info) {
                System.out.println(s);
            }
            String username = info[0];
            String host = info[1];
            int port = NumberUtil.convertToint(info[2], 1099);
            String serviceName = info[3];

            Registry clientRegistry = LocateRegistry.getRegistry(host, port);
            IClient client = (IClient) clientRegistry.lookup(serviceName);
            client.setName(username);
            clientList.add(client);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return shapeList;
    }
}
