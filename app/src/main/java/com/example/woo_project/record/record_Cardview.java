package com.example.woo_project.record;


import android.graphics.Bitmap;

public class record_Cardview
{
    private String id;
    private String name,time;
    private String image;

    public record_Cardview(){
        super();
    }

    public record_Cardview(String id,String name,String time)
    {
        super();
        this.id = id;
        this.name = name;
        //this.image = image;
        this.time = time;
    }

    public String getTime(){
        return time;
    }

    public void setTime(){
        this.time = time;
    }

    public String getName(){
        return name;
    }

    public void setName(){
        this.name = name;
    }

    public String getImage(){
        return image;
    }

    public void setImage(){
        this.image = image;
    }

    public String getId(){
        return id;
    }

    public void setId(){
        this.id = id;
    }

}
