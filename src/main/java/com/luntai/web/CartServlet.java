package com.luntai.web;

import com.google.gson.Gson;
import com.luntai.pojo.Book;
import com.luntai.pojo.Cart;
import com.luntai.pojo.CartItem;
import com.luntai.service.BookService;
import com.luntai.service.impl.BookServiceImpl;
import com.luntai.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "CartServlet", value = "/cartServlet")
public class CartServlet extends BaseServlet {

    private BookService bookService = new BookServiceImpl();

    protected void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1. get book by id (id is in the request param in URL)
        int id = WebUtils.parseInt(request.getParameter("id"), 0);

        // step2. convert book to cartItem object
        Book book = this.bookService.queryBookById(id);
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());

        // step3. add cartItem to the cart
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) {
            // if cart does not exist in session, create one cart and put it into session
            // cart in the session will lost after session deactivates (close the browser)
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }
        cart.addItem(cartItem);

        // step4. write back last item to main page
        request.getSession().setAttribute("lastName", cartItem.getName()); // add to session

        // step5. redirect to referer url (where it comes from)
        response.sendRedirect(request.getHeader("Referer"));
    }

    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1. get book by id (id is in the request param in URL)
        int id = WebUtils.parseInt(request.getParameter("id"), 0);

        // step2. find the book in the cart
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            cart.deleteItem(id);
            // redirect to referer url
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            cart.clear();
            // redirect to referer url
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1. get book id and updated quant
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        int count = WebUtils.parseInt(request.getParameter("count"), 1);

        // step2. update the cart
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            cart.updateCount(id, count);
            // redirect to referer url
            response.sendRedirect(request.getHeader("Referer"));
        }

    }

    protected void ajaxAddItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1. get book by id (id is in the request param in URL)
        int id = WebUtils.parseInt(request.getParameter("id"), 0);

        // step2. convert book to cartItem object
        Book book = this.bookService.queryBookById(id);
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());

        // step3. add cartItem to the cart
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) {
            // if cart does not exist in session, create one cart and put it into session
            // cart in the session will lost after session deactivates (close the browser)
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }
        cart.addItem(cartItem);

        // step4. write back last item to main page
        request.getSession().setAttribute("lastName", cartItem.getName()); // add to session

        // step5. return json
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("totalCount", cart.getTotalCount());
        resultMap.put("lastName", cartItem.getName());

        Gson gson = new Gson();
        String jsonStr = gson.toJson(resultMap);
        response.getWriter().write(jsonStr);
    }

}
