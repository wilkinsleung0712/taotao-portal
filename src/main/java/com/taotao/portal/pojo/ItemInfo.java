package com.taotao.portal.pojo;

import org.apache.commons.lang3.StringUtils;

import com.taotao.pojo.TbItem;

public class ItemInfo extends TbItem {

    public String[] getImages(){
        String image = this.getImage();
        if (StringUtils.isBlank(image)) {
            return image.split(",");
        }
        return null;
    }
}
