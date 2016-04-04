package com.sasha.db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Саша on 09.12.2014.
 */
public class FrameGoToCourse extends JFrame {
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;

    private CourseInfo courseInfo;

    FrameGoToCourse(int course_id) {
        courseInfo = new CourseInfo(course_id);

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        //Положение зависящее от платформы
        setLocationByPlatform(true);
        //Устанавливаем название
        setTitle(courseInfo.getName());
        //Нельзя изменять размер окна;
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        PanelShowInformationAboutCourse informationAboutCourse = new PanelShowInformationAboutCourse(courseInfo);

        PanelWriteReview writeReview = new PanelWriteReview(course_id, this);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(informationAboutCourse).addComponent(writeReview))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(informationAboutCourse).addComponent(writeReview)
        );

        setVisible(true);

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

}
