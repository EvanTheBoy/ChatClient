package com.hw.client0410;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

//这是专门为客户端写的接收其他客户端传来的消息的线程
public class ClientThread implements Runnable, MsgType {
    private Socket s;
    private JTextArea area;
    private JList<String> userList;
    private OutputStream output;
    public ClientThread(Socket s, JTextArea area, JList<String> userList) {
        this.s = s;
        this.area = area;
        this.userList = userList;
        try {
            this.output = s.getOutputStream();
            output.write(USER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readUser(InputStreamReader input) throws Exception {
        int size = input.read();
        System.out.println("user size = " + size);
        String[] userArr = new String[size];
        for (int i = 0; i < size; ++i) {
            String userMsg = readString(input);
            System.out.println("用户上线消息收到，准备添加进列表..." + userMsg);
            userArr[i] = userMsg;
        }
        userList.setListData(userArr);
    }

    private String readString(InputStreamReader is) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while ((i = is.read()) != '#') {
            char c = (char) i;
            stringBuffer.append(c);
        }
        return new String(stringBuffer);
    }

    private String getMessage(InputStreamReader input) throws Exception {
        StringBuffer message = new StringBuffer();
        int i = 0;
        while ((i = input.read()) != 13) {
            char c = (char) i;
            message.append(c);
            System.out.println("现在的消息是:" + message);
        }
        return new String(message);
    }

    private void handleMessage(InputStreamReader input) throws Exception {
        String message = getMessage(input);
        area.append(message.trim() + "\n");
    }

    public void run() {
        while (true) {
            try {
                InputStreamReader input = new InputStreamReader(s.getInputStream());
                int head = input.read();
                System.out.println("得到消息协议头, head = " + head);
                System.out.println("准备进入switch...");
                switch (head) {
                    case GROUP:
                        handleMessage(input);
                        break;
                    case USER:
                        readUser(input);
                        break;
                    case PRIVATE:
                        handleMessage(input);
                        //area2.append(privateMessage.trim() + "\n");
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
