package com.glch.study.study05.test.controller;

import com.glch.study.study05.handler.MyException;
import org.apache.http.HttpStatus;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzl
 * @Date 2022/7/3
 * @description
 */
@RestController
public class ExceptionHandleControllerTest {

    @GetMapping("/exceptionTest/{id}")
    public String exceptionTest(@PathVariable("id") int id) {
        if (id == 1) {
            throw new MyException(HttpStatus.SC_BAD_REQUEST,"ERROR");
        }
        return "success";
    }
}
