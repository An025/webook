package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.*;
import java.util.stream.Collectors;

public class CartDaoMem implements CartDao {

    private ArrayList<Product> data = new ArrayList<>();
    private static CartDaoMem instance = null;
    private BillingInfoMem billingInfoMem;
    private float totalPrice;
    private PaymentDetailMem paymentDetail;
    private UUID orderID;
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
    public void removeProductFromCart(Product product) {

    }

    @Override
    public void setBillingInfo(BillingInfoMem billingInfoMem) {
        this.billingInfoMem = billingInfoMem;
    }

    public void setOrderID(UUID orderID) {
        this.orderID = orderID;
    }

    public UUID getOrderID() {
        return orderID;
    }

    public void setPaymentDetail(PaymentDetailMem paymentDetail){
        this.paymentDetail = paymentDetail;
    }

    public PaymentDetailMem getPaymentDetail() {
        return paymentDetail;
    }

    @Override
        public ArrayList<Product> getAll () {
            return data;
        }

    @Override
    public void increaseAmountOfProductInCartByOne(Product product) {

    }

    @Override
    public void decreaseAmountOfProductInCartByOne(Product product) {



    }

    public BillingInfoMem getBillingInfoMem() {
        return billingInfoMem;
    }
}




