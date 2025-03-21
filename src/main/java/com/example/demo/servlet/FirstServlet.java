package com.example.demo.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/firstServlet")
public class FirstServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FirstServlet");
        // 设置响应内容类型，例如纯文本和字符编码
        resp.setContentType("text/plain;charset=UTF-8");
        // 获取响应输出流
        PrintWriter out = resp.getWriter();
        // 向浏览器输出字符串
        out.write("Hello from FirstServlet!");
        // 可选：刷新并关闭输出流
        out.flush();
        out.close();
    }

}
