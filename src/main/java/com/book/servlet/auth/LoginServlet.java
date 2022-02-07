package com.book.servlet.auth;

import com.book.service.UserService;
import com.book.service.impl.UserServiceImpl;
import com.book.util.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    UserService service;

    @Override
    public void init() throws ServletException {
        service = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        // 获取页面上下文，与前端页面传递数据
        Context context = new Context();

        // 如果登录失败，Session 中存在 login-fail=true 键值对
        if (req.getSession().getAttribute("login-fail") != null) {
            // 此时登录失败
            // 设置页面元素，进行登录失败反馈
            context.setVariable("failure", true);
            // 从 Session 中移除 login-fail 键值对
            req.getSession().removeAttribute("login-fail");
        }

        // 如果登录成功，Session （cookie）中没有这个键 login-fail，即没有登录失败
        if (req.getSession().getAttribute("user") != null) {
            // 登录成功，重定向进首页 index
            resp.sendRedirect("index");
            return;
        }

        ThymeleafUtil.process("login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember-me");

        if (service.auth(username, password, req.getSession())) {
            // 进行登录判断
            // 如果用户名和密码能够在数据库中查询到，则重定向跳转进入首页
            // 登录成功
//            resp.getWriter().write("Success!");
            resp.sendRedirect("index");

        } else {
            // 登录失败，没有在数据库中查找到用户
            // 在 Session 中 添加 login-fail=true 键值对，标记登录失败
            req.getSession().setAttribute("login-fail", true);
            // 请求传递给 get 请求进行后续处理
            this.doGet(req, resp);
        }

    }
}
