package com.taotao.portal.service;

import com.taotao.portal.pojo.ItemInfo;

public interface ItemService {

    /**
     * 获取商品基本信息
     * @param itemId
     * @return
     */
    public ItemInfo getItemById(Long itemId); 
    
    /**
     * 获取商品描述
     * @param itemId
     * @return
     */
    public String getItemDescById(Long itemId);
}
