package com.sasha.db;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Саша on 15.12.2014.
 */
public class Database {
    public static boolean updateReview(int int_grade, String review, int student_id, int course_id) {
        PreparedStatement stmt = null;
        Connection connection = ConnectionManager.getConnection();
        try {

            stmt = connection.prepareStatement("UPDATE Review SET grade_course = ?, review_txt = ?" +
                    " WHERE student_id = ? AND course_id = ? ;");

            stmt.setInt(1, int_grade);
            stmt.setString(2, review);
            stmt.setInt(3, student_id);
            stmt.setInt(4, course_id);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addNewStudent(String firstName, String lastName, String login, String password,
                                        int university_id) {
        PreparedStatement stmt = null;
        Connection connection = ConnectionManager.getConnection();
        try {

            stmt = connection.prepareStatement("INSERT INTO Student (login, password, last_name, first_name, " +
                    "university_id) VALUES( ?, ?, ?, ?, ?);");

            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.setString(3, lastName);
            stmt.setString(4, firstName);
            if (university_id > 0)
                stmt.setString(5, String.valueOf(university_id));
            else
                stmt.setString(5, null);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static String getFirstAndLastNameFromStudentId(int student_id) {
        Connection connection = ConnectionManager.getConnection();
        Statement stmt = null;
        String stmtString = "";

        try {
            stmt = connection.createStatement();
            stmtString = "SELECT * FROM Student WHERE student_id = " + student_id + ";";
            ResultSet rs = stmt.executeQuery(stmtString);
            if (rs != null) {
                rs.next();
                String res = rs.getString("first_name") + " " + rs.getString("last_name") + "," +
                        getUniversityName(rs.getInt("university_id")) + "("
                        + rs.getString("login") + ")";
                return res;
            }
        } catch (SQLException e) {
            System.out.println(stmtString);
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Course> getCourses(int subject_id, int time, int order) {
        Connection connection = ConnectionManager.getConnection();
        Statement stmt = null;
        String stmtString = "";

        try {
            stmt = connection.createStatement();
            if (order == 0) {
                stmtString = "SELECT * FROM course";
                if (subject_id > 0)
                    stmtString += " JOIN subject_course on course.course_id = subject_course.course_id " +
                            "and subject_course.subject_id =" + subject_id;
            } else {
                stmtString = "Select * from(\n" +
                        "SELECT Course.course_id, Course.name, Course.begin_date, Course.end_date, grade_course, review_txt, (sum(grade_course)/count(grade_course))as gr \n" +
                        "FROM Course\n" +
                        "left join Review on Course.course_id = Review.course_id \n" +
                        "group by course_id\n" +
                        "order by gr desc\n" +
                        ") T ";
                if (subject_id > 0)
                    stmtString += "join subject_course\n" +
                            "on T.course_id = subject_course.course_id\n" +
                            "and subject_course.subject_id = " + subject_id + " ";
            }
            switch (time) {
                case 1:
                    stmtString += " WHERE begin_date > now() ";
                    break;
                case 2:
                    stmtString += " WHERE begin_date < now() and  end_date > now()  ";
                    break;
                case 3:
                    stmtString += " WHERE end_date < now() ";
                    break;
                case 4:
                    stmtString += " WHERE begin_date IS NULL ";
                    break;
            }
            if (order == 0)
                stmtString += " order by course.name asc;";
            else
                stmtString += ";";
            ResultSet rs = stmt.executeQuery(stmtString);
            ArrayList<Course> courses = new ArrayList<Course>();
            while (rs.next() && rs != null) {
                if (order == 0)
                    courses.add(new Course(rs.getInt("Course.course_id"), rs.getString("Course.name")));
                else
                    courses.add(new Course(rs.getInt(1), rs.getString(2)));
            }
            return courses;
        } catch (SQLException e) {
            System.out.println(stmtString);
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getAllUniversities() {
        Connection connection = ConnectionManager.getConnection();
        Statement stmt;
        String stmtString = "";
        try {
            stmt = connection.createStatement();
            stmtString = "SELECT * FROM University order by name asc;";
            ResultSet rs = stmt.executeQuery(stmtString);
            ArrayList<String> universities = new ArrayList<String>();
            while (rs.next() && rs != null) {
                universities.add(rs.getString("name"));
            }
            return universities;
        } catch (SQLException e) {
            System.out.println(stmtString);
            e.printStackTrace();
        }
        return null;
    }

    public static int getUniversityId(String name) {
        Connection connection = ConnectionManager.getConnection();
        Statement stmt;
        String stmtString = "";
        try {
            stmt = connection.createStatement();
            stmtString = "SELECT * FROM University WHERE name = \"" + name + "\";";
            ResultSet rs = stmt.executeQuery(stmtString);
            ArrayList<String> universities = new ArrayList<String>();
            if (rs.next() && rs != null) {
                return rs.getInt("university_id");
            }

        } catch (SQLException e) {
            System.out.println(stmtString);
            e.printStackTrace();
        }
        return -1;
    }

    public static String getUniversityName(int university_id) {
        if(university_id == 0)
            return "";
        Connection connection = ConnectionManager.getConnection();
        Statement stmt;
        String stmtString = "";
        try {
            stmt = connection.createStatement();
            stmtString = "SELECT * FROM University WHERE university_id = " + university_id + ";";
            ResultSet rs = stmt.executeQuery(stmtString);
            ArrayList<String> universities = new ArrayList<String>();
            if (rs.next() && rs != null) {
                return rs.getString("name");
            } else
                return "";

        } catch (SQLException e) {
            System.out.println(stmtString);
            e.printStackTrace();
        }
        return null;
    }

    public static String getInformationAboutTeacherByCourseId(int course_id){
        Connection connection = ConnectionManager.getConnection();
        Statement stmt;
        String stmtString = "";
        try {
            stmt = connection.createStatement();
            stmtString = "select * from teacher join teacher_course on teacher.teacher_id = teacher_course.teacher_id " +
                    "where teacher_course.course_id ="+course_id+";";
            ResultSet rs = stmt.executeQuery(stmtString);
            String Teachers = "";
            while (rs.next() && rs != null) {
                Teachers += rs.getString("teacher.last_name") + " " + rs.getString("teacher.first_name")+", ";
            }
            return Teachers;

        } catch (SQLException e) {
            System.out.println(stmtString);
            e.printStackTrace();
        }
        return null;

    }
}
