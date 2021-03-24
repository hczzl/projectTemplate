package com.glch.base.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;

public class JsonResponse extends HashMap<String, Object> {

    public final static Logger logger = LoggerFactory.getLogger(JsonResponse.class);
    public static final String CODE = "statusCode";
    public static final String MESSAGE = "msg";
    public static final String ERROR = "error";
    public static final String DATA = "data";
    //返回代码
    public static final Integer OK = 0;
    public static final Integer ServerError = 500;
    public static final Integer TokenError = 20;
    public static final Integer UrlError = 404;

    public JsonResponse() {
        put(CODE, OK);
    }


    public static JsonResponse error(Exception e) {
        if (e instanceof BizException) {
            return error(ServerError, ((BizException) e).toString(), ((BizException) e).getMessage());
        }
        if (e == null || e.getMessage() == null) {
            return error(ServerError, "未知异常,请联系管理员");
        }
        return error(ServerError, e.toString(), e.getMessage());
    }

    public static JsonResponse serverError(String msg) {
        return error(ServerError, msg);
    }

    public static JsonResponse tokenError() {
        return error(TokenError, "请先登录");
    }

    public static JsonResponse urlError() {
        return error(UrlError, "无效的路径，请检查路径是否正确");
    }

    public static JsonResponse codeMsg(int code, String msg) {
        JsonResponse result = new JsonResponse();
        result.put(CODE, code);
        result.put(MESSAGE, msg);
        return result;
    }

    public static JsonResponse error(String msg) {
        JsonResponse result = codeMsg(ServerError, msg);
        result.put(ERROR, msg);
        return result;
    }

    public static JsonResponse error(int code, String msg) {
        JsonResponse result = codeMsg(code, msg);
        result.put(ERROR, msg);
        return result;
    }

    public static JsonResponse error(int code, String error, String msg) {
        JsonResponse result = codeMsg(code, msg);
        result.put(MESSAGE, msg);
        return result;
    }

    /**
     * 请求成功
     * @param msg 消息
     * @return
     */
    public static JsonResponse ok(String msg) {
        JsonResponse result = new JsonResponse();
        result.put(MESSAGE, msg);
        return result;
    }

    /**
     * 请求成功
     * @param code 返回码
     * @param msg 消息
     * @return
     */
    public static JsonResponse ok(int code, String msg) {
        JsonResponse result = new JsonResponse();
        result.put(CODE, code);
        result.put(MESSAGE, msg);
        return result;
    }

    /**
     * 请求成功
     * @param obj 返回数据
     * @return
     */
    public static JsonResponse ok(Object obj) {
        JsonResponse result = new JsonResponse();
        result.put("data", obj);
        return result;
    }

    public static JsonResponse ok(String msg, Object obj) {
        JsonResponse result = new JsonResponse();
        result.put(MESSAGE, msg);
        result.put("data", obj);
        return result;
    }

    public static JsonResponse ok(Map<String, Object> map) {
        JsonResponse result = new JsonResponse();
        result.put(DATA, map);
        return result;
    }

    public static JsonResponse ok(List<Object> list) {
        JsonResponse result = new JsonResponse();
        result.put(CODE, OK);
        result.put("list", list);
        return result;
    }

    public static JsonResponse ok(String msg, Map<String, Object> map) {
        JsonResponse result = codeMsg(OK, msg);
        result.put(DATA, map);
        return result;
    }

    public static JsonResponse ok() {
        JsonResponse result = new JsonResponse();
        return result;
    }

    @Override
    public JsonResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public JSONObject toJson() {
        return new JSONObject(this);
    }
}
