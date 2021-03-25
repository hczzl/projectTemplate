package com.glch;

import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhongzhilong
 * @date 2021-03-25
 * @description 对一个列表进行两两组合测试
 */
public class Test {
    public static void main(String[] args) {
        // List<Pair<String, String>> pairs = combineTwoElement(Lists.newArrayList("1", "2", "3", "4"));
        List<Pair<String, String>> pairs = combineTwoElement(Arrays.asList("1", "2", "3", "4"));
        List<Pair<String, String>> pairs1 = combineTwoElement(Arrays.asList("1", "2", "3", "4", "5"));
        for (Pair<String, String> pair : pairs) {
            System.out.println("pair = "+pair);
        }
        System.out.println(pairs);
        System.out.println(pairs1);

    }

    public static List<Pair<String, String>> combineTwoElement(List<String> list) {
        // List<Pair<String, String>> resultList = Lists.newArrayListWithExpectedSize(list.size() * list.size() / 2);
        // if (CollectionUtils.isEmpty(list)) {
        //    return null;
        // }
        List<Pair<String, String>> resultList = new ArrayList<>();
        if (list.size() >= 2) {
            for (int j = 0; j < list.size(); j++) {
                resultList.addAll(ll(list.subList(j, list.size())));
            }

        }
        return resultList;
    }


    public static List<Pair<String, String>> ll(List<String> list) {
        // 提交计算List的大小
        // List<Pair<String, String>> resultList = Lists.newArrayListWithExpectedSize(list.size() * list.size() / 2);
        List<Pair<String, String>> resultList = new ArrayList<>();
        for (int j = 1; j < list.size(); j++) {
            resultList.add(new Pair<>(list.get(0), list.get(j)));
        }
        return resultList;
    }

}
