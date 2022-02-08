package com.book.service.impl;

import com.book.dao.BookMapper;
import com.book.entity.Book;
import com.book.entity.Borrow;
import com.book.service.BookService;
import com.book.util.MybatisUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.session.SqlSession;

import java.util.*;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    @Override
    public List<Borrow> getBorrowList() {
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            return mapper.getBorrowList();
        }
    }

    @Override
    public void ReturnBook(int id) {
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.deleteBorrow(id);
        }
    }

    @Override
    public List<Book> getActiveBookList() {
        Set<Integer> set = new HashSet<>();
        this.getBorrowList().forEach(borrow -> set.add(borrow.getBook_id()));

        // 查询剩余可以借阅的书籍
        // 全部书籍中排除已经借出的书籍
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);

            return mapper.getBookList()
                    .stream()
                    .filter(book -> !set.contains(book.getBid()))
                    .collect(Collectors.toList());

        }
    }

    @Override
    public void addBorrow(int sid, int bid) {
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.addBorrow(sid, bid);
        }
    }

    @Override
    public Map<Book, Boolean> getAllBookList() {

        Set<Integer> set = new HashSet<>();
        this.getBorrowList().forEach(borrow -> set.add(borrow.getBook_id()));

        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            Map<Book, Boolean> map = new LinkedHashMap<>();
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);

            mapper.getBookList().forEach(book -> {
                map.put(book, set.contains(book.getBid()));
            });

            return map;
        }
    }

    @Override
    public void deleteBook(int bid) {
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.deleteBook(bid);
        }
    }

    @Override
    public void addBook(String title, String desc, double price) {
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.addBook(title, desc, price);
        }
    }
}
