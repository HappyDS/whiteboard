package rmi;

import data.ChatMessage;
import shape.IShape;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IClient extends Remote {
    void shapeFromServer(IShape shape) throws RemoteException;

    void undoFromServer() throws RemoteException;

    void clearFromServer() throws RemoteException;

    void reloadFromServer(List<IShape> shapes) throws RemoteException;

    void messageFromServer(ChatMessage message) throws RemoteException;

    void userListFromServer(List<String> userList) throws RemoteException;

    String getName() throws RemoteException;

    void setName(String name) throws RemoteException;

    void onServerClosed() throws RemoteException;
}
