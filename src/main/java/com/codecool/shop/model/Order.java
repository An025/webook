package com.codecool.shop.model;

import java.util.ArrayList;

public class Order {

    private String orderDate;
    private String orderStatus;
    private String totalPrice;
    private ArrayList<Product> products = new ArrayList<>();
    private int userId;
    private int id;


    public Order(String orderDate, String orderStatus, int id, int userId, ArrayList<Product> products) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.id = id;
        this.userId = userId;
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "products=" + products +
                ", id=" + id +
                '}';
    }
}
