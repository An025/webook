package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.BillingInfoMem;
import com.codecool.shop.model.Product;

import java.util.ArrayList;

public class CartDaoJdbc implements CartDao {
    @Override
    public void add(Product product) {

    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void setBillingInfo(BillingInfoMem billingInfoMem) {

    }

    @Override
    public ArrayList<Product> getAll() {
        return null;
    }
}
