package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.BillingInfoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface CartDao {

    void add(Product product);
    Product find(int id);
    void remove(int id);
    void setBillingInfo(BillingInfoMem billingInfoMem);
    ArrayList<Product> getAll();
    void decreaseAmountOfProductInCartByOne(Product product);
}
