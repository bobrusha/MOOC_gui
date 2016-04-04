package com.sasha.db;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class GUI {

    static final String PROGRAM_NAME = "MOOC Tacker";

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://%s/%s?user=%s&password=%s";

    static JFrame frame;
    static UserInformation userinfo;

    public static void main(String args[]) {
        String address = "localhost";
        String db = "online_courses";
        String user = "root";
        String password = "789056";
        Connection connection = null;

        try {
            Class.forName(JDBC_DRIVER);

            ConnectionManager.createConnection(String.format(DB_URL, address, db, user, password));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                frame = new FrameStart();


            }
        });
    }
}