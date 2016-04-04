package com.sasha.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Саша on 30.11.2014.
 */
public class ConnectionManager {
    private static Connection connection;

    public static void createConnection(String url) throws SQLException {
        connection = DriverManager.getConnection(url);
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
