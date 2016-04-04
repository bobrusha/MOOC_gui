package com.sasha.db;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Саша on 12.12.2014.
 */
public class FrameReadReview extends JFrame{
    public FrameReadReview(int course_id){
        setSize(FrameMain.DEFAULT_WIDTH, FrameMain.DEFAULT_HEIGHT);
        //Положение зависящее от платформы
        setLocationByPlatform(true);
        //Устанавливаем название
        setTitle(GUI.PROGRAM_NAME);
        //Нельзя изменять размер окна;
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        PanelShowInformationAboutCourse panelCourseInfo = new PanelShowInformationAboutCourse(new CourseInfo(course_id));

        ArrayList<JLabel> jlabels = getReviews(course_id);
        for(JLabel l: jlabels) {
            panel.add(l);
        }
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JScrollPane jScrollPane = new JScrollPane(panel);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(panelCourseInfo).addComponent(jScrollPane)));
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(panelCourseInfo).addComponent(jScrollPane)
        );

        setVisible(true);

    }
    public ArrayList<JLabel> getReviews(int course_id){
        Connection connection = ConnectionManager.getConnection();
        Statement stmt;
        String stmtString = "";
        try {
            stmt = connection.createStatement();
            stmtString = "SELECT * FROM Review WHERE course_id = " + course_id + " order by review_id asc;";
            ResultSet rs = stmt.executeQuery(stmtString);
            ArrayList<JLabel> reviews = new ArrayList<JLabel>();
            while (rs.next() && rs != null) {
                String str = "<html><body width = 700>";
                str += "<i> " + Database.getFirstAndLastNameFromStudentId(rs.getInt("student_id")) + " wrote: </i><br>";
                str += " <b>" + rs.getString("review_txt") + "</b><br></body></html>";
                reviews.add(new JLabel(str));
            }
            return reviews;
        } catch (SQLException e) {
            System.out.println(stmtString);
            e.printStackTrace();
        }
        return null;
    }
}
