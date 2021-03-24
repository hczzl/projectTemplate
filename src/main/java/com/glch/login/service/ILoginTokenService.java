package com.glch.login.service;

import com.glch.login.entity.LoginToken;
import com.baomidou.mybatisplus.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wenchaochao
 * @since 2020-04-05
 */
public interface ILoginTokenService extends IService<LoginToken> {
    /**
     * 生成token
     *
     * @param request
     * @return
     */
    String createToken(HttpServletRequest request);

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    Map<String, Map> validateToken(String token);

    /**
     * 移除token
     *
     * @param token
     */
    void removeToken(String token);

    /**
     * 插入token
     *
     * @param loginToken
     * @return
     */
    int insertToken(LoginToken loginToken);

    /**
     * 退出登录，删除token
     *
     * @param token
     * @return
     */
    int deleteToken(String token);

    /**
     * 根据token获取到LoginToken
     *
     * @param token
     * @return
     */
    LoginToken getLoginToken(String token);
}
