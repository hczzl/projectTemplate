package com.glch.base.util;


import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKTReader;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据转换工具类
 *
 * @author wenchaochao
 * @since 2020-04-06
 */
public class DataTransUtil {

    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(obj);
                map.put(fieldName, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 字符串转Geometry对象
     *
     * @param str
     * @return
     */
    public static Geometry string2Geometry(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        Geometry geometry = null;
        try {
            geometry = new WKTReader(new GeometryFactory(new PrecisionModel(), 4326)).read(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return geometry;
    }

    /**
     * 字符串转经纬度
     *
     * @param str
     * @return
     */
    public static List<Double[]> geometryStrToPoints(String str) {
        List<Double[]> list = new ArrayList<>();
        try {
            Geometry geometry = string2Geometry(str);
            list = geometryToPoints(geometry);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    /**
     * Geometry转经纬度
     *
     * @param geometry
     * @return
     */
    public static List<Double[]> geometryToPoints(Geometry geometry) {
        List<Double[]> list = new ArrayList<>();
        try {
            if (null == geometry) {
                return list;
            }
            Coordinate[] coordinates = geometry.getCoordinates();
            if (null != coordinates) {
                for (int i = 0; i < coordinates.length; i++) {
                    Coordinate coordinate = coordinates[i];
                    Double[] doubles = new Double[2];
                    doubles[0] = coordinate.x;
                    doubles[1] = coordinate.y;
                    list.add(doubles);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    /**
     * base64(字节码)转inputStream
     *
     * @param base64Byte
     * @return
     */
    public static InputStream base64ToInputStream(final byte[] base64Byte) {
        ByteArrayInputStream stream;
        try {
            byte[] b = Base64.decodeBase64(base64Byte);
            stream = new ByteArrayInputStream(b);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return stream;
    }

    /**
     * 字节数组转换流
     *
     * @param b
     * @return
     */
    public static InputStream byte2InputStream(final byte[] b) {
        ByteArrayInputStream stream;
        try {
            stream = new ByteArrayInputStream(b);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return stream;
    }

    /**
     * inputStream转换为字节数组
     *
     * @param in
     * @return
     */
    public static byte[] stream2ByteArr(InputStream in) {
        byte[] data = null;
        try {
            //输入流转换为字节
            ByteArrayOutputStream aos = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                aos.write(buff, 0, rc);
            }
            data = aos.toByteArray();
            //字节转换为字符串
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    public static String stream2String(InputStream in, String charset) {
        String str = "";
        if (!StringUtil.isEmpty(charset)) {
            charset = "utf-8";
        }
        byte[] data;
        try {
            data = stream2ByteArr(in);
            //字节转换为字符串
            str = new String(data, charset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

    /**
     * inputStream转base64
     *
     * @param in
     * @return
     */
    public static String getBase64FromInputStream(InputStream in) {
        String base64 = "";
        byte[] data;
        try {
            data = stream2ByteArr(in);
            //字节转换为字符串
            base64 = new String(Base64.encodeBase64(data));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    public static String convertFile2Base64(File file) {
        String base64 = "";
        byte[] data = null;
        InputStream in = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            BASE64Encoder encoder = new BASE64Encoder();
            base64 = encoder.encode(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    public static String stream2Basr64(InputStream in) {
        String base64 = "";
        try {
            ByteArrayOutputStream aos = new ByteArrayOutputStream();
            int len = 100;
            byte[] buff = new byte[len];
            int rc = 0;
            while ((rc = in.read(buff, 0, len)) > 0) {
                aos.write(buff, 0, rc);
            }
            base64 = new String(Base64.encodeBase64(aos.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return base64;
    }

    /**
     * base64(字符串)转inputStream
     *
     * @param base64
     * @return
     */
    public static InputStream base64ToInputStream(String base64) {
        ByteArrayInputStream stream;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(base64);
            stream = new ByteArrayInputStream(b);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return stream;
    }

    /**
     * byte转16进制
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 16进制转byte
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * byte转int
     *
     * @param src
     * @param offset
     * @return
     */
    public static int bytes2Int(byte[] src, int offset) {
        int value;
        value = ((src[offset + 3] & 0xFF) << 24) | ((src[offset + 2] & 0xFF) << 16) | ((src[offset + 1] & 0xFF) << 8) | (src[offset] & 0xFF);
        return value;
    }

    /**
     * 16进制转int
     *
     * @param hex
     * @return
     */
    public static int hex2Int(String hex) {
        byte[] b = parseHexStr2Byte(hex);
        return bytes2Int(b, 0);
    }

    public static void main(String[] args) {
        byte[] b = {(byte) 0x29, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        int result = bytes2Int(b, 0);
        System.err.println(result);
    }

    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     *
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * 16进制转换成为string类型字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 字符串转16进制
     *
     * @param str
     * @return
     */
    public static String stringToHex(String str) {
        String arr = "";
        for (int i = 0; i < str.length(); i++) {
            int ch = (int) str.charAt(i);
            String s = Integer.toHexString(ch);
            arr = arr + s;
        }
        return arr;
    }


    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = (byte) Integer.parseInt(inHex.substring(i, i + 2), 16);
            j++;
        }
        return result;
    }

    public static String toStringHex2(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 使字节数组倒叙排序
     *
     * @param bytes
     * @return
     */
    public static byte[] reverse(byte[] bytes) {
        int start = 0;
        int end = bytes.length - 1;
        while (true) {
            if (start >= end) {
                break;
            }
            byte temp = bytes[start];
            bytes[start] = bytes[end];
            bytes[end] = temp;
            start++;
            end--;
        }
        return bytes;
    }

    /**
     * JAVA 10进制转16进制高位在前地位在后
     *
     * @param var0
     * @return
     */
    public static String lowHigh(int var0) {
        int var1 = 1;
        int var2 = var0 >> 8;
        int var3 = var0 & 255;
        String var4 = Integer.toHexString(var2);
        String var5 = Integer.toHexString(var3);
        if (var4.length() > 2) {
            do {
                if (var1 > 1) {
                    var2 >>= 8;
                }
                var4 = Integer.toHexString(var2 >> 8);
                var5 = var5 + Integer.toHexString(var2 & 255);
                ++var1;
            } while (var4.length() > 2);
        }
        if (var4.length() < 2) {
            var4 = "0" + var4;
        }
        if (var5.length() < 2) {
            var5 = "0" + var5;
        }
        return var5 + var4;
    }

}
