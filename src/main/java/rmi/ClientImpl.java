package rmi;

import data.ChatMessage;
import shape.IShape;
import ui.BaseMainFrame;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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
    public void undoFromServer() {
        userMainFrame.remoteUndo();
    }

    @Override
    public void clearFromServer() {
        userMainFrame.remoteClear();
    }

    @Override
    public void messageFromServer(ChatMessage message) {
        userMainFrame.addMessage(message);
    }

    @Override
    public void userListFromServer(List<String> userList) throws RemoteException {
        userMainFrame.setUserList(userList);
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
