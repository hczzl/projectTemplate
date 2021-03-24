package com.glch.system.user.service;

import com.glch.system.user.entity.User;

public interface IUserService {

    User getUserByLoginName(String loginName, String pwd);

    User selectById(String creator);
}
