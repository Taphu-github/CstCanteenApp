package com.example.canteenapp;

public class DataMenuModel {

    String feedback,name;

    public DataMenuModel(){

    }

    public DataMenuModel(String feedback, String name) {
        this.feedback = feedback;
        this.name = name;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
