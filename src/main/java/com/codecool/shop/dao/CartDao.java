package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.BillingInfoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface CartDao {

    void add(Product product);
    Product find(int id);
    void removeProductFromCart(Product product);
    void setBillingInfo(BillingInfoMem billingInfoMem);
    ArrayList<Product> getAll();
    void increaseAmountOfProductInCartByOne(Product product);
    void decreaseAmountOfProductInCartByOne(Product product);
    int getIdOfActiveOrder();
    void inactivateOrder();
}
