package com.example.woo_project.shipping;

public class shipping_unshipped_cardview {
    private String id,vege_name,vege_img,vendor,shipping_date;



    public shipping_unshipped_cardview(String id, String vege_name, String vege_img, String shipping_date,String vendor) {
        this.id = id;
        this.vege_name = vege_name;
        this.vege_img = vege_img;
        this.shipping_date = shipping_date;
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

    public String getShipping_date() {
        return shipping_date;
    }

    public void setShipping_date(String shipping_date) {
        this.shipping_date = shipping_date;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

}
