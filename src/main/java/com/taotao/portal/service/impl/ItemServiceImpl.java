package com.taotao.portal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import com.taotao.util.HttpClientUtils;
import com.taotao.util.JsonUtils;

@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;

    @Override
    public ItemInfo getItemById(Long itemId) {
        try {
            String taotaoResultString = HttpClientUtils.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
            //
            TaotaoResult taotaoResultFromJson = JsonUtils.jsonToPojo(taotaoResultString, TaotaoResult.class);
            //
            String dataJson = JsonUtils.objectToJson(taotaoResultFromJson.getData());
            //
            if (!StringUtils.isBlank(dataJson)) {
                ItemInfo itemInfo = JsonUtils.jsonToPojo(dataJson, ItemInfo.class);
                return itemInfo;
            }

        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return null;

    }

}
