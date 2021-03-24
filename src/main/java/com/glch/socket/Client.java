package com.glch.socket;

import com.glch.base.util.StringUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author zhongzhilong
 * @date 2021-03-09
 * @description 客户端
 */
public class Client {
    public Socket socket;

    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
    }

    /**
     * 创建客户端并连接到服务器
     */
    public void connect() {
        try {
            socket = new Socket("127.0.0.1", 8088);
            System.out.println("创建客户端成功");
            // 拿到自身的输出流
            OutputStream outputStream = socket.getOutputStream();
            Test2.Student student = new Test2.Student();
            student.setName("192.168.1.64");
            byte[]bytes = StringUtil.ObjectToByte(student);
            for(byte b:bytes){
                System.out.println(b+",");
            }
            outputStream.write(bytes);
            outputStream.close();
            socket.close();
            // 将字节流转换为字符流
            // OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8");
            // 创建缓冲区
            // PrintWriter pw = new PrintWriter(writer, true);
            // pw.println("会沉寂吗");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
