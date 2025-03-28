package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// Security配置类
@Configuration
public class SecurityConfig {
//    // 定义认证逻辑，这里相当于重写了UserDetailsService吧
//    public UserDetailsService userDetailsService(){
//        // 1.使用内存数据进行认证，userDetailsService的实现类
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//
//        // 2.创建两个用户
//        UserDetails user1 = User.withUsername("bootscoder").password("root").authorities("admin").build();
//        UserDetails user2 = User.withUsername("jiahaoxuan").password("root").authorities("admin").build();
//
//
//        // 3.将这两个用户添加到内存中
//        manager.createUser(user1);
//        manager.createUser(user2);
//
//
//        return manager;
//    }

//    @Bean
//    //密码编码器，不解析密码
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }
    @Bean
        public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
        }
}
