package com.sasha.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Саша on 12.12.2014.
 */
public class CourseInfo {
    public CourseInfo(int _course_id){
        course_id = _course_id;
        getInfoAboutCourse();
    }
    public boolean getInfoAboutCourse(){
        Connection connection = ConnectionManager.getConnection();
        Statement stmt = null;
        String stmtString = "";
        try {
            stmt = connection.createStatement();
            stmtString = "SELECT * FROM Course WHERE course_id = " + course_id + ";";
            ResultSet rs = stmt.executeQuery(stmtString);

            if (rs.next() && rs != null) {
                name = rs.getString("name");
                begin_date = rs.getString("begin_date");
                end_date = rs.getString("end_date");
                description = rs.getString("description");
                lang = rs.getString("lang");
                link = rs.getString("link");
            }
            return true;

        } catch (SQLException e) {
            System.out.println(stmtString);
            e.printStackTrace();
        }
        return false;
    }

    private int course_id;
    private String name;
    private String begin_date;
    private String end_date;
    private String description;
    private String lang;
    private String link;

    public int getCourse_id() {
        return course_id;
    }

    public String getName() {
        return name;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getDescription() {
        return description;
    }

    public String getLang() {
        return lang;
    }

    public String getLink() {
        return link;
    }
}
