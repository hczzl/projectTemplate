package com.glch.base.caches;

import com.glch.base.util.StringUtil;
import com.glch.system.user.entity.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Context implements Serializable, Cloneable {
    /**
     *
     */
    private static final long serialVersionUID = 867363509179376364L;
    private String token;
    //当前用户
    private User curUser;
    private Map<String, Object> cacheInfo = new HashMap();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getCurUser() {
        return curUser;
    }

    public void setCurUser(User curUser) {
        this.curUser = curUser;
    }


}
