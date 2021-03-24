package com.glch.socket;


import com.alibaba.fastjson.JSONObject;
import com.glch.base.util.StringUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Test2 {
    public static void main(String[] args) {
        Student student = new Student();
        student.setId(10);
        student.setName("小明");
        byte[]bytes = StringUtil.ObjectToByte(student);
        System.out.println("byte的长度="+bytes.length);
    }

    static class Student implements Serializable {
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
