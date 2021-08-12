package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.BillingInfoMem;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.PaymentDetailMem;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.service.ProductServiceHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.UUID;


@WebServlet(urlPatterns = {"/payment/"})
public class PaymentInfo extends HttpServlet{
    private static Logger logger = LoggerFactory.getLogger(BillingInfo.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String line = null;
        StringBuffer jb = new StringBuffer();
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }
        Gson gson = new Gson();
        PaymentDetailMem paymentInfo = gson.fromJson(jb.toString(), PaymentDetailMem.class);

        boolean paymentValidity = paymentInfo.isValid();

        if (paymentValidity){
            CartDaoMem.getInstance().setPaymentDetail(paymentInfo);
            CartDaoMem.getInstance().setOrderID(UUID.randomUUID());
            logger.info("Valid payment");
        } else {
            PaymentDetailMem falseMemory = null;
            CartDaoMem.getInstance().setPaymentDetail(falseMemory);
            logger.info("Invalid payment");

        }

        String convertedObject = gson.toJson(paymentInfo);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(convertedObject);
        out.flush();
    }
}
