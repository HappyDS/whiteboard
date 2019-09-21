package rmi;

import shape.IShape;
import ui.BaseMainFrame;
import data.ChatMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Yangzhe Xie
 * @date 20/9/19
 */
public class ClientImpl extends UnicastRemoteObject implements IClient {

    private BaseMainFrame userMainFrame;
    private String name;

    public ClientImpl(BaseMainFrame userMainFrame) throws RemoteException {
        this.userMainFrame = userMainFrame;
    }

    @Override
    public void shapeFromServer(IShape shape) {
        userMainFrame.addShape(shape);
    }

    @Override
    public void messageFromServer(ChatMessage message) throws RemoteException {
        userMainFrame.addMessage(message);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
