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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/logout"})
public class Logout extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(Logout.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute("name");
            session.removeAttribute("id");
            logger.info("Remove session");
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());
            engine.process("product/logout.html", context, resp.getWriter());
            logger.info("Load logout page");
        }else{
            RequestDispatcher rd = req.getRequestDispatcher("/");
            rd.forward(req,resp);
            logger.info("Load login page");
        }

    }
}



