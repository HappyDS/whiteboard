package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

import com.google.gson.Gson;
import config.Config;
import data.types;

public class Database {
    //connect immediately when new Database
    private Connection conn = connect();
    private final static Logger logger = Logger.getLogger("Database");
    private final static Config config = new Config();


    private Connection connect() {
        logger.setLevel(Level.SEVERE);
        Connection conn = null;
        try {
            String url = String.format("jdbc:sqlite:%s", config.databasePath);
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
        try (
                PreparedStatement pStmt = this.conn.prepareStatement(sql)) {
            pStmt.setString(1, username);
            res = pStmt.executeQuery();
            while (res.next()) {
//                // JSONObject actually put address in, so need new word every time
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


    public static void main(String[] args) {
//        Database db = new Database();
//        db.getUser("admin");
//        System.out.println();

    }
}
