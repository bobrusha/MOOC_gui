package com.sasha.db;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Саша on 09.12.2014.
 */
public class PanelCoursesList extends JPanel {
    PanelCoursesList(ArrayList<Course> courses) {
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<String>();

        for (Course course : courses) {
            listModel.addElement(course.getName());
        }

        list = new JList<String>(listModel);
        list.setSelectedIndex(0);

        add(new JScrollPane(list), BorderLayout.CENTER);
    }

    public void UpdateInformation(ArrayList<Course> courses) {
        if (!listModel.isEmpty())
            listModel.clear();

        for (Course course : courses) {
            listModel.addElement(course.getName());
        }
        list.removeAll();
        list.setModel(listModel);
        list.setSelectedIndex(0);

    }

    public String getSelectedCourseName() {
        return list.getSelectedValue();
    }

    private DefaultListModel<String> listModel;
    private JList<String> list;
}
