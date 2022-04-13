package com.hw.client0410;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client implements ActionListener {
    private Socket socket;
    private OutputStream output;
    private JFrame jf;
    private JTextArea showArea;
    private JTextField text_field;
    private JScrollPane userTxtScrollPane, userListPanel;
    private JPanel textPane, rightPanel;
    private JButton sendButton;
    private DefaultListModel<String> userModel;
    private JList<String> userList;

    public void createClient(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            initUI();
            //接受消息线程
            //Thread that receives information
            ClientThread rt = new ClientThread(socket, showArea, userModel, userList);
            Thread t2 = new Thread(rt);
            t2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        jf = new JFrame("聊天室");
        jf.setSize(900, 600);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        showArea = new JTextArea();
        showArea.setEditable(false);
        showArea.setLineWrap(true);
        //显示文字部分的pane  pane which is used to display texts
        userTxtScrollPane = new JScrollPane(showArea);

        text_field = new JTextField();
        text_field.setColumns(30);
        //text_field.setPreferredSize(new Dimension(410, 35));
        text_field.addActionListener(this);
        sendButton = new JButton("send");
        //sendButton.setPreferredSize(new Dimension(80, 35));
        sendButton.addActionListener(this);
        //底部文本框和按钮pane  text field and pane of button on the bottom
        textPane = new JPanel();
        textPane.setLayout(new FlowLayout());
        textPane.add(text_field);
        textPane.add(sendButton);

        userModel = new DefaultListModel<>();
        userList = new JList<>(userModel);
        userListPanel = new JScrollPane(userList);
        rightPanel = new JPanel();
        rightPanel.setBorder(new LineBorder(null));
        rightPanel.setPreferredSize(new Dimension(280, 0));
        rightPanel.add(userListPanel);

//        leftPanel = new JPanel();
//        leftPanel.setBorder(new LineBorder(null));
//        leftPanel.setPreferredSize(new Dimension(620, 600));
//        leftPanel.add(userTxtScrollPane, BorderLayout.CENTER);
//        leftPanel.add(textPane, BorderLayout.SOUTH);
        jf.add(userTxtScrollPane, BorderLayout.CENTER);
        jf.add(textPane, BorderLayout.SOUTH);
        jf.add(rightPanel, BorderLayout.EAST);
//        jf.add(leftPanel, BorderLayout.WEST);
        jf.setVisible(true);
    }

//    //获取服务器发送过来的用户信息,并显示在用户列表上
//    //Obtain users' information sent from the server,and list them on the user's list
//    private void displayUsers(Socket socket) {
//        try {
//            InputStream input = socket.getInputStream();
//            byte[] bytes = new byte[30];
//            int length = input.read(bytes);
//            String word = new String(bytes, 0, length);
//            userModel.addElement(word);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = text_field.getText();
        if (message.length() > 0) {
            try {
                output = socket.getOutputStream();
                output.write(message.getBytes());
                output.flush();
                showArea.append("我:" + message + "\n");
                text_field.setText(null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Client().createClient("127.0.0.1", 8888);
    }
}
