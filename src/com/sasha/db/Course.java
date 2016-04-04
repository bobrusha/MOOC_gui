package com.sasha.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Саша on 08.12.2014.
 */
public class Course {
    public Course(int _id, String _name) {
        id = _id;
        name = _name;
    }

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<Course> getAllCourses() {
        Connection connection = ConnectionManager.getConnection();
        Statement stmt;
        String stmtString = "";
        try {
            stmt = connection.createStatement();
            stmtString = "SELECT * FROM Course order by name asc;";
            ResultSet rs = stmt.executeQuery(stmtString);
            ArrayList<Course> courses = new ArrayList<Course>();
            while (rs.next() && rs != null) {
                courses.add(new Course(rs.getInt("course_id"), rs.getString("name")));
            }
            return courses;
        } catch (SQLException e) {
            System.out.println(stmtString);
            e.printStackTrace();
        }
        return null;
    }



    public static int getCourseIdForCurrentName(String name) {
        Connection connection = ConnectionManager.getConnection();
        Statement stmt;
        try {
            stmt = connection.createStatement();
            String stmtString = "SELECT * FROM Course WHERE name = \"" + name + "\";";
            ResultSet rs = stmt.executeQuery(stmtString);
            int course_id;
            if (rs.next() && rs != null) {
                course_id = rs.getInt("course_id");
                return course_id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}