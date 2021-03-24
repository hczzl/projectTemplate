package com.glch;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


class WxServApplicationTests {
    public static void main(String[] args) throws ParseException {
      List<Map<String,Object>> list = new ArrayList<>();
      Map<String,Object> map1 = new HashMap<>();
      map1.put("001",001);
      Map<String,Object> map2 = new HashMap<>();
      map2.put("0002",002);
      list.add(map1);
      list.add(map2);
      System.out.println("移除的数据"+list.remove(0));

      System.out.println("移除后的数据"+list.toString());
    }
}
