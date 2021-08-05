package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CartDaoMem implements CartDao {

    private ArrayList<Product> data = new ArrayList<>();
    private static CartDaoMem instance = null;
    private BillingInfoMem billingInfoMem;
    private float totalPrice;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CartDaoMem() {
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        if (data.contains(product)){
            product.quantity = product.quantity + 1;
        }else{
            data.add(product);
            product.quantity = 1;
        }
    }

    @Override
    public Product find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public void setBillingInfo(BillingInfoMem billingInfoMem) {
        billingInfoMem = billingInfoMem;
    }

        @Override
        public ArrayList<Product> getAll () {
            return data;
        }
    }




