package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@WebServlet(urlPatterns = {"/confirmation"})
public class ConfirmationController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        BillingInfoMem billingInfo = CartDaoMem.getInstance().getBillingInfoMem();
        ArrayList<Product> productsInfo = CartDaoMem.getInstance().getAll();
        CartDaoMem.getInstance().setOrderID(UUID.randomUUID());
        float totalPrice = 0.0F;
        for(Product product: productsInfo ){
            totalPrice += product.getDefaultPrice() * product.getQuantity();
        }
        System.out.println(CartDaoMem.getInstance().getOrderID());
        context.setVariable("billingInfo", billingInfo);
        context.setVariable("productsList", productsInfo);
        context.setVariable("totalprice", totalPrice);
        context.setVariable("orderID", CartDaoMem.getInstance().getOrderID());
        engine.process("product/confirmation.html", context, resp.getWriter());
    }
}
