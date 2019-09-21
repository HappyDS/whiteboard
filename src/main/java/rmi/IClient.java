package rmi;

import shape.IShape;
import data.ChatMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    void shapeFromServer(IShape shape) throws RemoteException;

    void messageFromServer(ChatMessage message) throws RemoteException;

    String getName() throws RemoteException;

    void setName(String name) throws RemoteException;
}
