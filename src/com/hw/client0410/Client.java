package com.hw.client0410;

import com.hw.login_register0427.Login_Register;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client implements ActionListener, ListSelectionListener, MsgType {
    private Socket socket;
    private OutputStream output;
    private JFrame jf;
    private JTextArea showArea;
    private JTextField text_field;
    private JScrollPane userTxtScrollPane, userListPanel;
    private JPanel textPane;
    private JButton sendButton;
    private JList<String> userList;
    private int chosen = 1; //1代表广播，2代表群聊，3代表私聊
    private String identity = null;

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

    //创建群聊窗口
    private void initUI() {
        jf = new JFrame("聊天室");
        jf.setSize(700, 500);
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

        //完善最终的界面
        jf.add(userTxtScrollPane, BorderLayout.CENTER);
        jf.add(textPane, BorderLayout.SOUTH);
        jf.add(userListPanel, BorderLayout.EAST);
        jf.setVisible(true);
    }

    //选中用户列表中某个用户，则调用这个方法
    @Override
    public void valueChanged(ListSelectionEvent e) {
        try {
            if (!userList.getValueIsAdjusting()) {
                identity = userList.getSelectedValue();
                //chosen = !identity.equals("群聊"); //代表选中了某个用户，现在我们要开启相应功能了
                if (identity.equals("广播")) {
                    chosen = 1;
                } else if (identity.equals("群聊")) {
                    chosen = 2;
                } else {
                    chosen = 3;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //按钮的点击事件
    @Override
    public void actionPerformed(ActionEvent e) {
        String message = text_field.getText();
        if (message.length() > 0) {
            try {
                output = socket.getOutputStream();
                if (chosen == 3) {
                    //此时代表私聊
                    output.write(PRIVATE);
                    String clientIdentity = (identity.charAt(identity.length() - 1) + "\r\n");
                    output.write(clientIdentity.getBytes());
                } else if (chosen == 1) {
                    //1代表广播
                    output.write(BROADCAST);
                } else if (chosen == 2) {
                    //此时chosen已定位2，代表群聊
                    output.write(GROUP);
                }
                output.write((message + "\r\n").getBytes());
                output.flush();
                String sendMsg = "我:" + message + "\n";
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
