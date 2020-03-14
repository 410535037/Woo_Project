package com.example.woo_project.home;

import android.graphics.Bitmap;
import android.media.Image;

public class home2_plant_img_cardview
{
    private int id;
    private String name,index;
    private int image;

    public home2_plant_img_cardview(){
        super();
    }

    public home2_plant_img_cardview(int id,String name,int image,String index)
    {
        super();
        this.name = name;
        this.image = image;
        this.id = id;
        this.index = index;
    }

    public String getIndex(){
        return index;
    }

    public void setIndex(){
        this.index = index;
    }

    public String getName(){
        return name;
    }

    public void setName(){
        this.name = name;
    }

    public int getImage(){
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
