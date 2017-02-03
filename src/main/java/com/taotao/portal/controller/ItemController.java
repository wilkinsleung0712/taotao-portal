package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.pojo.TbItem;
import com.taotao.portal.service.ItemService;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String getItemDetailsPage(@PathVariable Long itemId, Model model) {
        TbItem item = itemService.getItemById(itemId);
        if (null != item) {
            model.addAttribute("item", item);
        }
        return "item";
    }
}
