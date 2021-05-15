package com.luntai.service.impl;

import com.luntai.dao.BookDao;
import com.luntai.dao.impl.BookDaoImpl;
import com.luntai.pojo.Book;
import com.luntai.pojo.Page;
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

    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        // return a page object

        // create a page object
        Page<Book> page = new Page<>();
        page.setPageSize(pageSize);

        // find total records
        Integer pageTotalCount = this.bookDao.queryForPageTotalCount();
        // find total number of pages
        Integer pageTotal = pageTotalCount % pageSize > 0 ? pageTotalCount / pageSize + 1 : pageTotalCount / pageSize;

        // !!! must set pageTotal before setPageNo, because in Page.setPageNo(), it will check if current pageNo is greater than pageTotal
        page.setPageTotal(pageTotal);
        page.setPageNo(pageNo);

        // find begin index
        int begin = (page.getPageNo() - 1) * pageSize;  // use .getPageNo() to prevent pageNo overflow (will cap to pageTotal)
        // get items on current page
        List<Book> items = this.bookDao.queryForPageItems(begin, pageSize);

        // save attributes to page object

        page.setPageTotalCount(pageTotalCount);

        page.setItems(items);

        return page;
    }
}
