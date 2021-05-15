package com.luntai.web;

import com.luntai.pojo.Book;
import com.luntai.pojo.Page;
import com.luntai.service.BookService;
import com.luntai.service.impl.BookServiceImpl;
import com.luntai.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "BookServlet", value = "/manager/bookServlet")
public class BookServlet extends BaseServlet {

    private BookService bookService = new BookServiceImpl();

    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: get request parameter and save to Book Bean object
        Map<String, String[]> parameterMap = request.getParameterMap();
        Book book = WebUtils.copyParamToBean(parameterMap, new Book()); // save book attributes

        // step2: call bookService to add book
        this.bookService.addBook(book);

        // step3: redirect user to book list page (separate call)
        // use redirect other than dispatch to prevent repetitive execution cause by front page refresh (each refresh will send new request)
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 0) + 1; // add 1 to prevent page change
        String url = request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo;
        response.sendRedirect(url);
    }

    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: get request parameter and save to Book Bean object
        Map<String, String[]> parameterMap = request.getParameterMap();
        Book book = WebUtils.copyParamToBean(parameterMap, new Book()); // save book attributes

        // step2: call bookService to update book
        this.bookService.updateBook(book);

        // step3: redirect user to book list page (separate call)
        // use redirect other than dispatch to prevent repetitive execution cause by front page refresh (each refresh will send new request)
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 0);
        String url = request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo;
        response.sendRedirect(url);
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: get request parameter: id
        int id = WebUtils.parseInt(request.getParameter("id"), 0);

        // step2: call bookService to delete book
        this.bookService.deleteBookById(id);

        // step3: redirect user to book list page (separate call)
        // use redirect other than dispatch to prevent repetitive execution cause by front page refresh (each refresh will send new request)
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 0);
        String url = request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo;
        response.sendRedirect(url);
    }

    protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: get request parameter: id
        int id = WebUtils.parseInt(request.getParameter("id"), 0);

        // step2: call bookService to query book
        Book book = this.bookService.queryBookById(id);

        // step3: save book to request for frontend to render
        request.setAttribute("book", book);

        //step4: dispatch request to book_edit page
        request.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(request, response);
    }

    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: get all books from bookService
        List<Book> books = this.bookService.queryBooks();

        // step2: save books to request for frontend to render
        request.setAttribute("books", books);

        // step3: dispatch request to book_manager page (main page of book manager)
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request, response);
    }

    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: get request parameter (pageNo and pageSize)
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);

        // step2: call bookService to list book on pages
        Page<Book> page = this.bookService.page(pageNo, pageSize);  // it will find the correspond page (at pagNo)
        page.setUrl("manager/bookServlet?action=page"); // set url to the page object so that the frontend can render this to html action

        // step3: save books to request for frontend to render
        request.setAttribute("page", page);

        // step4: dispatch request to book_manager page (main page of book manager)
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request, response);
    }
}
