package com.example.demo.controller;

import com.example.demo.config.ConfigFromYaml;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValueYmlController1 {


    @Value("${connectionInfo.email}")
    private String email;
    @Value("${connectionInfo.wechat}")
    private String wechat;

    private final ConfigFromYaml config;

    //使用依赖注入的生命周期特性，使得在类启动时候就注入
    public ValueYmlController1(ConfigFromYaml config) {
        this.config = config;
    }

    @RequestMapping("/selfprofile")
    public String selfProfile() {
        return "this is the usage of @value to inject from yml ========= selfProfile:"+"wechat:"+wechat+",email:"+email;
    }
    @RequestMapping("/advancedProfile")
    public Object selfProfile1() {
        return config.getMoreAdvanceProfile();
    }
}
