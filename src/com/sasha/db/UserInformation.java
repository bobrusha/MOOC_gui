package com.sasha.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Саша on 05.12.2014.
 */
public class UserInformation {
    UserInformation(int _id) {
        id = _id;
        updateInformation();
    }

    public void updateInformation() {
        Connection connection = ConnectionManager.getConnection();
        Statement stmt = null;
            try {
                stmt = connection.createStatement();

                String stmtString = "SELECT * FROM Student WHERE student_id = '" + id + "';";

                ResultSet rs = stmt.executeQuery(stmtString);
                if (rs.next()) {
                    username = rs.getString("login");
                    last_name = rs.getString("last_name");
                    first_name = rs.getString("first_name");
                }

                stmtString = "select Student_Course.student_id, Course.course_id, Course.name from Student_Course " +
                        "join Course on Student_Course.student_id =" + id +
                        " AND Course.course_id = Student_Course.course_id;";
                rs = stmt.executeQuery(stmtString);

                courses = new ArrayList<Course>();
                while (rs.next() && rs != null) {
                    courses.add(new Course(rs.getInt(("Course.course_id")), rs.getString("Course.name")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public ArrayList<Course> getEnrolledCourses() {
        return courses;
    }

    private int id;
    private String username;
    private String last_name;
    private String first_name;
    private ArrayList<Course> courses;
}
