package com.ze.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(ModelMap map) {
        map.put("name", "wang");
        map.put("age", 18);
        return "index";
    }

    @RequestMapping(value = "/about",method = RequestMethod.GET)
    public String about() {
        return "about";
    }

}
