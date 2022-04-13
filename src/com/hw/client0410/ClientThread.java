package com.hw.client0410;

import javax.swing.*;
import java.io.InputStream;
import java.net.Socket;

//这是专门为客户端写的接收其他客户端传来的消息的线程
public class ClientThread implements Runnable, MsgType {
    private Socket s;
    private JTextArea area;
    private DefaultListModel<String> userModel;
    private JList<String> userList;
    private String[] userArr = new String[1024];
    public ClientThread(Socket s, JTextArea area, DefaultListModel<String> userModel, JList<String> userList) {
        this.s = s;
        this.area = area;
        this.userModel = userModel;
        this.userList = userList;
    }

    public void readUser(InputStream input) throws Exception {
        int size = input.read();
        for (int i = 0; i < size; ++i) {
            byte[] userBytes = new byte[1024];
            int len = input.read(userBytes);
            String userMsg = new String(userBytes, 0, len);
            System.out.println("用户上线消息收到，准备添加进列表...");
            userArr[i] = userMsg;
            userList.setListData(userArr);
        }
    }

    public void run() {
        while (true) {
            try {
                InputStream input = s.getInputStream();
                int head = input.read();
                System.out.println("得到消息协议头，未确认该消息头具体内容! head = " + head);
                switch (head) {
                    case GROUP:
                        GroupChatThread gct = new GroupChatThread(s, area);
                        Thread t1 = new Thread(gct);
                        t1.start();
                        break;
                    case USER:
                        readUser(input);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
