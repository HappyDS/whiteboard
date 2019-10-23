package rmi;

import config.Config;
import data.AllData;
import data.ChatMessage;
import shape.IShape;
import util.DateUtil;
import util.MD5Util;
import util.NumberUtil;
import util.StringUtil;

import javax.swing.*;
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
    private String password;

    public ServerImpl(String password) throws RemoteException {
        this.password = password;
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
            if (!Config.SERVER_ALLOW_CONNECTION) {
                allData.setCode(-1);
                allData.setMsg("Connection refused");
                return allData;
            }

            String username = info[0];
            String host = info[1];
            if (!StringUtil.equals(host, "127.0.0.1")) {
                host = getClientHost();
            }
            int port = NumberUtil.convertToint(info[2], 1099);
            String serviceName = info[3];
            String key = info[4];

            System.out.println("client: " + host);
            System.out.println("Port: " + port);

            /* Username exists */
            if (userList.contains(username)) {
                allData.setCode(-1);
                allData.setMsg("Username exists, please try another one");
                return allData;
            }

            if (!StringUtil.equals(MD5Util.md5(username + password), key)) {
                allData.setCode(-1);
                allData.setMsg("Wrong Board Pass!");
                return allData;
            }

            if (!host.equals("127.0.0.1") && Config.ENABLE_CONFIRM) {
                String[] options = {"Approve", "Reject"};
                int x = JOptionPane.showOptionDialog(null, username + " wants to connect to the whiteboard",
                        "Message",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                if (x != 0) {
                    allData.setCode(-1);
                    allData.setMsg("Connection refused by manager");
                    return allData;
                }
            }

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
        for (IClient client : clientList) {
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
        for (IClient client : clientList) {
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
            for (IClient client : clientList) {
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
