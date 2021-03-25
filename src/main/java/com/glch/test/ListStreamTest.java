package com.glch.test;

import com.glch.base.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhongzhilong
 * @date 2021-03-25
 * @description List的stream功能测试
 */
public class ListStreamTest {
    public static void main(String[] args) {
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("小狗", 3, 1));
        list.add(new Animal("小猫", 4, 0));
        list.add(new Animal("小马", 5, 1));
        // 获取所有动物的名称，并且转为List
        List<String> nameList = list.stream().map(Animal::getName).collect(Collectors.toList());
        // 获取所有的动物名称，并且中间加入name不等于空的过滤，并且转为set
        Set<String> set = list.stream().filter(animal -> !StringUtil.isEmpty(animal.getName())).map(animal -> animal.getName()).collect(Collectors.toSet());
        // 简洁版本
        Set<String> set2 = list.stream().map(animal -> animal.getName()).collect(Collectors.toSet());
        // 获取tag = 1的动物列表
        List<Animal> tagList = list.stream().filter(animal -> animal.getTag() == 1).collect(Collectors.toList());
        // 获取动物的总年龄之和
        int sum = list.stream().mapToInt(Animal::getAge).sum();
    }

    static class Animal {
        private String name;
        private Integer age;
        private Integer tag;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public Integer getTag() {
            return tag;
        }

        public void setTag(Integer tag) {
            this.tag = tag;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Animal(String name, Integer age, Integer tag) {
            this.name = name;
            this.age = age;
            this.tag = tag;
        }
    }
}
