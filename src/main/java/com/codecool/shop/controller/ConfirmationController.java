package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.writer.LocalFileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@WebServlet(urlPatterns = {"/confirmation/*"})
public class ConfirmationController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(BillingInfo.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        BillingInfoMem billingInfo = CartDaoMem.getInstance().getBillingInfoMem();

        if(req.getPathInfo().equals("/true")){
            CartDaoMem.getInstance().getPaymentDetail().setPaid(true);
            logger.info("Success payment");
        }else{
            CartDaoMem.getInstance().getPaymentDetail().setPaid(false);
            logger.error("Unsuccess payment");

        }
        LocalFileWriter.serialize();
        ArrayList<Product> productsInfo = CartDaoMem.getInstance().getAll();
        float totalPrice = 0.0F;
        for(Product product: productsInfo ){
            totalPrice += product.getDefaultPrice() * product.getQuantity();
        }
        context.setVariable("productsList", productsInfo);
        context.setVariable("totalprice", totalPrice);
        context.setVariable("orderID", CartDaoMem.getInstance().getOrderID());
        context.setVariable("billingInfo", billingInfo);
        context.setVariable("paymentStatus", CartDaoMem.getInstance().getPaymentDetail().isPaid());
        engine.process("product/confirmation.html", context, resp.getWriter());
        logger.info("End Confirmation");
    }
}