package com.luntai.filter;

import com.luntai.utils.JdbcUtils;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebFilter(filterName = "TransactionFilter")
public class TransactionFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
            JdbcUtils.commitAndClose();
        } catch (Exception e) {
            JdbcUtils.rollbackAndCLose();
            e.printStackTrace();
            throw new RuntimeException(e); // throw to Tomcat to direct to manage error page
        }
    }
}
