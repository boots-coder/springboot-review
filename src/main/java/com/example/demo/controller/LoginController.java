package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login.html")
    public String login() {
        return "login"; // 对应 templates/login.html (如果使用模板引擎)
        // 如果使用静态页面，则不需要此方法
    }

    @PostMapping("/main.html")
    public String main() {
        return "main"; // 对应 templates/main.html
    }

    @PostMapping("/fail.html")
    public String fail() {
        return "fail"; // 对应 templates/fail.html
    }
}