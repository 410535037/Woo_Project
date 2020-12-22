package com.example.woo_project.reminder;

import android.widget.ImageView;

public class reminder_cardview {
    private String id,vege_img,name,tag1,tag2,check_img;


    public reminder_cardview(){
        super();
    }

    public reminder_cardview(String id, String vege_img, String name, String tag1, String tag2,String check_img)
    {
        super();
        this.id = id;
        this.vege_img = vege_img;
        this.name = name;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.check_img=check_img;
    }

    public String getTag1(){
        return tag1;
    }

    public String getTag2(){
        return tag2;
    }

    public String getName(){
        return name;
    }

    public String getVege_img(){
        return vege_img;
    }

    public String getId(){
        return id;
    }

    public String getCheck_img(){
        return check_img;
    }

}
