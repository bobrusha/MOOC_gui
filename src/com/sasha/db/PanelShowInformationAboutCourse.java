package com.sasha.db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Саша on 12.12.2014.
 */
public class PanelShowInformationAboutCourse extends JPanel{
    public static final int DEFAULT_WIDTH = 700;

    PanelShowInformationAboutCourse(CourseInfo _courseInfo){
        courseInfo = _courseInfo;
        String information_str = "<html><body width = " + DEFAULT_WIDTH + ">";
        information_str += "<h1>" + courseInfo.getName() + "</h1>";
        information_str += "<p> <b>Start of course:</b> " + courseInfo.getBegin_date() + "</p>";
        information_str += "<p> <b> Description: </b>" + courseInfo.getDescription() + "</p>";
        information_str += "<p><b>Teacher(s):</b>"+
                Database.getInformationAboutTeacherByCourseId(courseInfo.getCourse_id()) +"</p>";
        information_str += "</body> </html>";
        JLabel information_jlabel = new JLabel(information_str);


        JButton openInBrowser = new JButton("Open homepage in browser");
        openInBrowser.addActionListener(new openInBrowserActionListener());

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(information_jlabel).addComponent(openInBrowser)));
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(information_jlabel).addComponent(openInBrowser)
        );

    }
    class openInBrowserActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI(courseInfo.getLink()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }
    CourseInfo courseInfo;
}
