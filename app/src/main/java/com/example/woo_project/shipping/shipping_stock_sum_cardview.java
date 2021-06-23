package com.example.woo_project.shipping;

public class shipping_stock_sum_cardview {
    private String id,vege_name,vege_img,vendor;
    private int harvest_num;



    public shipping_stock_sum_cardview(String id, String vege_name, String vege_img, int harvest_num,String vendor) {
        this.id = id;
        this.vege_name = vege_name;
        this.vege_img = vege_img;
        this.harvest_num = harvest_num;
        this.vendor = vendor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVege_name() {
        return vege_name;
    }

    public void setVege_name(String vege_name) {
        this.vege_name = vege_name;
    }

    public String getVege_img() {
        return vege_img;
    }

    public void setVege_img(String vege_img) {
        this.vege_img = vege_img;
    }

    public int getHarvest_num() {
        return harvest_num;
    }

    public void setHarvest_num(int harvest_num) {
        this.harvest_num = harvest_num;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

}
