package com.book.service;

import com.book.entity.Book;
import com.book.entity.Borrow;

import java.util.List;
import java.util.Map;

public interface BookService {
    List<Borrow> getBorrowList();

    void ReturnBook(int id);

    List<Book> getActiveBookList();

    void addBorrow(int sid, int bid);

    Map<Book, Boolean> getAllBookList();

    void deleteBook(int bid);

    void addBook(String title, String desc, double price);

    int countBooks();
}
