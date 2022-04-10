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
    @Override
    public void run() {
        InputStream input;
        try {
            input = s.getInputStream();
            while (true) {
                byte[] bytes = new byte[1024];
                int length = input.read(bytes);
                String message = new String(bytes, 0, length);
                System.out.println("得到一条消息");
                area.append(message.trim() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
