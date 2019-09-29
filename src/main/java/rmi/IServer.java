package rmi;

import data.AllData;
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

    void sendMessage(String message, String username) throws RemoteException;

    void undo(String username) throws RemoteException;

    void clear(String username) throws RemoteException;

    AllData addUser(String[] info) throws RemoteException;

    void reloadFromFile(List<IShape> shapes) throws RemoteException;

    void onClientClosed(String username) throws RemoteException;

    void closeServer() throws RemoteException;

    void removeUser(String username) throws RemoteException;
}
