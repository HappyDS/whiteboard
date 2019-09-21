package database;

import config.Config;
import data.types;
import server.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Database {
    private final static Logger logger = Logger.getLogger("Database");
    private final static Config config = new Config();
    //connect immediately when new Database
    private Connection conn;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock readLock = lock.readLock();
    Lock writeLock = lock.writeLock();
    private ReadWriteLock write = new ReentrantReadWriteLock();

    Database(DBType DBType) {
        this.conn = connect(DBType);
    }

    public static void main(String[] args) {

//        Database db = new Database(DBType.CLIENT);
//        db.getUser("admin");
//        db.updateSession("admin");
//        db.getMessages(2);
//        System.out.println();

    }

    private Connection connect(DBType DBType) {
        logger.setLevel(Level.INFO);
        Connection conn = null;
        try {
            String url;
            switch (DBType) {
                case CLIENT:
                    url = String.format("jdbc:sqlite:%s", config.databaseClientPath);
                    break;
                case SERVER:
                    url = String.format("jdbc:sqlite:%s", config.databaseServerPath);
                    break;
                default:
                    throw new SQLException();

            }
            conn = DriverManager.getConnection(url);
            logger.info("Connection to SQLite has been established.");
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            System.exit(0);
        }
        return conn;
    }

    private void closeConn(Connection conn) throws SQLException {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            throw e;
        }
        logger.info("[*] connection closed");
    }

    public List<types.User> getUser(String username) {
        List<types.User> userList = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE username=?;";
        ResultSet res;
        try {
            PreparedStatement pStmt = this.conn.prepareStatement(sql);
            pStmt.setString(1, username);
            this.readLock.lock();
            res = pStmt.executeQuery();
            this.readLock.unlock();
            while (res.next()) {
                types.User user = new types.User();
                user.uid = res.getInt("uid");
                user.username = res.getString("username");
                user.group = res.getInt("group");
                user.hashedPassword = res.getString("hashedPassword");
                user.session = res.getString("session");
                user.timestamp = res.getInt("timestamp");
                userList.add(user);
            }
        } catch (SQLException e) {
            logger.warning(String.format("[*] getUser failed: %s", e.getMessage()));
        }
        return userList;

    }

    public void updateSession(String username) {
        String sql = "UPDATE user SET session=?,timestamp=? WHERE username=?;";
        try {
            PreparedStatement pStmt = this.conn.prepareStatement(sql);

            Session session = new Session();

            pStmt.setString(1, session.session);
            pStmt.setLong(2, session.timestamp);
            pStmt.setString(3, username);

            this.writeLock.lock();

            pStmt.executeUpdate();
            this.writeLock.unlock();


        } catch (SQLException e) {
            logger.warning(String.format("[*] updateSession failed: %s", e.getMessage()));
        }
    }

    public List<types.Message> getMessages(int countLimited) {
//        String sql = "SELECT * FROM messages LIMIT ? ORDER BY timestamp ;";
        String sql = "SELECT * FROM messages LIMIT ?;";
        List<types.Message> msgList = new ArrayList<>();
        ResultSet res;
        try {
            PreparedStatement pStmt = this.conn.prepareStatement(sql);
            Date date = new Date();
            pStmt.setLong(1, countLimited);
            res = pStmt.executeQuery();
            while (res.next()) {
                types.Message msg = new types.Message();

                msg.idx = res.getInt("idx");
                msg.content = res.getString("content");
                msg.userUid = res.getInt("userUid");
                msg.username = res.getString("username");
                msg.timestamp = res.getLong("timestamp");
                msgList.add(msg);
            }

        } catch (SQLException e) {
            logger.warning(String.format("[*] getMessages failed: %s", e.getMessage()));
        }
        return msgList;
    }
}
