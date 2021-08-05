package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.service.ProductServiceHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/addToCart/*"})
public class OrderChanger extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // finds item by id in product list
        // put items into cart
        // sends out the number of items in cart
        String[] pathInfo = request.getPathInfo().split("/");
        ProductService productService = ProductServiceHelper.getDataForProduct();
        int numberOfItemsInCart = 0;
        try {
            int productId = Integer.parseInt(pathInfo[1]);
            Product productPutIntoCart = productService.getProduct(productId);
            CartDao cartDao = CartDaoMem.getInstance();
            cartDao.add(productPutIntoCart);
            for (Product product : productService.getAllProductFromCart()) {
                numberOfItemsInCart = numberOfItemsInCart + product.quantity;
            }
        } catch (NumberFormatException e) {
            for (Product product : productService.getAllProductFromCart()) {
                numberOfItemsInCart = numberOfItemsInCart + product.quantity;
            }
        }
        PrintWriter out = response.getWriter();
        System.out.println(numberOfItemsInCart);
        out.print(numberOfItemsInCart);
        out.flush();
    }
}
