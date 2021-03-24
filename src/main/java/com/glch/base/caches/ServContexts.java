package com.glch.base.caches;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.glch.base.util.StringUtil;
import com.glch.login.entity.LoginToken;
import com.glch.login.service.ILoginTokenService;
import com.glch.system.user.entity.User;
import com.glch.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.glch.base.util.SpringUtil;

public class ServContexts implements Serializable, Cloneable {
    /**
     *
     */
    private static final long serialVersionUID = -1922672131334054621L;
    private static Map<String, Context> contexts = new HashMap();

    /*
     * 获取当前会话的请求
     */
    public static HttpServletRequest getCurRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /*
     * 获取当前会话的上下文
     */
    public static Context getCurContext() {
        if (getCurRequest().getAttribute("context") == null) {
            return null;
        }
        Context cx = (Context) getCurRequest().getAttribute("context");
        return cx;
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public static User getCurUser() {
        Context context = (Context) getCurRequest().getAttribute("context");
        if (StringUtil.isEmpty(context)) {
            return null;
        }
        return context.getCurUser();
    }

    /*
     * 根据token获取会话信息
     */
    public static Context getContext(String token) {
        if (contexts.containsKey(token)) {
            return contexts.get(token);
        } else {
            return null;
        }
    }

    /*
     * 根据token获取数据
     */
    public static void addContext(String token, Context ctx) {
        if (contexts.containsKey(token)) {
            contexts.remove(token);
        }
        contexts.put(token, ctx);
    }

    /*
     * 查询会话是否存在
     */
    public static boolean isExist(String token) {
        boolean b = false;
        b = contexts.containsKey(token);
        if (b == false) {
            ILoginTokenService loginTokenService = SpringUtil.getBean(ILoginTokenService.class);
            LoginToken loginToken = loginTokenService.getLoginToken(token);
            if (loginToken != null) {
                //恢复会话
                IUserService userService = SpringUtil.getBean(IUserService.class);
                User user = userService.selectById(loginToken.getCreator());
                Context context = new Context();
                context.setToken(token);
                context.setCurUser(user);
                addContext(token, context);
                b = true;
            }
        }
        return b;
    }
}
