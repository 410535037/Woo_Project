package com.example.woo_project.record;

public class record_Cardview
{
    private int id;
    private String name,time;
    private String image;

    public record_Cardview(){
        super();
    }

    public record_Cardview(int id,String name,String time)
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

    public int getId(){
        return id;
    }

    public void setId(){
        this.id = id;
    }

}
