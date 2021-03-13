package com.example.woo_project.reminder;

import android.widget.ImageView;

public class reminder_cardview {
    private String id,vege_img,name,tag1,tag2;
    private String check_img,vendor,remark,preharvest,pregrowing,unit;

    private int preday_num,pregrowing_num;

    public reminder_cardview(){
        super();
    }

    public reminder_cardview(String id, String vege_img, String name, String tag1, String tag2,String unit,
                             String check_img, String vendor, String remark, String preharvest, String pregrowing, int preday_num, int pregrowing_num)
    {
        super();
        this.id = id;
        this.vege_img = vege_img;
        this.name = name;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.unit = unit;
        this.check_img=check_img;

        this.vendor = vendor;
        this.remark = remark;
        this.preharvest = preharvest;
        this.pregrowing = pregrowing;
        this.preday_num = preday_num;
        this.pregrowing_num = pregrowing_num;

    }


    public String getTag1(){
        return tag1;
    }

    public String getTag2(){
        return tag2;
    }

    public void setUnit(String unit){ this.unit = unit;}
    public String getUnit(){
        return unit;
    }

    public String getName(){
        return name;
    }

    public String getVege_img(){
        return vege_img;
    }

    public String getId(){
        return id;
    }

    public void setCheck_img(String check_img){ this.check_img = check_img;}
    public String getCheck_img(){
        return check_img;
    }

    public void setVendor(String vendor){ this.vendor = vendor;}
    public String getVendor(){
        return vendor;
    }

    public void setPreharvest(String preharvest){ this.preharvest = preharvest;}
    public String getPreharvest(){
        return preharvest;
    }

    public void setPregrowing(String pregrowing){ this.pregrowing = pregrowing;}
    public String getPregrowing(){
        return pregrowing;
    }

    public void setRemark(String remark){ this.remark = remark;}
    public String getRemark(){
        return remark;
    }

    public void setPregrowing_num(int pregrowing_num){ this.pregrowing_num = pregrowing_num;}
    public int getPregrowing_num(){
        return pregrowing_num;
    }

    public void setPreday_num(int preday_num){ this.preday_num = preday_num;}
    public int getPreday_num(){
        return preday_num;
    }







}
