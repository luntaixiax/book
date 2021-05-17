package com.luntai.dao.impl;

import com.luntai.dao.BookDao;
import com.luntai.pojo.Book;

import java.util.List;

public class BookDaoImpl extends BaseDao implements BookDao {

    @Override
    public int addBook(Book book) {
        String sql = "INSERT INTO t_book(`name`, `author`, `price`, `sales`, `stock`, `img_path`) VALUES (?,?,?,?,?,?)";
        return update(sql, book.getName(), book.getAuthor(), book.getPrice(), book.getSales(), book.getStock(), book.getImgPath());
    }

    @Override
    public int deleteBookById(Integer id) {
        String sql = "DELETE FROM t_book WHERE id = ?";
        return update(sql, id);
    }

    @Override
    public int updateBook(Book book) {
        String sql = "UPDATE t_book SET `name`=?,`author`=?,`price`=?,`sales`=?,`stock`=?,`img_path`=? WHERE id = ?";
        return update(sql, book.getName(), book.getAuthor(), book.getPrice(), book.getSales(), book.getStock(), book.getImgPath(), book.getId());
    }

    @Override
    public Book queryBookById(Integer id) {
        String sql = "SELECT `id` , `name` , `author` , `price` , `sales` , `stock` , `img_path` imgPath FROM t_book WHERE id = ?";
        return queryForOne(Book.class, sql, id);
    }

    @Override
    public List<Book> queryBooks() {
        String sql = "SELECT `id` , `name` , `author` , `price` , `sales` , `stock` , `img_path` imgPath FROM t_book";
        return queryForList(Book.class, sql);
    }

    @Override
    public Integer queryForPageTotalCount() {
        String sql = "SELECT count(*) FROM t_book";
        Number count = (Number) queryForSingleValue(sql);
        return count.intValue();
    }

    @Override
    public List<Book> queryForPageItems(int begin, int pageSize) {
        String sql = "SELECT `id` , `name` , `author` , `price` , `sales` , `stock` , `img_path` imgPath FROM t_book LIMIT ?, ?";
        return queryForList(Book.class, sql, begin, pageSize);
    }

    @Override
    public Integer queryForPageTotalCountByPrice(int min, int max) {
        String sql = "SELECT count(*) FROM t_book WHERE price BETWEEN ? AND ?";
        Number count = (Number) queryForSingleValue(sql, min, max);
        return count.intValue();
    }

    @Override
    public List<Book> queryForPageItemsByPrice(int begin, int pageSize, int min, int max) {
        String sql = "SELECT `id` , `name` , `author` , `price` , `sales` , `stock` , `img_path` imgPath FROM t_book " +
                "WHERE price BETWEEN ? AND ? ORDER BY price LIMIT ?, ?";
        return queryForList(Book.class, sql, min, max, begin, pageSize);
    }
}
