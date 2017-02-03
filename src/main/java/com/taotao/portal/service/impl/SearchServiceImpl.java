package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchServices;
import com.taotao.util.HttpClientUtils;
import com.taotao.util.JsonUtils;

@Service
public class SearchServiceImpl implements SearchServices {

    /**
     * Taotao-search 服务基本URI
     */
    @Value("${SEARCH_BASE_URI}")
    private String SEARCH_BASE_URI;

    @Override
    public SearchResult search(String queryString, Integer pages, Integer rows) {
        String uri = SEARCH_BASE_URI;
        // uri的param配置
        Map<String, String> params = new HashMap<>();
        params.put("q", queryString);
        params.put("pages", String.valueOf(pages));
        params.put("rows", String.valueOf(rows));

        try {
            String jsonData = HttpClientUtils.doGet(uri, params);
            // json数据转换为已知的taotaoresult
            TaotaoResult taotaoSearchResult = JsonUtils.jsonToPojo(jsonData, TaotaoResult.class);
            // 返回JSP前需要检验得到的是否为正确result
            if (null != taotaoSearchResult && taotaoSearchResult.getStatus() == 200) {
                // 提取taotaoresult里的数据
                String taoTaoResultDataString = JsonUtils.objectToJson(taotaoSearchResult.getData());
                // 把解压得到的json数据转化成ItemList
                SearchResult searchResult = JsonUtils.jsonToPojo(taoTaoResultDataString, SearchResult.class);
                // 转化TaotaoResult到SearchResult
                return searchResult;
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        return null;
    }

}
