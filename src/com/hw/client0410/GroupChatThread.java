package com.hw.client0410;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class GroupChatThread implements Runnable {
    private JTextArea area;
    private Socket s;
    public GroupChatThread(Socket s, JTextArea area) {
        this.s = s;
        this.area = area;
    }

//    private String getMsg(InputStream input) throws Exception {
//        StringBuffer message = new StringBuffer();
//        int i = 0;
//        while ((i = input.read()) != '#') {
//            char c = (char) i;
//            message.append(c);
//            System.out.println("现在的消息是:" + message);
//        }
//        return new String(message);
//    }

    @Override
    public void run() {
        InputStream input;
        try {
            input = s.getInputStream();
            while (true) {
                System.out.println("消息就要来了");
                byte[] bytes = new byte[1024];
                int length = input.read(bytes);
                String message = new String(bytes, 0, length);
//                String message = getMsg(input);
                System.out.println("得到一条消息:" + message);
                area.append(message.trim() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
