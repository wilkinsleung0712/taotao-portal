package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
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

    @Value("${ITEM_DESC_URL}")
    private String ITEM_DESC_URL;

    @Value("${ITEM_PARAM_URL}")
    private String ITEM_PARAM_URL;

    @Override
    public ItemInfo getItemById(Long itemId) {
        try {
            // 向taotao-rest发出HTTP请求商品基本信息并得到一个json数据
            String taotaoResultString = HttpClientUtils.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
            // 检查得到的Json数据是否为空
            if (!StringUtils.isBlank(taotaoResultString)) {
                // json数据需要转化成TaotaoResult类型
                // 需要把TbItem类型转化成ItemInfo类型
                TaotaoResult taotaoResultFromJson = TaotaoResult.formatToPojo(taotaoResultString, ItemInfo.class);
                if (taotaoResultFromJson.getStatus() == 200) {
                    // 把TaotaoResult中的data数据取出来
                    ItemInfo itemInfo = (ItemInfo) taotaoResultFromJson.getData();
                    return itemInfo;
                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return null;

    }

    @Override
    public String getItemDescById(Long itemId) {

        try {
            // 向taotao-rest发出HTTP请求商品描述并得到一个json数据
            String itemDescJson = HttpClientUtils.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
            // 检查得到的Json数据是否为空
            if (!StringUtils.isBlank(itemDescJson)) {
                // json数据需要转化成TaotaoResult类型
                // 需要把Object类型转化成TbItemDesc类型
                TaotaoResult result = TaotaoResult.formatToPojo(itemDescJson, TbItemDesc.class);
                if (result.getStatus() == 200) {
                    TbItemDesc tbItemDesc = (TbItemDesc) result.getData();
                    return tbItemDesc.getItemDesc();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getItemParamById(Long itemId) {
        try {
            // 向taotao-rest发出HTTP请求商品描述并得到一个json数据
            String itemParamItemJson = HttpClientUtils.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
            // json数据需要转化成TaotaoResult类型
            // 需要把Object类型转化成TbItemDesc类型
            TaotaoResult result = TaotaoResult.formatToPojo(itemParamItemJson, TbItemParamItem.class);
            if (result.getStatus() == 200) {
                TbItemParamItem tbItemParamItem = (TbItemParamItem) result.getData();
                // 这是储存在数据库的Json格式的paramdata
                String paramData = tbItemParamItem.getParamData();
                // 转化成数据列表
                List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
                // 转化成HTML片段
                StringBuffer  sb=new StringBuffer();
                sb.append("<table cellpadding=\"1\"  cellspacing=\"1\"  width=\"100%\"  border=\"0\"  >\n");
                for(Map m1:jsonList){
                    sb.append("  <tr>\n");
                    sb.append("<th colspan=\"2\">"+m1.get("group")+"</th>\n");
                    sb.append( "</tr>\n");
                    List<Map> list2=(List<Map>) m1.get("params");
                    for (Map m2 : list2) {
                        sb.append("  <tr>\n");
                        sb.append("<th>"+m2.get("k")+"</th>\n");
                        sb.append("<th>"+m2.get("v")+"</th>\n");
                        sb.append( "</tr>\n");          }
                }
                
                sb.append(" </table>");
                return sb.toString();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
