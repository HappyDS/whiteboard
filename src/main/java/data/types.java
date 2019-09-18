package data;

public interface types {
    class ImgBlob {
        private int idx, userUID;
        private String filename, blob, username;

        public ImgBlob(int idx, int userUID, String filename, String blob, String username) {
            this.idx = idx;
            this.userUID = userUID;
            this.filename = filename;
            this.blob = blob;
            this.username = username;
        }
    }

    class Message {
        private int idx, userUid;
        private String content, timestamp, username;

        Message(int idx, int userUid, String content, String timestamp, String username) {
            this.idx = idx;
            this.content = content;
            this.timestamp = timestamp;
            this.userUid = userUid;
            this.username = username;
        }
    }

    class User {
        public int uid, group, timestamp;
        public String username, hashedPassword, session;

        public User(int uid, String username, String hashedPassword, int group, String session, int timestamp) {
            this.uid = uid;
            this.username = username;
            this.hashedPassword = hashedPassword;
            this.group = group;
            this.session = session;
            this.timestamp = timestamp;
        }

        public User() {

        }
    }
}
