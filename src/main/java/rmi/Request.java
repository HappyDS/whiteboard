package rmi;

import shape.IShape;

public class Request {
    ActionType actionType;
    IShape shape;
    int useId;
    String hashedPassword;
    long localTimestamp;
}


