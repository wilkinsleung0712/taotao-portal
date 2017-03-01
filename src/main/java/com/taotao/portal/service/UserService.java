package com.taotao.portal.service;

import com.taotao.pojo.TbUser;

public interface UserService {
    
    /**
     * 调用密匙获取用户信息
     * 内部调用SSO API
     * @param token
     * @return
     */
    public TbUser getUserByToken(String token);
}
