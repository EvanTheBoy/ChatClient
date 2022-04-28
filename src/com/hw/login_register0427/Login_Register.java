package com.hw.login_register0427;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Login_Register implements ActionListener {
    private JTextField userID;
    private JPasswordField password;
    private JRadioButton btn1, btn2, btn3;
    private JButton confirm;
    private JPanel buttonPanel, titlePanel;
    private JFrame jf, frame;
    private String[] userInfo = new String[2];
    private boolean collected;

    public Login_Register() {
        collected = false;
    }

    public void initEnterUI() {
        //登录页面
        jf = new JFrame("Login Page");
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
        JButton loginButton = new JButton("Enter");
        loginButton.setBounds(135, 300, 83, 30);
        loginButton.addActionListener(this);
        jf.add(loginButton);

        //注册按钮
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(265, 300, 83, 30);
        jf.add(registerButton);

        jf.setVisible(true);
    }

    private void initOptionUI() {
        //选择页面
        frame = new JFrame("选择界面");
        frame.setSize(280, 140);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        //设置问题
        JLabel title = new JLabel();
        title.setText("请选择你要加入的群聊");
        titlePanel = new JPanel();
        titlePanel.add(title);

        //设置按钮
        btn1 = new JRadioButton("随缘");
        btn1.setSelected(true);
        btn2 = new JRadioButton("1群");
        btn3 = new JRadioButton("2群");
        ButtonGroup group = new ButtonGroup();
        group.add(btn1);
        group.add(btn2);
        group.add(btn3);
        buttonPanel = new JPanel();
        buttonPanel.add(btn1);
        buttonPanel.add(btn2);
        buttonPanel.add(btn3);
        confirm = new JButton("确认");
        buttonPanel.add(confirm);

        //最后的界面完成
        frame.add(titlePanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
        System.out.println("选择页面");
    }

    public boolean isCollected() {
        return collected;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Enter")) {
            System.out.println("按下了按钮");
            String userName = userID.getText();
            System.out.println("获取到的用户名是:"+userName);
            //获取用户的用户名
            userInfo[0] = userName;
            String passwd = Arrays.toString(password.getPassword());
            User user = new User();
            user.setPassword(passwd);
            user.setUsername(userName);
            jf.setVisible(false);
            initOptionUI();
        } else if (e.getActionCommand().equals("确认")) {
            for (Component c : buttonPanel.getComponents()) {
                if (c instanceof JRadioButton) {
                    if (((JRadioButton) c).isSelected()) {
                        String info = ((JRadioButton) c).getText();
                        userInfo[1] = info;
                        collected = true;
                        frame.setVisible(false);
                    }
                }
            }
        }
    }
}
