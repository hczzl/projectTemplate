package com.glch.login.mapper;

import com.glch.login.entity.LoginToken;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wenchaochao
 * @since 2020-04-05
 */
@Mapper
public interface LoginTokenMapper extends BaseMapper<LoginToken> {
    /**
     * 插入token
     *
     * @param loginToken
     * @return
     */
    int insertToken(LoginToken loginToken);

    /**
     * 删除token
     *
     * @param token
     * @return
     */
    int deleteToken(String token);

    /**
     * 根据登录名获取token信息
     *
     * @param loginName
     * @return
     */
    List<LoginToken> selectTokenByLoginName(String loginName);

    /**
     * 根据token获取到LoginToken
     *
     * @param token
     * @return
     */
    LoginToken getLoginToken(String token);


}
