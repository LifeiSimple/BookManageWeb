package com.book.service.impl;

import com.book.dao.UserMapper;
import com.book.entity.User;
import com.book.service.UserService;
import com.book.util.MybatisUtil;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public boolean auth(String username, String password, HttpSession session) {
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> users = mapper.getUser(username, password);

            boolean check = false;
            User userSession = null;
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    check = true;
                    userSession = user;
                    break;
                }
            }
            // 登录检验失败
            if (!check) return false;
            else session.setAttribute("user", userSession);
            return true;
        }
    }
}
