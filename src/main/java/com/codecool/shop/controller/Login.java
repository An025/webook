package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.jdbc.CartDaoJdbc;
import com.codecool.shop.dao.jdbc.CustomerDaoJdbc;
import com.codecool.shop.model.Customer;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(Login.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/login.html", context, resp.getWriter());
        logger.info("Load login page");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        CustomerDao customer = CustomerDaoJdbc.getInstance();
        Customer registeredCustomer = customer.find(email, password);
        CartDaoJdbc cart = CartDaoJdbc.getInstance();
        cart.setUserId(registeredCustomer.getId());
        System.out.println("customer ID during payment" + registeredCustomer.getId());
        if(registeredCustomer == null){
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());
            context.setVariable("error", "Invalid email or password. ");
            engine.process("product/login.html", context, resp.getWriter());
            logger.error("There is no such customer in the database");
        }else{
            HttpSession session=req.getSession();
            session.setAttribute("id", registeredCustomer.getId());
            session.setAttribute("name",registeredCustomer.getName());
            RequestDispatcher rd = req.getRequestDispatcher("/");
            rd.forward(req,resp);
            logger.info("Find customer in database");
        }
    }
}
