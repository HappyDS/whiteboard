package rmi;

import shape.IShape;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    void shapeFromServer(IShape shape) throws RemoteException;;
}
