package com.sasha.db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Саша on 09.12.2014.
 */
public class FrameAllCourses extends JFrame {
    FrameAllCourses(FrameMain _owner) {
        owner = _owner;
        setSize(FrameMain.DEFAULT_WIDTH, FrameMain.DEFAULT_HEIGHT);
        //Положение зависящее от платформы
        setLocationByPlatform(true);
        //Устанавливаем название
        setTitle(GUI.PROGRAM_NAME);
        //Нельзя изменять размер окна;
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);


        panelCheckboxSubject = new PanelCheckboxSubject(this, Subject.getAllSubjects());

        panelCoursesList = new PanelCoursesList(Course.getAllCourses());

        JPanel butPanel = new JPanel();

        JButton addToCourses = new JButton("Add to my courses");
        addToCourses.addActionListener(new AddCoursesActionListener());

        JButton readReview = new JButton("Read about course");
        readReview.addActionListener(new ReadReviewActionListener());

        butPanel.add(readReview);
        butPanel.add(addToCourses);

        panelCoursesList.add(butPanel, BorderLayout.SOUTH);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(panelCheckboxSubject)
                                .addComponent(panelCoursesList))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(panelCheckboxSubject)
                        .addComponent(panelCoursesList)
        );



        setVisible(true);
    }

    class AddCoursesActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = panelCoursesList.getSelectedCourseName();
            Student.addNewCourseWithCurrentName(name);
            owner.updatePanelEnrolledCourses();
        }
    }

    class ReadReviewActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int course_id = Course.getCourseIdForCurrentName(panelCoursesList.getSelectedCourseName());
            if(course_id > 0) {
                FrameReadReview frameReadReview = new FrameReadReview(course_id);
            }
        }
    }
    FrameMain owner;
    PanelCoursesList panelCoursesList;
    PanelCheckboxSubject panelCheckboxSubject;
}
