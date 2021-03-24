package com.glch.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {

    public static void main(String args[]) {
        try {
            ServerSocket server = null;
            // 端口为8001,可以从配置文件读取
            server = new ServerSocket(8001);
            while (true) {
                // server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
                Socket socket = server.accept();
                // 每接收到一个Socket就建立一个新的线程来处理它
                new Thread(new Task(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用来处理Socket请求的
     */
    static class Task implements Runnable {
        private Socket socket;
        public Task(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                handleSocket();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 跟客户端Socket进行通信
         *
         * @throws Exception
         */
        private void handleSocket() throws Exception {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
            StringBuilder sb = new StringBuilder();
            String temp;
            int index;
            while ((temp = br.readLine()) != null) {
                System.out.println(temp);
                // 遇到eof时就结束接收，类似的，可以用来解决相关的业务需求
                if ((index = temp.indexOf("eof")) != -1) {
                    sb.append(temp.substring(0, index));
                    break;
                }
                sb.append(temp);
            }
            System.out.println("客户端: " + sb);
            // 收到客户端的请求，对客户端进行回复
            Writer writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            writer.write("你好，客户端。");
            writer.write("eof\n");
            writer.flush();
            writer.close();
            br.close();
            socket.close();
        }
    }
}