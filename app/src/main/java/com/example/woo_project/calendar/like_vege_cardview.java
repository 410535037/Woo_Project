package com.example.woo_project.calendar;

import android.graphics.Bitmap;

public class like_vege_cardview {
    private int id;
    private String name,tag1,tag2;
    private Bitmap image;

    public like_vege_cardview(){
        super();
    }

    public like_vege_cardview(int id,String name,Bitmap image,String tag1,String tag2)
    {
        super();
        this.name = name;
        this.image = image;
        this.tag1 = tag1;
        this.tag2 = tag2;
    }

    public String getTag1(){
        return tag1;
    }

    public void setTag1(){
        this.tag1 = tag1;
    }

    public String getTag2(){
        return tag2;
    }

    public void setTag2(){
        this.tag2 = tag2;
    }

    public String getName(){
        return name;
    }

    public void setName(){
        this.name = name;
    }

    public Bitmap getImage(){
        return image;
    }

    public void setImage(){
        this.image = image;
    }

    public int getId(){
        return id;
    }

    public void setId(){
        this.id = id;
    }


}