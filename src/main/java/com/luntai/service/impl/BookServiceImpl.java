package com.luntai.service.impl;

import com.luntai.dao.BookDao;
import com.luntai.dao.impl.BookDaoImpl;
import com.luntai.pojo.Book;
import com.luntai.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private BookDao bookDao = new BookDaoImpl();

    @Override
    public void addBook(Book book) {
        this.bookDao.addBook(book);
    }

    @Override
    public void deleteBookById(Integer id) {
        this.bookDao.deleteBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        this.bookDao.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return this.bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return this.bookDao.queryBooks();
    }
}
