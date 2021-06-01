package com.example.woo_project.reminder;

public class reminder_harvest_bottomsheetdialog_cardview {

    String cardview_harvest_date;
    int cardview_harvest_num;
    int id;
    public reminder_harvest_bottomsheetdialog_cardview(int id, int cardview_harvest_num, String cardview_harvest_date){
        super();

        this.id = id;

        this.cardview_harvest_num = cardview_harvest_num;
        this.cardview_harvest_date = cardview_harvest_date;

    }

    public void setId(int id){ this.id=id; }
    public int getId(){return id;}

    public void setCardview_harvest_num(int cardview_harvest_num){ this.cardview_harvest_num=cardview_harvest_num; }
    public int getCardview_harvest_num(){return cardview_harvest_num;}

    public void setCardview_harvest_date(String cardview_harvest_date){this.cardview_harvest_date=cardview_harvest_date;}
    public String getCardview_harvest_date(){return cardview_harvest_date;}

}
