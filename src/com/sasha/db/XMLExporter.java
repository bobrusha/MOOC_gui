package com.sasha.db;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Саша on 16.12.2014.
 */
public class XMLExporter {
    public static final String FILE_NAME = "courses.xml";

    public static boolean createExportFile(){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(FILE_NAME, "UTF-8");
            writer.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            writer.println("<!DOCTYPE courses SYSTEM \"courses.dtd\">");
            ArrayList<XMLCourseStructure> arr = getInformationFoExport();
            for(XMLCourseStructure s: arr){
                writer.println("<COURSE id = " + s.getCourse_id() + " name = \"" + s.getName() + "\" >");
                if(s.getReview()!= null){
                    writer.println("\t <REVIEW>");
                    writer.println("\t \t <GRADE> " + s.getGrade() + "</GRADE>");
                    writer.println("\t \t <TEXT> " + s.getReview());
                    writer.println("\t \t </TEXT>");
                    writer.println("\t </REVIEW>");
                }
                writer.println("</COURSE>");
            }
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static ArrayList<XMLCourseStructure> getInformationFoExport(){
        Connection connection = ConnectionManager.getConnection();
        Statement stmt;
        String stmtString = "";
        try {
            stmt = connection.createStatement();
            stmtString = " SELECT Course.course_id, Course.name, grade_course, review_txt FROM Review " +
                    "RIGHT JOIN Student_Course on Student_Course.student_id = " + GUI.userinfo.getId() +
                    " OR Review.course_id = Student_Course.course_id " +
                    "join Course on Course.course_id = Student_Course.course_id;";
            ResultSet rs = stmt.executeQuery(stmtString);
            ArrayList<XMLCourseStructure> courseStructures = new ArrayList<XMLCourseStructure>();
            while (rs.next() && rs != null) {
                courseStructures.add(new XMLCourseStructure(rs.getInt("Course.course_id"),
                        rs.getString("Course.name"), rs.getInt("grade_course"),rs.getString("Review.review_txt")));
            }
            return courseStructures;
        } catch (SQLException e) {
            System.out.println(stmtString);
            e.printStackTrace();
        }
        return null;
    }
}

class XMLCourseStructure{
    public XMLCourseStructure(int _course_id, String _name, int _grade, String _review){
        course_id = _course_id;
        name = _name;
        review = _review;
        grade = _grade;
    }
    private int course_id;
    private String name;
    private String review;
    private int grade;

    public int getCourse_id() {
        return course_id;
    }

    public String getName() {
        return name;
    }

    public String getReview() {
        return review;
    }

    public int getGrade() {
        return grade;
    }
}
