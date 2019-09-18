package datatypes;

class User {
    private int uid;
    private String username, hashedPassword, group, session, timestamp;

    public User(int uid, String username, String hashedPassword, String group, String session, String timestamp) {
        this.uid = uid;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.group = group;
        this.session = session;
        this.timestamp = timestamp;
    }
}

class Messages {
    private int idx, userUid;
    private String content, timestamp, username;

    Messages(int idx, int userUid, String content, String timestamp, String username) {
        this.idx = idx;
        this.content = content;
        this.timestamp = timestamp;
        this.userUid = userUid;
        this.username = username;
    }
}

class ImgBlob {
    private int imgUid;
    private String filename, blob, username;

}

public class DataTypes {

}