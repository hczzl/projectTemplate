package com.glch.base.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取数据单位工具类
 *
 * @author wenchaochao
 * @since 2020-04-21
 */
public class DataUnitUtil {

    /**
     * 获取数据单位
     *
     * @param digit
     * @return
     */
    public static Map<String, Object> getDigitUnit(Object digit) {
        if (null == digit) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            if (digit instanceof Long) {
                Long data = (Long) digit;
                resultMap = getUnit(data);
            } else if (digit instanceof Integer) {
                Integer data = (Integer) digit;
                resultMap = getUnit(Long.parseLong(data.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return resultMap;
    }

    /**
     * 获取单位
     *
     * @param digit
     * @return
     */
    public static Map<String, Object> getUnit(Long digit) {
        if (null == digit) {
            return new HashMap<>();
        }
        //初始化数据
        Map<String, Object> resultMap = new HashMap<>();
        String unit = "";
        if(digit < 10000){
            resultMap.put("digit",digit);
            resultMap.put("unit",unit);
            return resultMap;
        }
        String data = "";
        int lenght = String.valueOf(digit).length();
        // 将数据转换为字符串，获取其长度
        for (int i = lenght; i > 0; i--) {
            Long divisor = getDivisor(i);
            long temp = digit % divisor;
            if (i == lenght) {
                data = digit / getDivisor(i - 1) + ".";
            } else {
                data += String.valueOf(temp).substring(0, 1);
            }
        }
        if (10000 <= digit && digit <= 999999) {
            unit = "万";
        } else if (1000000 <= digit && digit <= 9999999) {
            unit = "百万";
        } else if (10000000 <= digit && digit <= 99999999) {
            unit = "千万";
        } else if (100000000 <= digit && digit <= 9999999999L) {
            unit = "亿";
        } else if (10000000000L <= digit && digit <= 99999999999L) {
            unit = "十亿";
        } else if (100000000000L <= digit && digit <= 999999999999L) {
            unit = "百亿";
        } else if (1000000000000L <= digit && digit <= 9999999999999L) {
            unit = "千亿";
        }
        //保留小数点后两位
        DecimalFormat df = new DecimalFormat("0.00");
        resultMap.put("digit", df.format(Double.valueOf(data)));
        /*BigDecimal bd = new BigDecimal(data);
        resultMap.put("digit",bd.setScale(2,BigDecimal.ROUND_FLOOR).doubleValue());*/
        resultMap.put("unit", unit);
        return resultMap;
    }

    /**
     * 根据位数获取除数
     *
     * @param length
     * @return
     */
    public static Long getDivisor(int length) {
        if (length <= 0) {
            return null;
        }
        String diviisor = "1";
        for (int i = 0; i < length; i++) {
            diviisor += "0";
        }
        return Long.parseLong(diviisor);
    }

    public static void main(String[] args) {
        int digit = 0;
        Map<String, Object> map = getDigitUnit(digit);
        System.err.println(map);
    }
}
