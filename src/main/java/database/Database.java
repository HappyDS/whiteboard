package database;

import java.sql.*;
import java.util.logging.*;

import config.Config;
import datatypes.DataTypes;

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


    public static void main(String[] args) {
        Database db = new Database();

    }
}
