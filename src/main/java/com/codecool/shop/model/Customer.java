package com.codecool.shop.model;

public class Customer {
    String name;
    String email;
    String password;
    String billingid;

    public Customer(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getBillingid() {
        return billingid;
    }

    public void setBillingid(String billingid) {
        this.billingid = billingid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", billingid='" + billingid + '\'' +
                '}';
    }
}
