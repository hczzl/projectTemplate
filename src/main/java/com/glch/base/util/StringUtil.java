package com.glch.base.util;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {

    /*
     * 获取uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /*
     * 实体类转map
     */
    public static Map<String, Object> ConvertObjToMap(Object obj) throws Exception {
        Map<String, Object> reMap = new HashMap<String, Object>();
        if (obj == null)
            return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {

            Field f = obj.getClass().getDeclaredField(fields[i].getName());
            f.setAccessible(true);
            Object o = f.get(obj);
            reMap.put(fields[i].getName(), o);
        }

        return reMap;
    }

    /*
     *  为空则返回true，不否则返回false
     */
    public static boolean isEmpty(Object s) {
        return s == null || "".equals(s) || "null".equals(s) || s == "";
    }

    /*
     * 左补字符
     */
    public static String addZeroForNum(String str, int length, String info) throws Exception {
        int strLen = 0;

        strLen = str.getBytes("UTF-8").length;

        if (strLen < length) {
            while (strLen < length) {
                StringBuffer sb = new StringBuffer();
                sb.append(info).append(str);
                str = sb.toString();
                strLen = str.length();
            }
        }

        return str;
    }

    private static final String RANDOM_NUMBER = "0123456789";
    //身份正则
    public static String ID_CARD_REG = "(([1-9]\\d{5})([12]{1})(\\d{3})(01|02|03|04|05|06|07|08|09|10|11|12)([0123]{1})(\\d{1})(\\d{3})(\\d|X))";
    //车牌号正则
    public static String CAR_CARD_REG = "[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-IK-VX-Z]{1}[A-Z]{1}[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}";

    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0 || s.trim().equals("undefined");
    }

    public static String encodeUrl(String str)
            throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "utf-8");
    }

    public static String decodeUrl(String str)
            throws UnsupportedEncodingException {
//		return URLDecoder.decode(str.replaceAll("%2B", "+").replaceAll("%3D",
//				"="), "utf-8");
        return URLDecoder.decode(str, "utf-8");
    }

    public static String getParentLongNumber(String selfLongNumber,
                                             String splitChar) {
        if (!isEmpty(selfLongNumber)
                && selfLongNumber.split(splitChar).length > 0) {
            String[] ss = selfLongNumber.split(splitChar);
            StringBuffer parentLongNumber = new StringBuffer();
            for (int i = 0, len = ss.length - 1; i < len; i++) {
                parentLongNumber.append((i == 0 ? "" : splitChar) + ss[i]);
            }
            return parentLongNumber.toString();
        } else {
            return null;
        }
    }

    synchronized public static String createId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static byte[] getBytes(char[] chars) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }

    public static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    public static String formatThrowableStackInfo(Throwable e) {
        StackTraceElement[] els = e.getStackTrace();
        StringBuffer msg = new StringBuffer(formatThrowableInfo(e));
        for (StackTraceElement el : els) {
            msg.append("at [" + el.getClassName() + "." + el.getMethodName() + ", in source " + el.getFileName() + ",line " + el.getLineNumber() + "]<br/>");
        }
        return msg.toString();
    }

    public static String formatThrowableInfo(Throwable e) {
        StringBuffer sb = new StringBuffer(e.toString() + "<br/>");
        if (e.getCause() != null) {
            sb.append("caused by " + e.getCause().toString() + "<br/>");
        }
        return sb.toString();
    }

    /**
     * 创建随机数，由0-9数字随机组成
     *
     * @param len 随机数长度
     * @return 随机数
     */
    public static String randomNumber(int len) {
        if (len <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Random radom = new Random();
        for (int r = 0; r < len; r++) {
            // 返回[0 62)之间的随机int数
            int at = radom.nextInt(10);
            // 从 RANDOM_STRING 中取出随机的字符
            sb.append(RANDOM_NUMBER.charAt(at));
        }
        return sb.toString();
    }

    /**
     * 验证是否为手机号码
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone == null || phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    public static String toString(String str) {
        if (str.isEmpty() || str == null) {
            return "";
        } else {
            return str.toString();
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(encodeUrl("+"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 转换为String数组<br>
     *
     * @param split 被转换的值
     * @return 结果
     */
    public static String[] toStrArray(String str) {
        return toStrArray(",", str);
    }

    /**
     * 转换为String数组<br>
     *
     * @param split 分隔符
     * @param split 被转换的值
     * @return 结果
     */
    public static String[] toStrArray(String split, String str) {
        return str.split(split);
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * 去重
     *
     * @param list1
     * @param list2
     * @return
     */
    public static List<String> removeDuplicate(List<Map<String, Object>> list1, List<Map<String, Object>> list2) {
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            Map<String, Object> map = list1.get(i);
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String context = (String) iterator.next();
                resultList.add(context);
            }
        }
        for (int i = 0; i < list2.size(); i++) {
            Map<String, Object> map = list2.get(i);
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String context = (String) iterator.next();
                resultList.add(context);
            }
        }
        List<String> list = resultList.stream().distinct().collect(Collectors.toList());
        return list;
    }

    /**
     * 判断list不为空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List<?> list) {
        boolean yes = false;
        if (list.size() > 0 && !list.isEmpty() && list != null) {
            yes = true;
        }
        return yes;
    }

    /**
     * 字符串数组排序
     *
     * @param input
     * @return
     */
    public static String[] arraySort(String[] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length - i - 1; j++) {
                if (input[j].compareTo(input[j + 1]) > 0) {
                    String temp = input[j];
                    input[j] = input[j + 1];
                    input[j + 1] = temp;
                }
            }
        }
        return input;
    }

    public static String[] removeEmpty(String[] str) {
        List<String> strList = Arrays.asList(str);
        List<String> newList = new ArrayList<>();
        for (int i = 0; i < strList.size(); i++) {
            if (strList.get(i) != null && !strList.get(i).equals("")) {
                newList.add(strList.get(i));
            }
        }
        String[] strArray = newList.toArray(new String[newList.size()]);
        return strArray;
    }

    public byte[] stringTurnByte(String obj) {
        byte[] bytes = obj.getBytes();
        return bytes;
    }

    public static byte[] ObjectToByte(java.lang.Object obj) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            byte[] bytes = bo.toByteArray();
            bo.close();
            oo.close();
            System.out.println("长度="+bytes.length);
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
