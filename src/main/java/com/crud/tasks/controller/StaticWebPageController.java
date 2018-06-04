package com.crud.tasks.controller;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
@Controller
public class StaticWebPageController {

    @RequestMapping("/")
    public String index(Map<String, Object>model){
        model.put("variable","My Thymeleaf variable");
        model.put("one",1);
        model.put("two",2);
        return"index";
    }
}
