package com.example.woo_project.calendar;

import android.graphics.Bitmap;

public class choose_calendar_cardview
{
    private int id;
    private String day,time,message;
    private String image;
    private String id_string,gmail,URL,place,vege;

    public choose_calendar_cardview(String id_string,String gmail,String URL,String place,String vege)
    {
        super();
        this.id_string = id_string;
        this.gmail = gmail;
        this.URL = URL;
        this.place = place;
        this.vege = vege;
    }
    public String getvege(){
        return vege;
    }

    public void setvege(){
        this.vege = vege;
    }

    public String getId_string(){
        return id_string;
    }

    public void setId_string(){
        this.id_string = id_string;
    }

    public String getgmail(){
        return gmail;
    }

    public void setgmail(){
        this.gmail = gmail;
    }

    public String getURL(){
        return URL;
    }

    public void setURL(){
        this.URL = URL;
    }

    public String getplace(){
        return place;
    }

    public void setplace(){
        this.place = place;
    }





    public choose_calendar_cardview(int id,String day,String time,String message,String image)
    {
        super();
        this.day = day;
        this.image = image;
        this.time = time;
        this.message = message;
    }

    public String getTime(){
        return time;
    }

    public void setTime(){
        this.time = time;
    }

    public String getDay(){
        return day;
    }

    public void setDay(){
        this.day = day;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(){
        this.message = message;
    }

    public String getImage(){
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
