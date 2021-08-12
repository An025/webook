package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.BillingInfoMem;
import com.codecool.shop.dao.implementation.CartDaoMem;
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
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = {"/billing/"})
public class BillingInfo extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(BillingInfo.class);

    @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            ProductService productService = ProductServiceHelper.getDataForProduct();
            logger.info("Create Billing Info");
            String line = null;
            StringBuffer jb = new StringBuffer();
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
            Gson gson = new Gson();
            BillingInfoMem billingInfo = gson.fromJson(jb.toString(), BillingInfoMem.class);
            productService.setAddress(billingInfo);
            boolean billingValidity = billingInfo.isValid();
            if (billingValidity){
                CartDaoMem.getInstance().setBillingInfo(billingInfo);
            } else {
                BillingInfoMem falseMemory = null;
                CartDaoMem.getInstance().setBillingInfo(falseMemory);
            }
            String convertedObject = gson.toJson(billingInfo);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(convertedObject);
            out.flush();
        }
    }
