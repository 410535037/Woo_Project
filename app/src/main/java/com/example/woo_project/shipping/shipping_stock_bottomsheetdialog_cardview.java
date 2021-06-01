package com.example.woo_project.shipping;

public class shipping_stock_bottomsheetdialog_cardview {

    private String title,date;
    private int takesday;
    private String description;

    public shipping_stock_bottomsheetdialog_cardview(String title, String date, int takesday, String description) {
        this.title = title;
        this.date = date;
        this.takesday = takesday;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTakesday() {
        return takesday;
    }

    public void setTakesday(int takesday) {
        this.takesday = takesday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
