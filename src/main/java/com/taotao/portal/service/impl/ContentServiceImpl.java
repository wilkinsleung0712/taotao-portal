package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import com.taotao.util.HttpClientUtils;
import com.taotao.util.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_INDEX_AD_URL}")
    private String REST_INDEX_AD_URL;

    @SuppressWarnings("unchecked")
    @Override
    public String getContentList() {
        // 需要使用httpclient调用taotao-rest的服务
        String jsonString = HttpClientUtils
                .doGet(REST_BASE_URL + REST_INDEX_AD_URL);
        String contentListResultJson = "";
        // 需要把字符串转换成java对象taotaoResult对象
        TaotaoResult resultFromJson = TaotaoResult.formatToList(jsonString,
                TbContent.class);
        // 从taotaoResult对象中取data属性，得到内容列表
        if (resultFromJson != null && resultFromJson.getData() != null) {
            List<TbContent> contentList = (List<TbContent>) resultFromJson
                    .getData();
            // 把内容列表转换成jsp页面要求的json格式。
            // sample:[{"srcB":"http://image.taotao.com/images/2015/03/03/2015030304360302109345.jpg","height":240,"alt":"","width":670,"src":"http://image.taotao.com/images/2015/03/03/2015030304360302109345.jpg","widthB":550,"href":"http://sale.jd.com/act/e0FMkuDhJz35CNt.html?cpdad=1DLSUE","heightB":240},{"srcB":"http://image.taotao.com/images/2015/03/03/2015030304353109508500.jpg","height":240,"alt":"","width":670,"src":"http://image.taotao.com/images/2015/03/03/2015030304353109508500.jpg","widthB":550,"href":"http://sale.jd.com/act/UMJaAPD2VIXkZn.html?cpdad=1DLSUE","heightB":240},{"srcB":"http://image.taotao.com/images/2015/03/03/2015030304345761102862.jpg","height":240,"alt":"","width":670,"src":"http://image.taotao.com/images/2015/03/03/2015030304345761102862.jpg","widthB":550,"href":"http://sale.jd.com/act/UMJaAPD2VIXkZn.html?cpdad=1DLSUE","heightB":240},{"srcB":"http://image.taotao.com/images/2015/03/03/201503030434200950530.jpg","height":240,"alt":"","width":670,"src":"http://image.taotao.com/images/2015/03/03/201503030434200950530.jpg","widthB":550,"href":"http://sale.jd.com/act/kj2pmwMuYCrGsK3g.html?cpdad=1DLSUE","heightB":240},{"srcB":"http://image.taotao.com/images/2015/03/03/2015030304333327002286.jpg","height":240,"alt":"","width":670,"src":"http://image.taotao.com/images/2015/03/03/2015030304333327002286.jpg","widthB":550,"href":"http://sale.jd.com/act/xcDvNbzAqK0CoG7I.html?cpdad=1DLSUE","heightB":240},{"srcB":"http://image.taotao.com/images/2015/03/03/2015030304324649807137.jpg","height":240,"alt":"","width":670,"src":"http://image.taotao.com/images/2015/03/03/2015030304324649807137.jpg","widthB":550,"href":"http://sale.jd.com/act/eDpBF1s8KcTOYM.html?cpdad=1DLSUE","heightB":240}];
            List<Map> jsonListMapData = new ArrayList<>();
            if (contentList != null && !contentList.isEmpty()) {
                for (TbContent tbContent : contentList) {
                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("src", tbContent.getPic());
                    dataMap.put("height", "240");
                    dataMap.put("alt", tbContent.getTitle());
                    dataMap.put("width", "670");
                    dataMap.put("srcB", tbContent.getPic2());
                    dataMap.put("heightB", "240");
                    dataMap.put("widthB", "550");
                    jsonListMapData.add(dataMap);
                }
            }

            contentListResultJson = JsonUtils.objectToJson(jsonListMapData);
        }

        // 返回一个json字符串。
        return contentListResultJson;
    }

}
