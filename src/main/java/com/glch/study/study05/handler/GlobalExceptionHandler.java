package com.glch.study.study05.handler;

import com.alibaba.fastjson.JSONObject;
import com.glch.base.common.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 *
 * @author zzl
 * @Date 2022/7/3
 * @description
 */
@ControllerAdvice // 保证所有的控制器都可以使用全局异常处理器
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义的异常
     * 同理，若想处理其他异常，也需单独定义即可
     *
     * @return
     */
    @ExceptionHandler(MyException.class) // 指名处理MyException这种异常
    @ResponseBody
    public JsonResponse resolveMyException(MyException ex) {
        // 打印完整的异常信息
        ex.printStackTrace();
        JSONObject object = new JSONObject();
        object.put("code", ex.getCode());
        object.put("msg", ex.getMsg());
        log.info("EXCEPTION | msg -> {}", ex.getMsg());
        return JsonResponse.ok(object);
    }
}
