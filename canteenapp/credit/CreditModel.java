package com.example.canteenapp.credit;

public class CreditModel {

    String amount, email, items,state;

    CreditModel(){

    }

    public CreditModel(String amount, String email, String items, String state) {
        this.amount = amount;
        this.email = email;
        this.items = items;
        this.state = state;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
