package com.glch.system.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.glch.system.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper  extends BaseMapper<User> {

    int updateUser(User user);

    User getUserByLoginName(String loginName);
}
