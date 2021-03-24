package com.glch.socket;


import com.glch.base.util.DataTransUtil;

import java.math.BigInteger;

public class Test {
    public static void main(String[] args) {
        //byte[]bytes = new byte[]{0x53,0x54,0x41,0x52,0x54,0x00,0x21,0x00,0x00,0x00,0x01,0x00,0x01,0x00,0x00,0x00,0x00};
        // 16进制转字符串
        byte[] bytes = new byte[]{0x00, 0x31, 0X39, 0X32, 0X2E, 0x31, 0x36, 0x38, 0x2E, 0x31, 0x2E, 0X36, 0x34, 0x00, 0x00, 0x00};
        // 1、byte转16进制
        String msg = DataTransUtil.bytesToHexString(bytes);
        // 2、16进制转字符串
        String msg2 = DataTransUtil.hexStringToString(msg);
        System.out.println("result=" + msg2);
        // 16进制字符串转字符串信息
        String msg3 = "003139322E3136382E312E3634000000";
        byte[] bytes2 = DataTransUtil.hexToByteArray(msg3);
        String msg4 = DataTransUtil.bytesToHexString(bytes2);
        String msg6 = DataTransUtil.hexStringToString(msg4);
        System.out.println("result2=" + msg6);
        // 16进制转10进制
        String m = "1F40";
        // 字符串转16进制
        byte[] b = DataTransUtil.hexToByteArray(m);
        System.out.println("b="+b);
        byte[]a = DataTransUtil.reverse(b);
        String m2= DataTransUtil.bytesToHexString(b);
        // 16进制转10进制
        BigInteger bigInteger= new BigInteger(m2,16);
        System.out.println("十六进制转10进制="+bigInteger);

    }

}
