package com.example.woo_project.shipping;

public class shipping_unshipped_cardview {

    //庫存量cardview需要的資料
    private String id,vege_name,vege_img,vendor,shipping_day,tag1_planting_greenhouse;
    private int shipping_num;


    //庫存量cardview點擊箱子圖案需要的資料
    private String seedling_day,unit,planting_day,harvest_day;
    private int seedling_num,seedling_takesday,planting_num,planting_takesday,harvest_num;

    public shipping_unshipped_cardview(String id, String vege_name, String vege_img, String vendor, String shipping_day, String tag1_planting_greenhouse, int shipping_num, String seedling_day, String unit, String planting_day, String harvest_day, int seedling_num, int seedling_takesday, int planting_num, int planting_takesday, int harvest_num) {
        this.id = id;
        this.vege_name = vege_name;
        this.vege_img = vege_img;
        this.vendor = vendor;
        this.shipping_day = shipping_day;
        this.tag1_planting_greenhouse = tag1_planting_greenhouse;
        this.shipping_num = shipping_num;
        this.seedling_day = seedling_day;
        this.unit = unit;
        this.planting_day = planting_day;
        this.harvest_day = harvest_day;
        this.seedling_num = seedling_num;
        this.seedling_takesday = seedling_takesday;
        this.planting_num = planting_num;
        this.planting_takesday = planting_takesday;
        this.harvest_num = harvest_num;
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

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getShipping_day() {
        return shipping_day;
    }

    public void setShipping_day(String shipping_day) {
        this.shipping_day = shipping_day;
    }

    public String getTag1_planting_greenhouse() {
        return tag1_planting_greenhouse;
    }

    public void setTag1_planting_greenhouse(String tag1_planting_greenhouse) {
        this.tag1_planting_greenhouse = tag1_planting_greenhouse;
    }

    public int getShipping_num() {
        return shipping_num;
    }

    public void setShipping_num(int shipping_num) {
        this.shipping_num = shipping_num;
    }

    public String getSeedling_day() {
        return seedling_day;
    }

    public void setSeedling_day(String seedling_day) {
        this.seedling_day = seedling_day;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPlanting_day() {
        return planting_day;
    }

    public void setPlanting_day(String planting_day) {
        this.planting_day = planting_day;
    }

    public String getHarvest_day() {
        return harvest_day;
    }

    public void setHarvest_day(String harvest_day) {
        this.harvest_day = harvest_day;
    }

    public int getSeedling_num() {
        return seedling_num;
    }

    public void setSeedling_num(int seedling_num) {
        this.seedling_num = seedling_num;
    }

    public int getSeedling_takesday() {
        return seedling_takesday;
    }

    public void setSeedling_takesday(int seedling_takesday) {
        this.seedling_takesday = seedling_takesday;
    }

    public int getPlanting_num() {
        return planting_num;
    }

    public void setPlanting_num(int planting_num) {
        this.planting_num = planting_num;
    }

    public int getPlanting_takesday() {
        return planting_takesday;
    }

    public void setPlanting_takesday(int planting_takesday) {
        this.planting_takesday = planting_takesday;
    }

    public int getHarvest_num() {
        return harvest_num;
    }

    public void setHarvest_num(int harvest_num) {
        this.harvest_num = harvest_num;
    }
}
