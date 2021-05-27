package com.luntai.web;

import com.luntai.utils.WebUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

//@WebServlet(name = "BaseServlet", value = "/BaseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // will do same logic as POST request
        doPost(request, response);
    }

    /**
     * automatically find the relevant method to execute
     * e.g.,  frontend send GET request with "action" param in URL or POST request with "action" parameter in body
     * step1. frontend send GET request:   http://ip:port/project/servlet?action=add
     * step2. BaseServlet will search itself and send the request to BaseServlet.add(request, response) method
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = null;
        if(ServletFileUpload.isMultipartContent(request)) {
            // if it is multipart form request, cannot use getParameter()
            Map<String, List<String>> map = WebUtils.fileRequestParamMap(request);
            action = map.get("action").get(0);
        } else{
            // else it is a normal request
            action = request.getParameter("action"); // get <action> method from GET request URL
        }

        // use reflection to automatically find desired method to execute
        try {
            // find method <action> in BaseServlet and its subclasses with method parameters signature request and response
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            // execute the method <action> and send in parameters
            method.invoke(this, request, response);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e); // throw error to caller
        }
    }
}
