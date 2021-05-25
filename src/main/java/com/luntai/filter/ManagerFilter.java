package com.luntai.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "ManagerFilter", value = {"/pages/manager/*", "/manager/bookServlet", "/orderServlet"})
public class ManagerFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Object user = httpServletRequest.getSession().getAttribute("user");
        if (user == null) {
            // if no user is found in the session (user has not login), dispatch to login page
            httpServletRequest.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
        }
        else {
            chain.doFilter(request, response); // continue to following filter/servlet
        }
    }
}
