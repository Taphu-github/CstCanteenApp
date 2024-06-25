package com.example.canteenapp.SQLitehelper;

public class DataModel {

    int id;
    String ItemName;
    float price;
    int quantity;


    public DataModel(){

    }

    public DataModel(int id, String itemName, float price, int quantity) {
        this.id = id;
        this.ItemName = itemName;
        this.price=price;
        this.quantity=quantity;


    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }
}
