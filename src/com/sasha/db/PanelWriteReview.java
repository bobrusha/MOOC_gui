package com.sasha.db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by Саша on 14.12.2014.
 */
public class PanelWriteReview extends JPanel{
    public static final int REVIEW_TEXT_AREA_WIDTH = 50;
    public static final int REVIEW_TEXT_AREA_HEIGHT = 5;

    PanelWriteReview(int _course_id, FrameGoToCourse _owner){
        owner = _owner;
        course_id = _course_id;
        Connection connection = ConnectionManager.getConnection();
        Statement stmt;
        String stmtString = "";

        try {
            stmt = connection.createStatement();
            stmtString = "SELECT * FROM Review WHERE course_id = " + course_id + " AND student_id = "
                    + GUI.userinfo.getId()+" ;";
            ResultSet rs = stmt.executeQuery(stmtString);

            JPanel panel_grade = new JPanel(new FlowLayout());
            setLayout(new BorderLayout());

            grade = new ButtonGroup();
            for (int i = 1; i <= 5; ++i){
                JRadioButton b = new JRadioButton("" + i);
                grade.add(b);
                panel_grade.add(b);
            }
            JButton sendReview;
            if(!rs.next()) {

                review = new TextArea(REVIEW_TEXT_AREA_HEIGHT, REVIEW_TEXT_AREA_WIDTH);

                sendReview = new JButton("Send review");
                sendReview.addActionListener(new sendReviewButtonActionListener());
            }else{
                add(new JLabel("You wrote:"), BorderLayout.NORTH);
                Enumeration<AbstractButton> buttons = grade.getElements();
                for(int i= 0; i< rs.getInt("grade_course"); ++i) {
                    buttons.nextElement().setSelected(true);
                }
                review = new TextArea(rs.getString("review_txt"), REVIEW_TEXT_AREA_HEIGHT, REVIEW_TEXT_AREA_WIDTH);

                sendReview = new JButton("Change review");
                sendReview.addActionListener(new UpdateReviewActionListener());
            }
            panel_grade.add(sendReview);

            add(review, BorderLayout.CENTER);
            add(panel_grade, BorderLayout.SOUTH);

        } catch (SQLException e) {
            System.out.println(stmtString);
            e.printStackTrace();
        }
    }
    class sendReviewButtonActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String str = review.getText();
            if(str.equals("")) {
                JOptionPane.showMessageDialog(owner, "Your review is empty!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            PreparedStatement stmt = null;
            Connection connection = ConnectionManager.getConnection();

            try {
                int int_grade = 0;
                for (Enumeration<AbstractButton> buttons = grade.getElements(); buttons.hasMoreElements();) {
                    AbstractButton button = buttons.nextElement();
                    if (button.isSelected()) {
                         int_grade = Integer.parseInt(button.getText());
                    }
                }

                stmt = connection.prepareStatement("INSERT INTO Review (student_id, course_id, grade_course, review_txt)" +
                        " values(?,?,?,?)");
                stmt.setInt(1, GUI.userinfo.getId());
                stmt.setInt(2, course_id);
                stmt.setInt(3, int_grade);
                stmt.setString(4, review.getText());

                stmt.executeUpdate();

                JOptionPane.showMessageDialog(owner, "Your review was added successfully");
                owner.dispose();

            } catch (SQLException e1) {
                System.out.println(stmt);
                e1.printStackTrace();
            }

        }
    }
    class UpdateReviewActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = review.getText();
            if(str.equals("")) {
                JOptionPane.showMessageDialog(owner, "Your review is empty!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int int_grade = 0;
            for (Enumeration<AbstractButton> buttons = grade.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    int_grade = Integer.parseInt(button.getText());
                }
            }
            Database.updateReview(int_grade, review.getText(), GUI.userinfo.getId(), course_id);
            JOptionPane.showMessageDialog(owner, "Your review was updated successfully");


        }
    }
    private TextArea review;
    private ButtonGroup grade;
    private int course_id;
    private FrameGoToCourse owner;
}
