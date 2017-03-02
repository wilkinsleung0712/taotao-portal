package com.taotao.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.service.CartService;

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

        return "redirect:/cart/success";
    }

    @RequestMapping("/success")
    public String showSuccess() {
        return "cartSuccess";
    }

    @RequestMapping("/test")
    public String showTest() {
        return "cartSuccess";
    }

}
