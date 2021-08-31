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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/changeQuantity/*"})
public class OrderChanger extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] pathInfo = request.getPathInfo().split("/");
        System.out.println(pathInfo[1]);
        System.out.println(pathInfo[2]);
        String directionOfChange = pathInfo[1];
        int productID = Integer.parseInt(pathInfo[2]);
        ProductService productService = ProductServiceHelper.getDataForProduct();
        for (Product product : productService.getAllProductFromCart()) {
            if (product.getId() == productID && directionOfChange.equals("Delete")) {
                productService.removeProductFromCart(product);
                break;
            } else if (product.getId() == productID && product.quantity > 1 && directionOfChange.equals("-")) {
//                product.quantity = product.quantity - 1;
                productService.decreaseAmountOfProductInCart(product);
                break;
            } else if (product.getId() == productID && directionOfChange.equals("+")) {
//                product.quantity = product.quantity + 1;
                productService.increaseAmountOfProductInCart(product);
                break;
            }
        }

        PrintWriter out = response.getWriter();
        out.print(1);
        out.flush();
    }
}
