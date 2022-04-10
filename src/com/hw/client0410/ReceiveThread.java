package com.hw.client0410;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

//这是专门为客户端写的接收其他客户端传来的消息的线程
public class ReceiveThread implements Runnable {
    private Socket s;
    private JTextArea area;
    private DefaultListModel<String> userList;
    public ReceiveThread(Socket s, JTextArea area, DefaultListModel<String> userList) {
        this.s = s;
        this.area = area;
        this.userList = userList;
    }
    public void run() {
        try {
            InputStream input = s.getInputStream();
            int head = input.read();
            byte[] userBytes = new byte[30];
            int len = input.read(userBytes);
            String userMsg = new String(userBytes, 0, len);
            userList.addElement(userMsg);

            while (true) {
                byte[] bytes = new byte[1024];
                int length = input.read(bytes);
                String message = new String(bytes, 0, length);
                area.append(message.trim() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
