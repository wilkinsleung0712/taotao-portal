package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.util.CookieUtils;
import com.taotao.util.HttpClientUtils;
import com.taotao.util.JsonUtils;

@Service
public class CartServiceImpl implements CartService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_ITEM_INFO_URL}")
    private String REST_ITEM_INFO_URL;

    @Override
    public TaotaoResult addCartItemToCart(Long itemId, Integer num, HttpServletRequest request,
            HttpServletResponse response) {
        // 需要调用已有的产品查找服务
        // taotao-rest API 方法
        CartItem cartItem = null;
        // 提取现有的CART
        List<CartItem> itemList = getCartItemList(request);

        // 查询是否已经有商品存在购物车，只需要把购买数量相加
        for (CartItem aCartItem : itemList) {
            if (aCartItem.getId() == itemId) {
                // 已经有商品存在购物车，只需要把购买数量相加
                aCartItem.setNum(aCartItem.getNum() + num);
                // 这里赋值告诉下面的代码已经找到了已有的商品
                cartItem = aCartItem;
                // 不需要写进购物车，因为购物车是list不能限制唯一值
                break;
            }
        }

        // 这里没有赋值说明上面的代码没有找到已有的商品
        if (cartItem == null) {
            // 没有在已有的购物车查到商品，需要新增加商品至购物车
            String jsonResult = HttpClientUtils.doGet(REST_BASE_URL + REST_ITEM_INFO_URL + itemId);
            TaotaoResult result = TaotaoResult.formatToPojo(jsonResult, TbItem.class);
            // 检查得到的回复是否为有效
            if (null != result && result.getStatus() == 200) {
                cartItem = new CartItem();
                // 提取商品信息并优化信息以便放入客户cookies
                TbItem tbItem = (TbItem) result.getData();
                cartItem.setId(tbItem.getId());
                cartItem.setImage(tbItem.getImage());
                cartItem.setNum(num);
                cartItem.setPrice(tbItem.getPrice());
                cartItem.setTitle(tbItem.getTitle());
                // 把商品写进购物车
                itemList.add(cartItem);
            }
        }

        // 把商品信息打包成json格式放进cookies
        // 如果购物车已有商品这里会更新购物车信息
        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList));

        return TaotaoResult.ok();
    }

    /**
     * 根据网页请求获取已有购物车信息
     * 
     * @param request
     * @return
     */
    private List<CartItem> getCartItemList(HttpServletRequest request) {
        // 根据Request提取cookies内容
        List<CartItem> cartList = new ArrayList<>();
        String cookiesJson = CookieUtils.getCookieValue(request, "TT_CART");
        if (!StringUtils.isBlank(cookiesJson)) {
            cartList = JsonUtils.jsonToList(cookiesJson, CartItem.class);
        }
        return cartList;
    }

    @Override
    public TaotaoResult removeCartItemFromCart(Long itemId, HttpServletRequest request, HttpServletResponse response) {

        List<CartItem> cartList = getCartItemList(request);
        // 如果购物车里没有任何东西
        if (cartList == null || cartList.isEmpty()) {
            return TaotaoResult.ok();
        }

        CartItem temp = null;

        for (CartItem cartItem : cartList) {
            if (cartItem.getId() == itemId) {
                // 如果购物车里有这款商品
                temp = cartItem;
            }
        }
        // 如果购物车里没有这款商品
        cartList.remove(temp);
        // 更新购物车内容
        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartList));
        
        return TaotaoResult.ok();
    }

}
