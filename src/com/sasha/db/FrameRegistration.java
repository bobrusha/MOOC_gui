package com.sasha.db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Саша on 15.12.2014.
 */
public class FrameRegistration extends JFrame {
    public static final int TEXTFIELD_WIDTH = 50;
    public FrameRegistration(){
        //Положение зависящее от платформы
        setLocationByPlatform(true);
        //Устанавливаем название
        setTitle(GUI.PROGRAM_NAME);
        //Нельзя изменять размер окна;
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel firstName = new JPanel();
        firstName_f = new JTextField(TEXTFIELD_WIDTH);
        firstName.add(new JLabel("First name:"));
        firstName.add(firstName_f);

        JPanel lastName = new JPanel();
        lastName_f = new JTextField(TEXTFIELD_WIDTH);
        lastName.add(new JLabel("Last name:"));
        lastName.add(lastName_f);

        JPanel login = new JPanel();
        login_f = new JTextField(TEXTFIELD_WIDTH);
        login.add(new JLabel("Login:         "));
        login.add(login_f);

        JPanel password = new JPanel();
        password_f = new JPasswordField(TEXTFIELD_WIDTH);
        password.add(new JLabel("Password:"));
        password.add(password_f);

        universities = new JComboBox<String>();
        ArrayList<String> universities_str = Database.getAllUniversities();
        universities.addItem("none");
        for(String u: universities_str){ universities.addItem(u);}
        JPanel univer = new JPanel();
        univer.add(new JLabel("University:"));
        univer.add(universities);

        JPanel buttonPanel = new JPanel();
        JButton register = new JButton("Register");
        register.addActionListener(new ActionListenerRegisterButton());
        buttonPanel.add(register);



        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup().addComponent(firstName).addComponent(lastName)
                        .addComponent(login).addComponent(password).addComponent(univer).addComponent(buttonPanel)
        );
        layout.setVerticalGroup(layout.createSequentialGroup().addComponent(firstName).addComponent(lastName)
                .addComponent(login).addComponent(password).addComponent(univer).addComponent(buttonPanel));
        pack();
        setVisible(true);
    }
    class ActionListenerRegisterButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String item = (String) universities.getSelectedItem();
            int university_id = 0;
            if(!item.equals("none")){
                university_id = Database.getUniversityId(item);
            }
            if(firstName_f.getText().equals("")) {
                JOptionPane.showMessageDialog(FrameRegistration.this, "Write your first name", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(lastName_f.getText().equals("")) {
                JOptionPane.showMessageDialog(FrameRegistration.this, "Write your last name", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(login_f.getText().equals("")) {
                JOptionPane.showMessageDialog(FrameRegistration.this, "Write your login", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(password_f.getPassword().equals("")) {
                JOptionPane.showMessageDialog(FrameRegistration.this, "Write your password", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }


            if(Database.addNewStudent(firstName_f.getText(), lastName_f.getText(), login_f.getText(),
                    String.valueOf(password_f.getPassword()), university_id)){
                    FrameRegistration.this.dispose();
            }else
                JOptionPane.showMessageDialog(FrameRegistration.this, "User with current login already exist", "Error",
                        JOptionPane.ERROR_MESSAGE);
        }
    }
    private JTextField firstName_f;
    private JTextField lastName_f;
    private JTextField login_f;
    private JPasswordField password_f;
    private JComboBox<String> universities;
}
