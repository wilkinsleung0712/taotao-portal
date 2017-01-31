package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchServices;

/**
 * @Date 10:12:00 am 31 Jan 2017
 * @author wilkins.liang
 *
 */
@Controller
public class SearchController {

    @Autowired
    private SearchServices searchService;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString,
            @RequestParam(value = "pages", defaultValue = "0") Integer pages,
            @RequestParam(value = "rows", defaultValue = "50") Integer rows, Model model) throws Exception {

        // 解决中文字符串乱码
        queryString = new String(queryString.getBytes("ISO-8859-1"), "UTF-8");
        // 调用服务层并取得taotaosearch
        SearchResult searchResult = searchService.search(queryString, pages, rows);
        // 添加返回值至jsp
        model.addAttribute("itemList", searchResult.getItemList());
        model.addAttribute("totalPages", searchResult.getPageCount());
        model.addAttribute("query", queryString);

        return "search";
    }
}
