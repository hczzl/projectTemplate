package com.glch.system.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.glch.login.entity.LoginToken;
import com.glch.login.mapper.LoginTokenMapper;
import com.glch.system.user.entity.User;
import com.glch.system.user.mapper.UserMapper;
import com.glch.system.user.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User getUserByLoginName(String loginName, String pwd) {

        //加密验证
        User user = this.baseMapper.getUserByLoginName(loginName);
        if(user != null && pwd.equals(user.getPwd())){
            return user;
        }
        return null;
    }

    @Override
    public User selectById(String creator) {
        return null;
    }
}
