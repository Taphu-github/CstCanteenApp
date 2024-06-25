package com.example.canteenapp;

public class DailyMenuModel {

    String name,price,available,purl;

    DailyMenuModel(){

    }

    public DailyMenuModel(String name, String price, String available, String purl) {
        this.name = name;
        this.price = price;
        this.available = available;
        this.purl = purl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
