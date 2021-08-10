package com.codecool.shop.dao.implementation;

public class PaymentDetailMem {


    boolean isPayPal;
    String email = "";
    String password = "";
    String name = "";
    String cvcCode = "";
    String cardNo = "";
    String expDate = "";

    public PaymentDetailMem(boolean isPayPal, String email, String password, String name, String cvcCode, String cardNo, String expDate) {
        this.isPayPal = isPayPal;
        this.email = email;
        this.password = password;
        this.name = name;
        this.cvcCode = cvcCode;
        this.cardNo = cardNo;
        this.expDate = expDate;
    }
}
