package com.sasha.db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Саша on 14.12.2014.
 */

   public class PanelCheckboxSubject extends JPanel {
        public PanelCheckboxSubject(FrameAllCourses _owner, ArrayList<Subject> _subjects) {
            owner = _owner;
            subjects = new ArrayList<Subject>(_subjects);

            subjectComboBox = new JComboBox<String>();
            subjectComboBox.addItem("All");
            for (Subject subject : subjects) {
                subjectComboBox.addItem(subject.getName());
            }


            progressComboBox = new JComboBox<String>();
            progressComboBox.addItem("All");
            progressComboBox.addItem("Future courses");
            progressComboBox.addItem("Courses in progress");
            progressComboBox.addItem("Finished courses");
            progressComboBox.addItem("No sessions");

            orderComboBox = new JComboBox<String>();
            orderComboBox.addItem("alphabetical order");
            orderComboBox.addItem("popularity order ");


            JButton searchButton = new JButton("Search");

            JPanel searchPanel = new JPanel();
            searchButton.addActionListener(new SearchButtonActionListener());
            searchPanel.add(searchButton);

            GroupLayout layout = new GroupLayout(this);
            this.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            layout.setHorizontalGroup(layout.createParallelGroup().addComponent(subjectComboBox)
                    .addComponent(progressComboBox).addComponent(orderComboBox).addComponent(searchPanel));
            layout.setVerticalGroup(layout.createSequentialGroup().addComponent(subjectComboBox)
                    .addComponent(progressComboBox).addComponent(orderComboBox).addComponent(searchPanel));


            setPreferredSize(getMinimumSize());
            setMaximumSize(getMinimumSize());

        }

        public int getSelectedSubjectId() {
            int index = subjectComboBox.getSelectedIndex()-1;
            if(index < 0)
                return 0;
            else
                return subjects.get(index).getId();
        }

        class SearchButtonActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                int subject_id = getSelectedSubjectId();
                int time = progressComboBox.getSelectedIndex();
                int order = orderComboBox.getSelectedIndex();
                owner.panelCoursesList.UpdateInformation(Database.getCourses(subject_id, time, order));
                owner.revalidate();
            }
        }

        FrameAllCourses owner;
        ArrayList<Subject> subjects;
        JComboBox<String> subjectComboBox;
        JComboBox<String> progressComboBox;
        JComboBox<String> langComboBox;
        JComboBox<String> orderComboBox;
    }

