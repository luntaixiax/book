package com.luntai.web;

import com.google.code.kaptcha.servlet.KaptchaServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ValidationServlet", value = "/kaptcha.jpg")
public class ValidationServlet extends KaptchaServlet {

}
