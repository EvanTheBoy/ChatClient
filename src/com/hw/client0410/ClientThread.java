package com.hw.client0410;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

//这是专门为客户端写的接收其他客户端传来的消息的线程
public class ClientThread implements Runnable, MsgType{
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
            int head = input.read();
            System.out.println("得到消息协议头，未确认该消息头具体内容! hea = "+head);
            switch (head) {
                case GROUP:
                    GroupChatThread gct = new GroupChatThread(s, area);
                    Thread t1 = new Thread(gct);
                    t1.start();
                    break;
                case USER:
                    System.out.println("已确认收到的消息协议为用户上线消息");
                    byte[] userBytes = new byte[30];
                    int len = input.read(userBytes);
                    String userMsg = new String(userBytes, 0, len);
                    System.out.println("用户上线消息收到，准备添加进列表...");

                    userList.addElement(userMsg);
                    System.out.println("添加至列表完毕  userList  = "+userList.size());
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
