package rmi;

import data.AllData;
import data.ChatMessage;
import shape.IShape;
import util.DateUtil;
import util.NumberUtil;
import util.StringUtil;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yangzhe Xie
 * @date 21/9/19
 */
public class ServerImpl extends UnicastRemoteObject implements IServer {
    private List<IClient> clientList = new ArrayList<>();
    private List<IShape> shapeList = new ArrayList<>();
    private List<ChatMessage> messageList = new ArrayList<>();
    private List<String> userList = new ArrayList<>();

    public ServerImpl() throws RemoteException {

    }

    @Override
    public void sendShape(IShape shape, String username) {
        shapeList.add(shape);
        List<IClient> disconnectedList = new ArrayList<>();
        for (IClient client : clientList) {
            try {
                if (!StringUtil.equals(client.getName(), username)) {
                    client.shapeFromServer(shape);
                }
            } catch (RemoteException e) {
                disconnectedList.add(client);
            }
        }
        onUserDisconnected(disconnectedList);
    }

    @Override
    public void sendMessage(String message, String username) {
        ChatMessage chatMessage = new ChatMessage(username, message, DateUtil.getFormattedDate());
        messageList.add(chatMessage);
        List<IClient> disconnectedList = new ArrayList<>();
        for (IClient client : clientList) {
            try {
                if (!StringUtil.equals(client.getName(), username)) {
                    client.messageFromServer(chatMessage);
                }
            } catch (RemoteException e) {
                disconnectedList.add(client);
            }
        }
        onUserDisconnected(disconnectedList);
    }

    @Override
    public void undo(String username) {
        List<IClient> disconnectedList = new ArrayList<>();
        shapeList.remove(shapeList.size() - 1);
        for (IClient client : clientList) {
            try {
                if (!StringUtil.equals(client.getName(), username)) {
                    client.undoFromServer();
                }
            } catch (RemoteException e) {
                disconnectedList.add(client);
            }
        }
        onUserDisconnected(disconnectedList);
    }

    @Override
    public void clear(String username) {
        List<IClient> disconnectedList = new ArrayList<>();
        shapeList.clear();
        for (IClient client : clientList) {
            try {
                if (!StringUtil.equals(client.getName(), username)) {
                    client.clearFromServer();
                }
            } catch (RemoteException e) {
                disconnectedList.add(client);
            }
        }
        onUserDisconnected(disconnectedList);
    }

    @Override
    public AllData addUser(String[] info) {
        AllData allData = new AllData();
        try {
            for (String s : info) {
                System.out.println(s);
            }
            String username = info[0];
            String host = info[1];
            int port = NumberUtil.convertToint(info[2], 1099);
            String serviceName = info[3];

            Registry clientRegistry = LocateRegistry.getRegistry(host, port);
            IClient client = (IClient) clientRegistry.lookup(serviceName);
            client.setName(username);
            clientList.add(client);
            userList.add(username);

            allData.setShapeList(shapeList);
            allData.setMessageList(messageList);
            allData.setUserList(userList);

            for (IClient c : clientList) {
                c.userListFromServer(userList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allData;
    }

    @Override
    public void reloadFromFile(List<IShape> shapes) {
        List<IClient> disconnectedList = new ArrayList<>();
        shapeList.clear();
        shapeList.addAll(shapes);
        for (IClient client : clientList) {
            try {
                client.reloadFromServer(shapeList);
            } catch (RemoteException e) {
                disconnectedList.add(client);
                e.printStackTrace();
            }
        }
        onUserDisconnected(disconnectedList);
    }

    @Override
    public void onClientClosed(String username) {
        userList.remove(username);
        IClient tmpClient = null;
        for (IClient client: clientList) {
            try {
                if (client.getName().equals(username)) {
                    tmpClient = client;
                } else {
                    client.userListFromServer(userList);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (tmpClient != null) {
            clientList.remove(tmpClient);
        }
    }

    @Override
    public void closeServer() {
        for (IClient client: clientList) {
            try {
                client.onServerClosed();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUser(String username) {
        try {
            IClient tmp = null;
            userList.remove(username);
            for (IClient client: clientList) {
                if (client.getName().equals(username)) {
                    tmp = client;
                } else {
                    client.userListFromServer(userList);
                }
            }
            if (tmp != null) {
                clientList.remove(tmp);
                tmp.onServerClosed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onUserDisconnected(List<IClient> disconnectedClients) {
        userList.clear();
        try {
            Iterator<IClient> it = clientList.iterator();
            while (it.hasNext()) {
                IClient client = it.next();
                if (!disconnectedClients.contains(client)) {
                    userList.add(client.getName());
                } else {
                    it.remove();
                }
            }

            for (IClient client : clientList) {
                client.userListFromServer(userList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
