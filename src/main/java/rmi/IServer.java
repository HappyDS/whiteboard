package rmi;

import shape.IShape;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Yangzhe Xie
 * @date 20/9/19
 */
public interface IServer extends Remote {
    void sendShape(IShape shape) throws RemoteException;;
}
