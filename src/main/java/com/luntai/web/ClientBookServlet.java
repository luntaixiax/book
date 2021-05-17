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

@WebServlet(name = "ClientBookServlet", value = "/client/bookServlet")
public class ClientBookServlet extends BaseServlet {

    private BookService bookService = new BookServiceImpl();

    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: get request parameter (pageNo and pageSize)
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);

        // step2: call bookService to list book on pages
        Page<Book> page = this.bookService.page(pageNo, pageSize);  // it will find the correspond page (at pagNo)
        page.setUrl("client/bookServlet?action=page"); // set url to the page object so that the frontend can render this to html action

        // step3: save books to request for frontend to render
        request.setAttribute("page", page);

        // step4: dispatch request to book_manager page (main page of book manager)
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request, response);
    }

    protected void pageByPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: get request parameter (pageNo and pageSize)
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        int min = WebUtils.parseInt(request.getParameter("min"), 0); // min number of records found (price range)
        int max = WebUtils.parseInt(request.getParameter("max"), Integer.MAX_VALUE); // max number of records found (price range)

        // step2: call bookService to list book on pages
        Page<Book> page = this.bookService.pageByPrice(pageNo, pageSize, min, max);  // it will find the correspond page (at pagNo)

        // step3: generate url to append to page object
        StringBuilder sb = new StringBuilder("client/bookServlet?action=pageByPrice");
        if (request.getParameter("min") != null) {
            sb.append("&min=").append(request.getParameter("min"));
        }
        if (request.getParameter("max") != null) {
            sb.append("&max=").append(request.getParameter("max"));
        }
        page.setUrl(sb.toString()); // set url to the page object so that the frontend can render this to html action

        // step4: save books to request for frontend to render
        request.setAttribute("page", page);

        // step5: dispatch request to book_manager page (main page of book manager)
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request, response);
    }
}
