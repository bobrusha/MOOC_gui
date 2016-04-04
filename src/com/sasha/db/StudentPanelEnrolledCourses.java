package com.sasha.db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentPanelEnrolledCourses extends JPanel {
    StudentPanelEnrolledCourses(FrameMain _owner) {
        owner = _owner;
        updatePanel();
    }

    public void updatePanel() {
        GUI.userinfo.updateInformation();
        courses = GUI.userinfo.getEnrolledCourses();

        removeAll();

        if (courses.size() != 0) {
            setLayout(new BorderLayout());

            listModel = new DefaultListModel();

            int length = courses.size();
            for (int i = 0; i < length; ++i) {
                listModel.addElement(courses.get(i).getName());
            }

            list = new JList(listModel);
            list.setSelectedIndex(0);
            add(new JScrollPane(list), BorderLayout.CENTER);
            JPanel butPanel = new JPanel();

            JButton addButton = new JButton("Add new course");
            addButton.addActionListener(new ActionListenerAddButton());
            butPanel.add(addButton);

            JButton goButton = new JButton("Go to the course");
            goButton.addActionListener(new ActionListenerGoButton());
            butPanel.add(goButton);

            JButton deleteButton = new JButton("Delete selected course");
            deleteButton.addActionListener(new ActionListenerDeleteButton());
            butPanel.add(deleteButton);

            add(butPanel, BorderLayout.SOUTH);

        } else {
            add(new JLabel("You don't have enrolled courses"));
            JPanel butPanel = new JPanel();
            JButton addButton = new JButton("Add new course");
            addButton.setFocusable(false);
            addButton.addActionListener(new ActionListenerAddButton());
            butPanel.add(addButton);
            add(butPanel, BorderLayout.SOUTH);
        }
        revalidate();
    }

    class ActionListenerAddButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new FrameAllCourses(owner);
        }
    }

    class ActionListenerGoButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            int course_id = courses.get(index).getId();
            JFrame frame = new FrameGoToCourse(course_id);
            //TODO: to do
        }
    }

    class ActionListenerDeleteButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            int course_id = courses.get(index).getId();

            Object[] options = {"Yes, delete", "No"};
            int answer = JOptionPane.showOptionDialog(GUI.frame, "Are you sure? All your review will be deleted",
                    "Delete course", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[1]);

            if (answer == 0)
                if (Student.deleteCourseWithCurrentCourseId(course_id)) {
                    JOptionPane.showMessageDialog(GUI.frame, "Course was deleted successfully");
                    StudentPanelEnrolledCourses.this.updatePanel();
                } else
                    JOptionPane.showMessageDialog(GUI.frame, "Course wasn't deleted. Try later");
        }
    }

    private DefaultListModel listModel;
    private JList list;
    private ArrayList<Course> courses;
    private FrameMain owner;
}
