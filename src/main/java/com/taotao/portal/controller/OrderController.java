package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CartService cartService;
    
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order-cart")
    public String getOrderCart(HttpServletRequest request,
            HttpServletResponse response, Model model) {
        List<CartItem> cartItemList = cartService.getCartItemList(request);
        model.addAttribute("cartList", cartItemList);

        // TODO
        // 在实际的商城中是必须有此功能，需要taotao-rest发布服务，由taotao-portal根据用户查询用户的收货地址列表。
        // 在此，使用静态数据模拟。

        return "order-cart";
    }
    
    @RequestMapping("/create")
    public String createOrder(Order order, Model model){
        try {
            String orderId = orderService.createOrder(order);
            if (!StringUtils.isBlank(orderId)) {
                model.addAttribute("orderId",orderId);
                model.addAttribute("payment", order.getPayment());
                model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }
}
