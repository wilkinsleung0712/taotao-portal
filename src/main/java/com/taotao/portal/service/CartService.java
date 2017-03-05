package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

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
    
    /**
     * 根据网页请求获取已有购物车信息
     * 
     * @param request
     * @return
     */
    public List<CartItem> getCartItemList(HttpServletRequest request);
}
