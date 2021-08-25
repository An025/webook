package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.BillingInfoMem;
import com.codecool.shop.dao.jdbc.CartDaoJdbc;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.util.ArrayList;

public interface OrderHistoryDao {


    ArrayList<Order> getAllOrders();

}
