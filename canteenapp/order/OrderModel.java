package com.example.canteenapp.order;

public class OrderModel {

    String email,items,amount,state;

    OrderModel(){

    }

    public OrderModel(String email, String items, String amount, String state) {
        this.email = email;
        this.items = items;
        this.amount = amount;
        this.state = state;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
