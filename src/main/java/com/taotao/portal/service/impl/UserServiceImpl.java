package com.taotao.portal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import com.taotao.util.HttpClientUtils;

@Service
public class UserServiceImpl implements UserService {

    @Value("${SSO_BASE_URL}")
    public String SSO_BASE_URL;

    @Value("${SSO_USER_TOKEN}")
    public String SSO_USER_TOKEN;
    
    @Value("${SSO_PAGE_LOGIN}")
    public String SSO_PAGE_LOGIN;

    @Override
    public TbUser getUserByToken(String token) {
        try {
            if (!StringUtils.isBlank(token)) {
                String result = HttpClientUtils.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
                //在没用用jspn callback时候应该返回taotaoresult
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(result, TbUser.class);
                if (taotaoResult.getStatus() == 200 && null != taotaoResult.getData()) {
                   TbUser user = (TbUser) taotaoResult.getData();
                   if (user != null) {
                       return user;
                   }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }

}
