package com.sasha.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Саша on 09.12.2014.
 */
public class Subject {
    Subject(int _id, String _name) {
        id = _id;
        name = _name;
    }

    private int id;
    private String name;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static ArrayList<Subject> getAllSubjects() {
        Connection connection = ConnectionManager.getConnection();
        Statement stmt = null;
        String stmtString = "";
        try {
            stmt = connection.createStatement();
            stmtString = "SELECT * FROM Subject;";
            ResultSet rs = stmt.executeQuery(stmtString);
            ArrayList<Subject> subjects = new ArrayList<Subject>();
            while (rs.next() && rs != null) {
                subjects.add(new Subject(rs.getInt("subject_id"), rs.getString("name")));
            }
            return subjects;
        } catch (SQLException e) {
            System.out.println(stmtString);
            e.printStackTrace();
        }
        return null;
    }

}
