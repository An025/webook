package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/newuser"})
public class NewUser extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(BillingInfo.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        //Call ProductController.java
        RequestDispatcher rd = request.getRequestDispatcher("/");
        rd.forward(request,response);
        logger.info("Save user to database");

    }


}
