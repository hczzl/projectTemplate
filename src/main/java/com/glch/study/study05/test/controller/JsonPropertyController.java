package com.glch.study.study05.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.glch.study.study05.test.domain.Stu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zzl
 * @Date 2022/7/3
 * @description @JsonProperty 注解的使用的测试控制器
 */
@RestController
public class JsonPropertyController {
    private static final Logger LOG = LoggerFactory.getLogger(JsonPropertyController.class);

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getStu")
    public String getStu() {
        Stu stu = new Stu();
        stu.setID("123");
        stu.setNAME("JsonProperty");
//        Map map =new HashMap();
//        map.put("ID","123");
//        map.put("NAME","JsonProperty");
        HttpEntity httpEntity = new HttpEntity(stu);
        JSONObject result = restTemplate.postForObject("http://localhost:9089/selectStu", httpEntity, JSONObject.class);
        return result.getString("ID")+","+result.getString("NAME");

    }

    @PostMapping("/selectStu")
    public JSONObject selectStu(@RequestBody JSONObject object) {
        LOG.info("ENTER | selectStu. -> {}", object);
        return object;
    }

}
