package com.example.woo_project.reminder;

public class reminder_second_layer_cardview {
    private int id;
    private String greenhouse,num;

    public reminder_second_layer_cardview(){
        super();
    }

    public reminder_second_layer_cardview(int id, String greenhouse, String num)
    {
        super();
        this.greenhouse = greenhouse;
        this.num = num;
    }

    public String getGreenhouse(){
        return greenhouse;
    }

    public void setGreenhouse(){
        this.greenhouse = greenhouse;
    }

    public int getId(){
        return id;
    }

    public void setId(){
        this.id = id;
    }

    public String getNum(){
        return num;
    }

    public void setNum(){
        this.num = num;
    }
}
