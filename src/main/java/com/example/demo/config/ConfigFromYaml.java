package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "myconfig")
public class ConfigFromYaml {

    // 对应 YAML 中的 "MoreAdvanceProfile"
    private MoreAdvanceProfile moreAdvanceProfile;

    @Data
    public static class MoreAdvanceProfile {
        // 对应 YAML 中的 "internship" 集合
        private List<String> internship;
        // 对应 YAML 中的 "selfProject" 集合对象
        private List<Project> selfProject;
    }

    @Data
    public static class Project {
        // 对应 YAML 中 selfProject 每个元素的 name 属性
        private String name;
        // 对应 YAML 中 selfProject 每个元素的 tech 属性
        private String tech;
    }
}