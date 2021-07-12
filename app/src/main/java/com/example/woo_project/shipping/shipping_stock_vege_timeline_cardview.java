package com.example.woo_project.shipping;

public class shipping_stock_vege_timeline_cardview {

    private String work_title,start_date,end_date;
    private int takesday;

    public shipping_stock_vege_timeline_cardview(String work_title, String start_date, String end_date, int takesday) {
        this.work_title = work_title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.takesday = takesday;
    }

    public String getWork_title() {
        return work_title;
    }

    public void setWork_title(String work_title) {
        this.work_title = work_title;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getTakesday() {
        return takesday;
    }

    public void setTakesday(int takesday) {
        this.takesday = takesday;
    }
}
