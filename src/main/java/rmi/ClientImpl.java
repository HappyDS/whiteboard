package rmi;

import shape.IShape;
import ui.UserMainFrame;

/**
 * @author Yangzhe Xie
 * @date 20/9/19
 */
public class ClientImpl implements IClient {

    private UserMainFrame userMainFrame;

    public ClientImpl(UserMainFrame userMainFrame) {
        this.userMainFrame = userMainFrame;
    }

    @Override
    public void shapeFromServer(IShape shape) {
        userMainFrame.addShape(shape);
    }
}
