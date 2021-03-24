package com.glch;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        // foreach循环方法
        list.forEach(item -> {
            System.out.println("元素=" + item);
        });
        // list.stream().fi
    }
}
