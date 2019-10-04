package config;

public interface types {
    class ImgBlob {
        public int idx, userUID;
        public String filename, blob, username;
        public long timestamp;

        public ImgBlob(int idx, int userUID, String filename, String blob, String username, long timestamp) {
            this.idx = idx;
            this.userUID = userUID;
            this.filename = filename;
            this.blob = blob;
            this.username = username;
            this.timestamp = timestamp;
        }
    }

    class Message {
        public int idx, userUid;
        public String content, username;
        public long timestamp;

        public Message(int idx, int userUid, String content, long timestamp, String username) {
            this.idx = idx;
            this.content = content;
            this.timestamp = timestamp;
            this.userUid = userUid;
            this.username = username;
        }

        public Message() {

        }


    }

    class User {
        public int uid, group;
        public long timestamp;
        public String username, hashedPassword, session;

        public User(int uid, String username, String hashedPassword, int group, String session, long timestamp) {
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
