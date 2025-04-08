package com.example.demo.config;

import com.example.demo.handler.MyLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
//这里重写了登陆页面后也要加上controller 接口
//而且这里有前缀，好像会混乱 -- 经过排查是前端页面需要加前缀
public class SecurityConfig {
    @Autowired
    PersistentTokenRepository persistentTokenRepository;
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 自定义表单登录配置
        http.formLogin(form -> {
            form.loginPage("/login.html")  // 自定义登录页面
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login")//登录路径，表单向该路径提交，提交后自动执行UserDetailsService的方法
//                    .defaultSuccessUrl("/main.html", true) // 改为重定向
                    .successForwardUrl("/main.html") // 服务器内部实现登录成功后的跳转
//                    .successHandler(new MyLoginSuccessHandler()) // 登录成功处理器
                    .failureForwardUrl("/fail.html");
        });

        // 设置放行的资源
        http.authorizeHttpRequests(authz -> {
            // 放行登录页、处理路径、失败页和静态资源
            authz.requestMatchers("/login.html", "/fail.html", "/main.html").permitAll();
            authz.requestMatchers("/css/*.css", "/js/*.js", "/img/**").permitAll();
            // 其余请求要求认证
            authz.anyRequest().authenticated();
        });
        // 退出登陆
        http.logout(logout -> {
            logout.logoutUrl("/logout")
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // 允许GET请求退出
                    .logoutSuccessUrl("/login.html")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true);
        });
        // 记住我配置
        http.rememberMe(remember -> {
            remember.userDetailsService(userDetailsService) //认证逻辑对象
                    .tokenRepository(persistentTokenRepository) //持久层对象
                    .tokenValiditySeconds(30); //保存时间，单位：秒
        });


        // 关闭csrf防护（仅供测试使用，生产环境需谨慎关闭）
//        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    // 密码编码器，使用BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 示例InMemory用户
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.withUsername("user")
//                //.password("123456") // 不要这样写！需要加密
//                .password(passwordEncoder.encode("123456"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
}