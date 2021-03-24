package com.glch.login.service.impl;

import com.glch.base.caches.Context;
import com.glch.base.caches.ServContexts;
import com.glch.base.util.DateUtils;
import com.glch.base.util.StringUtil;
import com.glch.base.util.UserAgentUtil;
import com.glch.login.entity.LoginToken;
import com.glch.login.mapper.LoginTokenMapper;
import com.glch.login.service.ILoginTokenService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.glch.system.user.entity.User;
import com.glch.system.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * token 服务实现类
 * </p>
 *
 * @author wenchaochao
 * @since 2020-04-05
 */
@Service
public class LoginTokenServiceImpl extends ServiceImpl<LoginTokenMapper, LoginToken> implements ILoginTokenService {

    @Autowired
    private LoginTokenMapper loginTokenMapper;
    @Autowired
    private UserMapper userMapper;

    private Map<String, Map> tokens = new HashMap();

    @Override
    public String createToken(HttpServletRequest request) {
        String key = DateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS") + StringUtil.createId();
        Map tokenInfo = new HashMap();
        tokenInfo.put("ip", UserAgentUtil.getIp(request));
        tokenInfo.put("sessionId", request.getSession().getId());
        tokens.put(key, tokenInfo);
        return key;
    }

    @Override
    public Map<String, Map> validateToken(String token) {
        return tokens.get(token);
    }

    @Override
    public void removeToken(String token) {
        tokens.remove(token);
    }

    @Override
    public int insertToken(LoginToken loginToken) {
        return loginTokenMapper.insertToken(loginToken);
    }

    @Override
    public int deleteToken(String token) {
        LoginToken loginToken = loginTokenMapper.getLoginToken(token);
        User user = userMapper.selectById(loginToken.getCreator());
        user.setLastLogoutTime(new Timestamp(System.currentTimeMillis()));
        userMapper.updateUser(user);
        return loginTokenMapper.deleteToken(token);
    }

    @Override
    public LoginToken getLoginToken(String token) {
        return loginTokenMapper.getLoginToken(token);
    }
}
