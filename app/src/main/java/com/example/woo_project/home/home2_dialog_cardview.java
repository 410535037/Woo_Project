package com.example.woo_project.home;

public class home2_dialog_cardview
{
    private int id,num;
    private String name,date;

    public home2_dialog_cardview(int id,String name,int num,String date)
    {
        super();
        this.id = id;
        this.name = name;
        this.num = num;
        this.date = date;
    }

    public int getId(){return id;}
    public void setId(){this.id = id;}
    public String getName(){return name;}
    public void setName(){this.name = name;}
    public int getNum(){return num;}
    public void setNum(){this.num = num;}
    public String getDate(){return date;}
    public void setDate(){this.date = date;}
}
