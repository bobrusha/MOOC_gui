package com.sasha.db;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by Саша on 06.12.2014.
 */
public class TopMenuBarPanel extends JPanel {
    TopMenuBarPanel() {
        username = new JLabel(GUI.userinfo.getUsername());

        username.setBorder(new LineBorder(Color.BLACK));


        add(username);


        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
    }

    public static final int DEFAULT_HEIGHT = 50;
    public static final int DEFAULT_WIDTH = 700;

    private JLabel username;

}
