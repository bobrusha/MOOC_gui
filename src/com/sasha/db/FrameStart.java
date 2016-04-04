package com.sasha.db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Саша on 04.12.2014.
 */
public class FrameStart extends JFrame {
    public FrameStart() {
        setSize(START_WIDTH, START_HEIGHT);
        //Положение зависящее от платформы
        setLocationByPlatform(true);
        //Устанавливаем название
        setTitle(GUI.PROGRAM_NAME);

        //Нельзя изменять размер окна;
        setResizable(false);

        username = new JTextField(20);
        password = new JPasswordField(20);
        logIn = new JButton("Log in");

        JLabel label_uname = new JLabel("Username:", SwingConstants.RIGHT);
        JLabel label_pword = new JLabel("Password:", SwingConstants.RIGHT);

        JPanel panel = new JPanel();
        panel.add(label_uname);
        panel.add(username);
        panel.add(label_pword);
        panel.add(password);
        panel.add(logIn);

        JButton registration = new JButton("Registration");
        registration.addActionListener(new ActionListenerRegistration());
        panel.add(registration);
        logIn.addActionListener(new LoginButtonListener());
        add(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }


    public static final int START_WIDTH = 350;
    public static final int START_HEIGHT = 200;

    private JTextField username;
    private JPasswordField password;
    private JButton logIn;
    private JFrame owner;

    class LoginButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            //Реакция на щелчок по кнопке
            String username_str = username.getText().trim();
            if (username_str.equals("")) {
                System.out.print("Please write username");
                JOptionPane.showMessageDialog(FrameStart.this,
                        "Field \" Username \"is empty",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            String password_str = new String(password.getPassword());
            if (password_str.equals("")) {
                System.out.println("Please write password");
                JOptionPane.showMessageDialog(FrameStart.this,
                        "Field \" Password \"is empty",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int res = checkStudentLogInData(username_str, password_str);
                if (res > 0) {
                    FrameStart.this.dispose();
                    GUI.userinfo = new UserInformation(res);
                    GUI.frame = new FrameMain();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        private int checkStudentLogInData(String username, String password) throws SQLException {
            Connection connection = ConnectionManager.getConnection();
            Statement stmt = connection.createStatement();
            String stmtString = "SELECT * FROM Student WHERE login = '" + username
                    + "' AND password = '" + password + "';";
            ResultSet rs = stmt.executeQuery(stmtString);

            if (rs.next()) {
                return (rs.getInt("student_id"));
            } else
                return -1;

        }
    }
    class ActionListenerRegistration implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            FrameRegistration frame = new FrameRegistration();
        }
    }
}

