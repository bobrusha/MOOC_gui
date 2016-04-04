package com.sasha.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Саша on 09.12.2014.
 */
public class Student {
    public static boolean deleteCourseWithCurrentCourseId(int course_id) {
        Connection connection = ConnectionManager.getConnection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String stmtString = "DELETE FROM Student_Course WHERE student_id = " + GUI.userinfo.getId() +
                    " AND course_id = " + course_id + ";";
            stmt.executeUpdate(stmtString);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addNewCourseWithCurrentName(String name) {
        Connection connection = ConnectionManager.getConnection();
        Statement stmt;
        try {
            stmt = connection.createStatement();
            String stmtString = "SELECT * FROM Course WHERE name = \"" + name + "\";";
            ResultSet rs = stmt.executeQuery(stmtString);
            int course_id;
            if (rs.next() && rs != null) {
                course_id = rs.getInt("course_id");
            } else {
                return false;
            }
            DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
            Date dateobj = new Date();
            String registration_date = "\'" + df.format(dateobj) + "\'";

            stmtString = "INSERT INTO Student_Course (student_id, course_id, registration_date) VALUES(" + GUI.userinfo.getId() +
                    ", " + course_id + "," + registration_date + ");";
            stmt.executeUpdate(stmtString);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
