package com.example.demo.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 拿到登录用户的信息
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("用户名:" + username);

        // 可以将用户名存储在会话中，方便在页面中访问
        HttpSession session = request.getSession();
        session.setAttribute("username", username);

        // 也可以在这里获取购物车数量并存入session
        // 假设有一个购物车服务来获取数量
        // CartService cartService = ...
        // int cartCount = cartService.getCartCount(username);
        // session.setAttribute("cartCount", cartCount);

        System.out.println("一些操作...");

        // 重定向到主页
        response.sendRedirect("/bootscoder/main.html");
    }
}