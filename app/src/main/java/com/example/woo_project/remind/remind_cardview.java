package com.example.woo_project.remind;

public class remind_cardview {
    private int id;
    private String name,tag1,tag2;
    private int image;
    private boolean isSelected;
    int pp=1;

    public remind_cardview(){
        super();
    }

    public remind_cardview(int id, String name, int image, String tag1, String tag2, boolean isSelected)
    {
        super();
        this.name = name;
        this.image = image;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.isSelected=isSelected;
    }

    public String getTag1(){
        return tag1;
    }

    public void setTag1(){
        this.tag1 = tag1;
    }

    public String getTag2(){
        return tag2;
    }

    public void setTag2(){
        this.tag2 = tag2;
    }

    public String getName(){
        return name;
    }

    public void setName(){
        this.name = name;
    }

    public int getImage(){
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

    public boolean getisSelected(){
        return isSelected;
    }

    public void setisSelected(){
        this.isSelected = isSelected;
    }
}
