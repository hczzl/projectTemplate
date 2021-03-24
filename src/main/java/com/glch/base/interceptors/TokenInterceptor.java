package com.glch.base.interceptors;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.glch.base.util.RedisUtil;
import com.glch.login.service.ILoginService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.glch.base.caches.ServContexts;
import com.glch.base.common.JsonResponse;
import com.glch.base.util.StringUtil;

/**
 * token拦截器
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Value("#{'${myfilter.unFilterUrls}'.split(' - ')}")
    private List<String> unFilterUrls;

    /*@Value("${expireTime}")
    private Integer expireTime;*/

    @Resource
    private RedisUtil redisUtil;

    public List<String> getUnFilterUrls() {
        return unFilterUrls;
    }

    public void setUnFilterUrls(List<String> unFilterUrls) {
        this.unFilterUrls = unFilterUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 日志id
        MDC.put("seqNo", StringUtil.uuid());

        String url = request.getRequestURL().toString();
        //判断是否需要拦截
        if (!isNoValidate(url)) {
            //需要拦截
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter outWriter = null;
            String token = getToken(request);
            //String token = "20200523145112650872476d8b6644619a8cf9b62e9479932";
            if (!StringUtil.isEmpty(token)) {
                token = token.replaceAll("Bearer ", "");
            }
            if (token == null || token.length() < 1 ||
                    !ServContexts.isExist(token)
                    ) {
                JsonResponse jsonResponse = JsonResponse.tokenError();
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT,DELETE");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers",
                        "Content-Type,Access-Control-Allow-Headers,Authorization,X-Requested-Width");
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setContentType("application/json;charset=UTF-8");
                outWriter = response.getWriter();
                outWriter.write(jsonResponse.toJson().toString());
                outWriter.flush();
                outWriter.close();
                return false;
            } else {
                // 如果token有效，则将token存入上下文中
                request.setAttribute("context", ServContexts.getContext(token));
                //如果token有效，则将token存入到缓存，设置失效时间,默认30分钟
                boolean redisToken = redisUtil.hasKey(token);
                if (redisToken) {
                    redisUtil.expire(token, 30, TimeUnit.MINUTES);
                } else {
                    redisUtil.setObj(token, token, 30, TimeUnit.MINUTES);
                }
            }
        }
        //不需要拦截
        return true;
    }

    /*
     * 判断是否有token的请求
     * by zend
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        /*if (token == null || token.length() < 1) {
            token = request.getParameter("token");
        }*/
        return token;
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {

    }

    private boolean isNoValidate(String url) {
        boolean noValidate = false;
        if (url != null && url.length() > 0) {
            for (String s : unFilterUrls) {
                if (url.indexOf(s) >= 0) {
                    noValidate = true;
                    break;
                }
            }
        }
        return noValidate;
    }
}
