package com.glch.socket;

import javax.jdo.annotations.Order;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zhongzhilong
 * @date 2021-03-09
 * @description socket服务端
 */

public class Server {
    public static void main(String[] args) {
        // Server server = new Server();
        // server.connect();
        // server.start();
        ServerSocket serverSocket = null;
        BufferedReader reader;
        BufferedWriter writer;
        try {
            serverSocket = new ServerSocket(8088);
            System.out.println("服务端服务开启监听\n");
            // 通过死循环开启长连接
            while (true) {
                Socket socket = serverSocket.accept();
                InputStream in = socket.getInputStream();
                byte[] bytes=new byte[1024];
                int len = in.read(bytes);
                System.out.println("服务器端收到:"+new String(bytes,0,len));
                in.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务端开启
     */
    public void connect() {
        ServerSocket serverSocket = null;
        try {
            // 创建服务端的端口
            serverSocket = new ServerSocket(8088);
            System.out.println("服务端初始化成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动监听
     */
    public void start() {
        ServerSocket serverSocket = null;
        try {
            // 拿到客户端
            Socket socket = serverSocket.accept();
            // 获取客户端的ip地址
            String address = socket.getInetAddress().getHostAddress();
            System.out.println("客户端ip为：" + address);
            // 拿到客户端传递的字节流
            InputStream is = socket.getInputStream();
            // 将字节流转换为字符流
            InputStreamReader ins = new InputStreamReader(is, "utf-8");
            //创建输入的缓冲区
            BufferedReader rd = new BufferedReader(ins);
            // 读取一行
            String message = rd.readLine();
            System.out.println(address + ":" + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}