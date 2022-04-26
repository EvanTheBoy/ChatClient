package com.hw.client0410;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login implements ActionListener {
    public void initUI() {
        JFrame jf = new JFrame("登录");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setSize(400, 400);

        //用户名与文本框部分
        JPanel textPanel = new JPanel();
        JTextField userArea = new JTextField();
        userArea.setPreferredSize(new Dimension(350, 30));
        JTextField passwordArea = new JTextField();
        passwordArea.setPreferredSize(new Dimension(350, 30));
        textPanel.add(userArea);
        textPanel.add(passwordArea);

        //按钮部分
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        //最后完成整体界面
        jf.add(textPanel, BorderLayout.CENTER);
        jf.add(buttonPanel, BorderLayout.SOUTH);
        jf.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        new Login().initUI();
    }
}
