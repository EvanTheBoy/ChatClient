package com.hw.client0410;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

//这是专门为客户端写的接收其他客户端传来的消息的线程
public class ClientThread implements Runnable {
    private Socket s;
    private JTextArea area;
    private DefaultListModel<String> userList;
    public ClientThread(Socket s, JTextArea area, DefaultListModel<String> userList) {
        this.s = s;
        this.area = area;
        this.userList = userList;
    }
    public void run() {
        try {
            InputStream input = s.getInputStream();
            byte[] msgBytes = new byte[5];
            int head = input.read(msgBytes);
            switch (head) {
                case 1:

                    break;
                case 3:
                    byte[] userBytes = new byte[30];
                    int len = input.read(userBytes);
                    String userMsg = new String(userBytes, 0, len);
                    userList.addElement(userMsg);
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
