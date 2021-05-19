package com.luntai.web;

import com.google.code.kaptcha.Constants;
import com.luntai.pojo.User;
import com.luntai.service.UserService;
import com.luntai.service.impl.UserServiceImpl;
import com.luntai.utils.WebUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "UserServlet", value = "/userServlet")
public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: get parameters from GET (URL) / POST (body) request and save to a map object
        Map<String, String[]> parameterMap = request.getParameterMap(); // username and password

        // step2: populate relevant key-value pairs into User Bean
        User user = WebUtils.copyParamToBean(parameterMap, new User());

        // validate if username and password is correct
        User loginUser = this.userService.login(user); // call userService to find the user
        if (loginUser == null) {    // user does not exist, means either username or password is wrong
            // wrong case, but still need to write back username field back to request and send back to frontend
            // so that the user does not need to fill again and again
            request.setAttribute("msg", "Username or pswd wrong!"); // error message to show
            request.setAttribute("username", request.getParameter("username"));
            // dispatch back to login page with username keep showing on the screen
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
        }
        else {
            // use session to record user username info (cookie and session)
            request.getSession().setAttribute("user", loginUser);  // request scope is not big enough
            // dispatch user to login success page
            request.getRequestDispatcher("/pages/user/login_success.jsp").forward(request, response);
        }
    }

    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: get parameters from GET (URL) / POST (body) request and save to a map object
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String code = request.getParameter("code");
        Map<String, String[]> parameterMap = request.getParameterMap();

        // step2: populate relevant key-value pairs into User Bean
        WebUtils.copyParamToBean(parameterMap, new User()); // pass in a void user and parameters, WebUtils will automatically inject key-value paris

        // step3. get validation code using google kaptcha
        String token = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY); // delete it immediately to prevent user redundant submit

        // step4: fields validation
        if (token != null && token.equalsIgnoreCase(code)){  // if 2d code is correct
            if (this.userService.exitsUsername(username)) {  // if user name exists
                // wrong case, but still need to write back username and email field back to request and send back to frontend
                // so that the user does not need to fill again and again
                request.setAttribute("msg", "Username Exists!"); // error message to show
                request.setAttribute("username", username);
                request.setAttribute("email", email);
                // dispatch request to regist.jsp again, save the parameters in the request field, jsp will read from it
                request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
            }
            else {    // normal case, username does not exist
                // call userService to register user
                User user = new User(null, username, password, email);
                this.userService.registerUser(user);
                // dispatch user to register success page
                request.getRequestDispatcher("/pages/user/regist_success.jsp").forward(request, response);
            }
        }
        else {    // validation code not correct
            // wrong case, but still need to write back username and email field back to request and send back to frontend
            request.setAttribute("msg", "Wrong QR code!"); // error message to show
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            // dispatch request to regist.jsp again, save the parameters in the request field, jsp will read from it
            request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
        }
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1. destroy session to clear user login info
        request.getSession().invalidate();
        // step2. redirect user to main page (context path)
        response.sendRedirect(request.getContextPath());
    }
}
