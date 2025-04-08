//package com.example.demo.config;
//
//import com.example.demo.servlet.SecondServlet;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ServletConfig {
//    //ServletRegistrationBean可以注册Servlet组件，将其放入Spring容器中即可注册Servlet
//    @Bean
//    public ServletRegistrationBean getServletRegistrationBean(){
//        // 注册Servlet组件
//        ServletRegistrationBean bean = new ServletRegistrationBean(new SecondServlet());
//        // 添加Servlet组件访问路径
//        bean.addUrlMappings("/secondServlet");
//        return bean;
//    }
//}
