package com.example.woo_project.reminder.json;

public class split_planting_setting {
    private int user,id,num;
    private String date,canopy;
    public  split_planting_setting()
    {
        super();
    }
    public split_planting_setting(int user, int id,int num,String date, String canopy)
    {
        super();
        this.user = user;
        this.id = id;
        this.canopy = canopy;
        this.num = num;
        this.date = date;

    }
    public void setUser(int user){this.user=user;}
    public int getUser(){return user;}

    public void setId(int id){ this.id=id; }
    public int getId(){return id;}

    public void setNum(int num){ this.num=num; }
    public int getNum(){return num;}

    public void setDate(String date){this.date=date;}
    public String getDate(){return date;}

    public void setCanopy(String canopy){this.canopy=canopy;}
    public String getCanopy(){return canopy;}
}
