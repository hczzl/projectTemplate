package com.glch.base.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.glch.study.study05.handler.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.glch.base.common.JsonResponse;

/**
 * 异常拦截
 */
//@ControllerAdvice
public class CustomInterceptAdvice {
    public final Logger logger = LoggerFactory.getLogger(getClass());

    /*
     * 异常拦截
     */
    @ExceptionHandler
    @ResponseBody
    public Object handle(Exception e) {
        logger.error(e.getMessage(), e);
        return JsonResponse.error(e);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handlerNoFoundException(Exception e) {
        logger.error(e.getMessage(), e);

        return JsonResponse.urlError();
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Object handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return JsonResponse.serverError("数据库中已存在该记录");
    }

    @ExceptionHandler(MyException.class) // 指名处理MyException这种异常
    @ResponseBody
    public JsonResponse resolveMyException(MyException ex) {
        ex.printStackTrace();
        // 打印完整的异常信息
        JSONObject object = new JSONObject();
        object.put("code", ex.getCode());
        object.put("msg", ex.getMsg());
        return JsonResponse.ok(object);
    }
}
