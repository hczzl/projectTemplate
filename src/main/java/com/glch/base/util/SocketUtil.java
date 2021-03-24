package com.glch.base.util;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketUtil {
    //socket服务器ip
    private static String ip;
    //socket服务器端口号
    private static Integer port;
    //socket全局变量
    public static Socket socket = null;
    //身份验证状态
    public static boolean identitySuccess = false;

    public static String getIp() {
        return ip;
    }
    @Value("${socket.cmdServer.ip}")
    public static void setIp(String ip) {
        SocketUtil.ip = ip;
    }

    public static Integer getPort() {
        return port;
    }
    @Value("${socket.cmdServer.port}")
    public static void setPort(Integer port) {
        SocketUtil.port = port;
    }

    public static void connect(){
        try {
            identitySuccess = false;
            socket = new Socket("192.168.1.156", 8080);
//            socket = new Socket(ip, port);
            System.out.println("连接成功！");
        } catch (Exception e) {
            socket = null;
            System.out.println("服务器异常，连接中间件失败！正在尝试重连。。。");
        }
    }

    public static Socket connect(String ip,Integer port){
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
            System.out.println("连接成功！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器异常，连接中间件失败！正在尝试重连。。。");
            return null;
        }
        return socket;
    }

    public static String send(Socket socket,byte[]  message) throws IOException {
        // 1、输出到服务端
        OutputStream os = null;
        os = socket.getOutputStream();
        os.write(message);
        // 2、从服务端读入
        InputStream is;
        byte[] buffer = new byte[115];
        StringBuilder sb = new StringBuilder();
        is = socket.getInputStream();
        if(is.available()>0){
            while (true) {
                int ret = is.read(buffer);
                if (ret < 0) {
                    break;
                }
                sb.append(new String(buffer, 0, ret));
                if (ret < buffer.length) {
                    break;
                }
            }
        }
        return sb.toString();
    }

    public static void closeConnect(Socket socket){
        OutputStream os = null;
        try {
            if(socket!=null){
                os = socket.getOutputStream();
                if(os != null){
                    os.close();
                }
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] send2Byte(Socket socket,byte[]  message) throws IOException {
        if(null == socket){
            return null;
        }
        OutputStream os;
        InputStream is;
        byte[] buffer = null;
        // 1、输出到服务端
        os = socket.getOutputStream();
        os.write(message);
        // 2、从服务端读入
        buffer = new byte[115];
        is = socket.getInputStream();
        if(is.available()>0){
            is.read(buffer);
        }
        return buffer;
    }

    /**
     * 连接状态监控
     * @return
     */
    public static boolean checkConnected(Socket socket,byte b){
        OutputStream os = null;
        try {
            os = socket.getOutputStream();
            os.write(b);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 验证登陆与身份
     */
    public static void validate(){
        if(SocketUtil.socket==null || (SocketUtil.socket != null && !SocketUtil.checkConnected(SocketUtil.socket,(byte)0xFF))){
            SocketUtil.closeConnect(SocketUtil.socket);
            SocketUtil.connect();
        }
        byte[] identityRequest = {(byte) 0xCD, (byte) 0xAB, (byte) 0xCD, (byte) 0xAB, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0xE0, (byte) 0xC1, (byte) 0xDE, (byte) 0xAB, (byte) 0xF6, (byte) 0x7F, (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0A, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        while(SocketUtil.socket!=null && !SocketUtil.identitySuccess){
            try {
                byte[] response = SocketUtil.send2Byte(SocketUtil.socket,identityRequest);
                boolean flag = true;
                for(int i=0;i<identityRequest.length;i++){
                    if(identityRequest[i] != response[i]){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    SocketUtil.identitySuccess = true;
                    break;
                }else{
                    SocketUtil.identitySuccess = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                SocketUtil.closeConnect(SocketUtil.socket);
                System.out.println("服务器异常，身份验证失败!");
            }
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
