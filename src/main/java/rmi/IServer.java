package rmi;

import shape.IShape;

import java.rmi.Remote;

/**
 * @author Yangzhe Xie
 * @date 20/9/19
 */
public interface IServer extends Remote {
    void sendShape(IShape shape);
}
