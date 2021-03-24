package com.glch.login.service.impl;

import com.glch.base.caches.Context;
import com.glch.base.caches.ServContexts;
import com.glch.base.util.DateUtils;
import com.glch.base.util.SpringUtil;
import com.glch.base.util.StringUtil;
import com.glch.base.util.UserAgentUtil;
import com.glch.login.entity.LoginToken;
import com.glch.login.mapper.LoginTokenMapper;
import com.glch.login.service.ILoginService;
import com.glch.login.service.ILoginTokenService;
import com.glch.system.user.entity.User;
import com.glch.system.user.mapper.UserMapper;
import com.glch.system.user.mapper.UserRoleMapper;
import com.glch.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 登录接口的实现类
 *
 * @author zhongzhilong
 * @date 2020/4/6
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ILoginTokenService loginTokenService;
    @Autowired
    private LoginTokenMapper loginTokenMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Map<String, Object> checkLogin(User user, HttpServletRequest request) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userId", user.getId());
        userMap.put("name", user.getName());
        userMap.put("deptId", user.getDeptId());
        userMap.put("deptName", "");
        userMap.put("position", user.getPosition());
        List<Map<String, Object>> list = userRoleMapper.queryByUserId(user.getId());
        for (Map<String, Object> map : list) {
            userMap.put("roleId", map.get("roleId"));
        }
        String token = loginTokenService.createToken(request);
        if (StringUtil.isEmpty(token)) {
            return null;
        }
        userMap.put("token", token);
        //插入token
        LoginToken loginToken = new LoginToken();
        loginToken.setId(token);
        loginToken.setLoginName(user.getLoginName());
        loginToken.setLoginIp(UserAgentUtil.getIp(request));
        loginToken.setCreator(user.getId());
        loginToken.setCreateTime(new Timestamp(System.currentTimeMillis()));
        loginTokenService.insertToken(loginToken);
        User u = new User();
        u.setId(user.getId());
        u.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
        userMapper.updateUser(u);
        return userMap;
    }

    @Override
    public Integer loginState() {
        Integer state = 0;
        String deviceId = "";
        if (!StringUtil.isEmpty(ServContexts.getCurUser())) {
            deviceId = ServContexts.getCurUser().getDeviceId();
        }
        return state;
    }


    /**
     * 判断设备连接时间和当前时间是否超过10分钟
     *
     * @param connecTime 连接时间
     * @return
     */
    public Integer isTimeOut(Date connecTime, Integer state) {
        return state;
    }

    /**
     * 如果连接时间不超过三个月，则授权未失效，否则失败
     *
     * @return
     */
    @Override
    public boolean validateAuthorization() {
        boolean success = false;
        String deviceId = "";
        if (!StringUtil.isEmpty(ServContexts.getCurUser())) {
            deviceId = ServContexts.getCurUser().getDeviceId();
        }
        return success;
    }

    @Override
    public int autoExit(String token) {
        int result = 0;
        if (StringUtil.isEmpty(token)) {
            return result;
        }
        LoginToken loginToken = loginTokenMapper.getLoginToken(token);
        if (null != loginToken) {
            User user = userMapper.selectById(loginToken.getCreator());
            user.setLastLogoutTime(new Timestamp(System.currentTimeMillis()));
            userMapper.updateUser(user);
        }
        result = loginTokenMapper.deleteToken(token);
        return result;
    }

}
