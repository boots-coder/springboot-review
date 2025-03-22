package com.example.demo.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }
    @RequestMapping("/hello2")
    @ResponseBody
    public String hello2(@NotBlank(message = "用户名不能为空") String name) {

        return "hello，"+name;
    }
}
