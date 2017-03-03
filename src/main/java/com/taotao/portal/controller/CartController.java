package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.util.CookieUtils;
import com.taotao.util.JsonUtils;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/add/{itemId}")
    public String addItemToCartById(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            TaotaoResult result = cartService.addCartItemToCart(itemId, num, request, response);
            if (result.getStatus() != 200) {
                // 加载失败需要返回一个错误页面

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // 发生错误需要返回一个错误页面
            e.printStackTrace();
        }
        // 在跳转URI尾部加上HTML因为在web.xml用了伪静态化HTML
        return "redirect:/cart/success.html";
    }

    @RequestMapping("/delete/{itemId}")
    public String deleteCartItemById(@PathVariable Long itemId, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            TaotaoResult result = cartService.removeCartItemFromCart(itemId, request, response);
            if (result.getStatus() != 200) {
                // 加载失败需要返回一个错误页面

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // 在跳转URI尾部加上HTML因为在web.xml用了伪静态化HTML
        return "redirect:/cart/cart.html";
    }

    @RequestMapping("/cart")
    public String showCart(Model model, HttpServletRequest request) {
        String cookieValueJson = CookieUtils.getCookieValue(request, "TT_CART");
        List<CartItem> cartList = JsonUtils.jsonToList(cookieValueJson, CartItem.class);
        model.addAttribute("cartList", cartList);
        return "cart";
    }

    @RequestMapping("/success")
    public String showSuccess() {
        return "cartSuccess";
    }

}
