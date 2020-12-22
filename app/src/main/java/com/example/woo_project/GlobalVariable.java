package com.example.woo_project;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariable extends Application
{
    private String Word="No_message";     //要傳送的作物名稱
    private String Vege_item="";
    private String Action_item="";
    private List<String> Vege_image_home=new ArrayList<>();
    private String Select_month="無";
    private String Forum_content="錯誤",Forum_title="錯誤";
    private String Market_item="無";
    private String User_gmail="無";
    private String User_name="錯誤";
    private String Search_forum_string="無";
    private String User_email="";
    private String Record_vege_name="";
    private int Forum_title_click=-1;
    private String Select_vege_name="";
    private String Plant_id="";
    private String Choose_calendar_gmail="無",Choose_calendar_vege="無",Choose_calendar_id_string="無";
    private String Cardview_postion="";
    private int Cf;

    public void setChoose_calendar_info(String choose_calendar_id_string,String choose_calendar_gmail,String choose_calendar_vege)
    {
        this.Choose_calendar_gmail = choose_calendar_gmail;
        this.Choose_calendar_id_string = choose_calendar_id_string;
        this.Choose_calendar_vege = choose_calendar_vege;
    }
    public String getChoose_calendar_info(){ return Choose_calendar_gmail+"%"+Choose_calendar_id_string+"%"+Choose_calendar_vege;}

    //修改 變數字串
    public void setWord(String word){
        this.Word = word;
    }
    //顯示 變數字串
    public String getWord() {
        return Word;
    }

    public void setRecord_vege_name(String record_vege_name){this.Record_vege_name = record_vege_name;}
    public String getRecord_vege_name(){ return Record_vege_name;}

    public void setAction_item(String action_item){this.Action_item = action_item;}
    public String getAction_item(){ return Action_item;}

    //首頁作物圖片
    public void setVege_image_home(String vege_image_home){this.Vege_image_home.add(vege_image_home);}
    public List<String> getVege_image_home(){ return Vege_image_home;}

    public void setForum_content(String forum_content){this.Forum_content = forum_content;}
    public String getForum_content(){ return Forum_content;}

    public void setForum_title(String forum_title){this.Forum_title = forum_title;}
    public String getForum_title(){ return Forum_title;}

    public void setForum_title_click(int forum_title_click){this.Forum_title_click = forum_title_click;}
    public int getForum_title_click(){ return Forum_title_click;}

    public void setMarket_item(String market_item){this.Market_item = market_item;}
    public String getMarket_item(){ return Market_item;}

    public void setSearch_forum_string(String search_forum_string){this.Market_item = search_forum_string;}
    public String getSearch_forum_string(){ return Search_forum_string;}

    public void setSelect_month(String select_month){this.Select_month = select_month;  Log.v("test","gl的select: "+select_month);}
    public  String getSelect_month(){ Log.v("test","search的select: "+Select_month); return Select_month;}

    //登入後需要傳值到個人設定主頁，點擊個人資訊時需要用到
    public void setUser_email(String user_email){this.User_email = user_email;}
    public String getUser_email(){ return User_email;}

    //個人資訊填寫完後須將姓名傳到個人設定主頁
    public void setUser_name(String user_name){this.User_name = user_name;}
    public String getUser_name(){ return User_name;}



    public void setUser_gmail(String user_gmail){this.User_gmail = user_gmail;}
    public String getUser_gmail(){ return User_gmail;}

    public void setSelect_vege_name(String select_vege_name){this.Select_vege_name = select_vege_name;}
    public String getSelect_vege_name(){ return Select_vege_name;}

    public void setPlant_id(String plant_id){this.Plant_id = plant_id;}
    public String getPlant_id(){ return Plant_id;}

    public void setCardview_postion(String cardview_postion){this.Cardview_postion = cardview_postion;}
    public String getCardview_postion(){ return Cardview_postion;}

    public void setCf(int cf){this.Cf = cf;}
    public int getCf(){ return Cf;}

}
