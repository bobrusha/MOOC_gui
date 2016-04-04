package com.sasha.db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Саша on 05.12.2014.
 */
public class FrameMain extends JFrame {
    public FrameMain() {

        TopMenuBarPanel topMenuBarPanel = new TopMenuBarPanel();

        studentPanelEnrolledCourses = new StudentPanelEnrolledCourses(this);
        //ShowCoursesPanel coursesPanel = new ShowCoursesPanel();

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        //Положение зависящее от платформы
        setLocationByPlatform(true);
        //Устанавливаем название
        setTitle(GUI.PROGRAM_NAME);
        //Нельзя изменять размер окна;
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        JMenu exportMenu = new JMenu("Export");
        JMenuItem exportToXML = new JMenuItem("Export to XML");
        exportToXML.addActionListener(new exportToXMLActionListener());
        exportMenu.add(exportToXML);
        menuBar.add(exportMenu);
        setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(topMenuBarPanel).addComponent(studentPanelEnrolledCourses)));
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(topMenuBarPanel).addComponent(studentPanelEnrolledCourses)
        );
        setVisible(true);
    }

    public void updatePanelEnrolledCourses() {
        studentPanelEnrolledCourses.updatePanel();
        revalidate();
    }

    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;

    StudentPanelEnrolledCourses studentPanelEnrolledCourses;

    class exportToXMLActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            XMLExporter.createExportFile();
        }
    }
}
