package rmi;

import shape.IShape;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Yangzhe Xie
 * @date 20/9/19
 */
public interface IServer extends Remote {
    void sendShape(IShape shape, String username) throws RemoteException;

    List<IShape> addUser(String[] info) throws RemoteException;
}
