package com.hw.login_register0427;

import com.hw.client0410.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Login_Register implements ActionListener {
    private JTextField userID;
    private JPasswordField password;
    private int id = 0;
    public void initUI() {
        JFrame jf = new JFrame("Login Page");
        jf.setSize(500, 500);
        jf.setLayout(null);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //账号label
        JLabel nameStr = new JLabel("Account");
        nameStr.setBounds(100, 165, 100, 25);
        jf.add(nameStr);

        //密码label
        JLabel passwordStr = new JLabel("Password");
        passwordStr.setBounds(100, 215, 100, 25);
        jf.add(passwordStr);

        //账号输入框
        userID = new JTextField();
        userID.setBounds(180, 165, 200, 28);
        jf.add(userID);

        //密码输入框
        password = new JPasswordField();
        password.setBounds(180, 215, 200, 28);
        jf.add(password);

        //登录按钮
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(135, 300, 83, 30);
        loginButton.addActionListener(this);
        jf.add(loginButton);

        //注册按钮
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(265, 300, 83, 30);
        jf.add(registerButton);

        jf.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userName = userID.getText();
        String passwd = new String(password.getPassword());
        User user = new User();
        user.setId(++id);
        user.setPassword(passwd);
        user.setUsername(userName);
    }

    public static void main(String[] args) {
        new Login_Register().initUI();
    }
}
