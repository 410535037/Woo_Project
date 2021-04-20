package com.example.woo_project.reminder;

public class reminder_planting_bottomsheetdialog_cardview {

    String cardview_canopy,cardview_date;
    int cardview_num;
    int id;
    public reminder_planting_bottomsheetdialog_cardview(int id, String cardview_canopy, int cardview_num, String cardview_date){
        super();

        this.id = id;
        this.cardview_canopy = cardview_canopy;
        this.cardview_num = cardview_num;
        this.cardview_date = cardview_date;

    }

    public void setId(int id){ this.id=id; }
    public int getId(){return id;}

    public void setCardview_canopy(String cardview_canopy){this.cardview_canopy=cardview_canopy;}
    public String getCardview_canopy(){return cardview_canopy;}

    public void setCardview_num(int cardview_num){ this.cardview_num=cardview_num; }
    public int getCardview_num(){return cardview_num;}

    public void setCardview_date(String cardview_date){this.cardview_date=cardview_date;}
    public String getCardview_date(){return cardview_date;}

}
