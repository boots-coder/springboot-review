package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PageControllerThymeleaf {
    // 页面跳转
    @GetMapping("/showThymeleaf")
    public String showPage(Model model){
        model.addAttribute("msg","Hello Thymeleaf");
        return "index";
    }
}
