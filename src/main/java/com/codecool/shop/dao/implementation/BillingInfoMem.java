package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;

import java.util.ArrayList;

public class BillingInfoMem {
    String name;
    String city;
    String email;
    String phone;
    String country;
    String zipcode;
    String address;

    public boolean isValid(){
        this.phone = this.phone.replaceAll("[- ()]*","");
        boolean validity = true;
        if (! this.name.matches("[a-zA-Z ]+")){
            this.name = "error";
            validity = false;
        }
        if (! this.city.matches("[a-zA-Z ]+")){
            this.city = "error";
            validity = false;
        }
        if (! this.country.matches("[a-zA-Z ]+")){
            this.country = "error";
            validity = false;
        }
        if (! this.address.matches("[a-zA-Z0-9 .-/,]+")){
            this.address = "error";
            validity = false;
        }
        if (! this.phone.matches("(([0-9]{7})|([0-9]{9})|(061[0-9]{7})|(06[0-9]{9})|(\\+361[0-9]{7})|(\\+36[0-9]{9}))")){
            this.phone = "error";
            validity = false;
        }
        if (! this.zipcode.matches("[0-9]+")){
            this.zipcode = "error";
            validity = false;
        }
        if (! this.email.matches("[a-zA-Z._]+@[a-zA-Z]+\\.(com|hu)")){
            this.email = "error";
            validity = false;
        }
        return validity;
    }
}
