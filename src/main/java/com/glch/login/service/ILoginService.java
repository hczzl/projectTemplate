package com.glch.login.service;

import com.glch.system.user.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 登录接口
 *
 * @author zhongzhilong
 * @date 2020/4/6
 */
public interface ILoginService {
    /**
     * 验证登录，返回用户基本信息
     *
     * @param user
     * @return
     */
    Map<String, Object> checkLogin(User user, HttpServletRequest request);

    /**
     * 验证当前用户的登录状态:在线、离线
     * 登录后验证
     *
     * @return
     */
    Integer loginState();

    /**
     * 验证授权是否过期
     * 登录前验证
     *
     * @return
     */
    boolean validateAuthorization();

    /**
     * token失效自动退出
     * @param token 用户登录token
     * @return
     */
    int autoExit(String token);

}
