package com.hw.client0410;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client implements ActionListener, ListSelectionListener {
    private Socket socket;
    private OutputStream output;
    private JFrame jf;
    private JTextArea showArea, pShowArea;
    private JTextField text_field, pTxtField;
    private JScrollPane userTxtScrollPane, userListPanel, privateUserPane;
    private JPanel textPane, rightPanel, pFieldPane;
    private JButton sendButton, pSendButton;
    private JList<String> userList;
    
    public void createClient(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            initUI();
            //接受消息线程
            ClientThread rt = new ClientThread(socket, showArea, userList);
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

        //消息显示部分
        showArea = new JTextArea();
        showArea.setEditable(false);
        showArea.setLineWrap(true);
        userTxtScrollPane = new JScrollPane(showArea);

        //消息发送框和按钮
        text_field = new JTextField();
        text_field.setColumns(30);
        text_field.addActionListener(this);
        sendButton = new JButton("send");
        sendButton.addActionListener(this);
        textPane = new JPanel();
        textPane.setLayout(new FlowLayout());
        textPane.add(text_field);
        textPane.add(sendButton);

        //用户列表
        userListPanel = new JScrollPane();
        userListPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        userListPanel.setPreferredSize(new Dimension(220, 100));
        userList = new JList<>();
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setFont(new Font("楷体", Font.BOLD, 32));
        userList.addListSelectionListener(this);
        userListPanel.setViewportView(userList);

        jf.add(userTxtScrollPane, BorderLayout.CENTER);
        jf.add(textPane, BorderLayout.SOUTH);
        jf.add(userListPanel, BorderLayout.EAST);
        jf.setVisible(true);
    }

    private void activatePrivateUI(String username) {
        JFrame privateUI = new JFrame("与" + username + "的聊天");
        privateUI.setSize(800, 500);
        privateUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        privateUI.setLocationRelativeTo(null);

        //消息显示区域
        pShowArea = new JTextArea();
        pShowArea.setEditable(false);
        pShowArea.setLineWrap(true);
        privateUserPane = new JScrollPane(pShowArea);

        //文本框和按钮
        pTxtField = new JTextField();
        pTxtField.setColumns(30);
        pTxtField.addActionListener(this);
        pSendButton = new JButton("send");
        pSendButton.addActionListener(this);
        pFieldPane = new JPanel(new FlowLayout());
        pFieldPane.add(pTxtField);
        pFieldPane.add(pSendButton);

        //最后的界面添加
        privateUI.add(privateUserPane, BorderLayout.CENTER);
        privateUI.add(pFieldPane, BorderLayout.SOUTH);
        privateUI.setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        try {
            if (!userList.getValueIsAdjusting()) {
                String identity = userList.getSelectedValue();
                activatePrivateUI(identity);
                String message = pTxtField.getText();
                output = socket.getOutputStream();
                System.out.println("现在我将发送私聊消息" + message);
                output.write(message.getBytes());
                String sendPrivateMsg = message + "\n";
                pShowArea.append(sendPrivateMsg);
                pTxtField.setText(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = text_field.getText();
        if (message.length() > 0) {
            try {
                output = socket.getOutputStream();
                output.write(message.getBytes());
                output.flush();
                String sendMsg = "我:" + message + "\n";
                System.out.println("我将把这个消息发送出去:" + sendMsg);
                showArea.append(sendMsg);
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
