package com.hw.client0410;

import javax.swing.*;
import java.awt.*;

public class Login {
    public void initUI() {
        JFrame jf = new JFrame("登录");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setSize(400, 400);

        //用户名与文本框部分
        JPanel textPanel = new JPanel();
        JTextField userArea = new JTextField();
        JTextField passwordArea = new JTextField();


        //按钮部分
        JPanel buttonPanel = new JPanel();

        //最后完成整体界面
        jf.add(textPanel, BorderLayout.CENTER);
        jf.add(buttonPanel, BorderLayout.SOUTH);
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        new Login().initUI();
    }
}
