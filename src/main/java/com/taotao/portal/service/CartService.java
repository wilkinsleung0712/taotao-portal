package com.taotao.portal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.pojo.TaotaoResult;

public interface CartService {

    /**
     * 根据产品id获取产品信息
     * 
     * @param itemId
     * @return
     */
    public TaotaoResult addCartItemToCart(Long itemId, Integer num, HttpServletRequest request,
            HttpServletResponse response);


    /**
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    public TaotaoResult removeCartItemFromCart(Long itemId, HttpServletRequest request, HttpServletResponse response);
}
