package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderHistoryDao;
import com.codecool.shop.dao.jdbc.OrderHistoryDaoJdbc;
import com.codecool.shop.config.databasemanager.DatabaseConnection;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.service.ProductServiceHelper;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/orderhistorydata"})
public class OrderHistoryData extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//            DatabaseConnection bookDatabaseManager = new DatabaseConnection();
            DataSource datasource = DatabaseConnection.connect();
            OrderHistoryDao orderHistory = OrderHistoryDaoJdbc.getInstance(datasource);
            Gson gson = new Gson();
            String jsonString = gson.toJson(orderHistory.getAllOrders());
            System.out.println(jsonString);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(jsonString);
            out.flush();
        }
    }