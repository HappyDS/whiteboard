package rmi;

import shape.IShape;

import java.rmi.Remote;

public interface IClient extends Remote {
    void shapeFromServer(IShape shape);
}
