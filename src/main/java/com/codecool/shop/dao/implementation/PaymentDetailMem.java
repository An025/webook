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

    public boolean isValid(){
        boolean validity = true;
        if (isPayPal){
            if (! this.email.matches("[a-zA-Z._]+@[a-zA-Z]+\\.(com|hu)")){
                this.email = "error";
                validity = false;
            }
            if (this.password.isEmpty()){
                this.password = "error";
                validity = false;
            }
        } else {
            if (! this.name.matches("[a-zA-Z ]+")){
                this.name = "error";
                validity = false;
            }
            if (! this.cardNo.matches("[0-9]{16}")){
                this.cardNo = "error";
                validity = false;
            }
            if (! this.expDate.matches("[0-9]{2}/[0-9]{2}")){
                this.expDate = "error";
                validity = false;
            }
            if (! this.cvcCode.matches("[0-9]{3}")){
                this.cvcCode = "error";
                validity = false;
            }
        }

        return validity;
    }


}
