package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;
import com.taotao.util.HttpClientUtils;
import com.taotao.util.JsonUtils;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;
    @Value("${ORDER_CREATE_URL}")
    private String ORDER_CREATE_URL;

    @Override
    public String createOrder(Order order) {
        // 调用taotao-order的服务提交订单。
        String resultJsonValue = HttpClientUtils.doPostJson(
                ORDER_BASE_URL + ORDER_CREATE_URL,
                JsonUtils.objectToJson(order));
        // 把json转换成taotaoResult
        TaotaoResult result = TaotaoResult.format(resultJsonValue);
        if (null != result && result.getStatus() == 200
                && result.getData() != null) {
            return result.getData().toString();
        }

        return "";
    }

}
