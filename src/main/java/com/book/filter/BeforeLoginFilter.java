package com.book.filter;

import com.book.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class BeforeLoginFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String url = req.getRequestURI().toString();
        // 进行过滤，无论是否登录，部分资源需要允许访问
        if (!url.contains("/static/") && !url.endsWith("login")) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            // 没有登录，Session 中没有 user 键值对
            // 重定向去登录页面
            if (user == null) {
                res.sendRedirect("login");
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
