package com.codecool.shop.controller;

import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.service.ProductServiceHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/getItemsInCart"})
public class CartReview extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            ProductService productService = ProductServiceHelper.getDataForProduct();
//            String json = new Gson().toJson(productService.getAllProductFromCart());
//            System.out.println(productService.getAllProductFromCart());
            Gson gson = new Gson();
            String jsonString = gson.toJson(productService.getAllProductFromCart());
//            Type type = new TypeToken<HashMap<Product, Integer>>(){}.getType();
//            HashMap<Product, Integer> clonedMap = gson.fromJson(jsonString, type);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(jsonString);
            out.flush();
        }
    }
