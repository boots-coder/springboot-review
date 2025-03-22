package com.example.demo.controller;

import com.example.demo.config.ConfigFromYaml;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.http.HttpRequest;

@Controller
public class PageControllerThymeleaf {
    @Autowired
    ConfigFromYaml configFromYaml;
    // 页面跳转
    @GetMapping("/showThymeleaf")
    public String showPage(Model model, HttpServletRequest httpRequest, HttpSession session){
        model.addAttribute("msg","Hello Thymeleaf");
        model.addAttribute("sex","女");
        model.addAttribute("id", 12);
        model.addAttribute("myProjects", configFromYaml.getMoreAdvanceProfile().getSelfProject());
        httpRequest.setAttribute("req", "myrequest");
        session.setAttribute("session", "mysession");
        session.getServletContext().setAttribute("app", "myapplictaion ");
        return "index";
    }
}
