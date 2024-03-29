package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.implementation.BillingInfoMem;
import com.codecool.shop.dao.jdbc.CustomerDaoJdbc;
import com.codecool.shop.model.Customer;
import com.google.gson.Gson;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/newuser"})
public class NewUser extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(NewUser.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        logger.info("Create New User");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password1 = request.getParameter("password1");

        String name = firstName + " " + lastName;
        Customer newCustomer = new Customer(name, email, password1);
        CustomerDao customerDao = CustomerDaoJdbc.getInstance();
        customerDao.add(newCustomer);
        int customerId = newCustomer.getId();

        //Call ProductController.java
        RequestDispatcher rd = request.getRequestDispatcher("/");
        rd.forward(request,response);
        logger.info("Save user to database");

    }


}
