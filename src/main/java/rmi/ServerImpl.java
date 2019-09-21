package rmi;

import shape.IShape;
import ui.BaseMainFrame;
import ui.PaintBoard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Yangzhe Xie
 * @date 21/9/19
 */
public class ServerImpl extends UnicastRemoteObject implements IServer {
    private BaseMainFrame baseMainFrame;

    public ServerImpl(BaseMainFrame baseMainFrame) throws RemoteException {
        this.baseMainFrame = baseMainFrame;
    }

    @Override
    public void sendShape(IShape shape) {
        baseMainFrame.addShape(shape);
    }
}
