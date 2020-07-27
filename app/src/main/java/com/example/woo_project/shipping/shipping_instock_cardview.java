package com.example.woo_project.shipping;

public class shipping_instock_cardview
{
    private String vege_name;
    private double instock_total=0.0,weight_inorder=0.0;

    public shipping_instock_cardview(){
        super();
    }

    public shipping_instock_cardview(String vege_name,double instock_total,double weight_inorder)
    {
        super();
        this.vege_name = vege_name;
        this.instock_total = instock_total;
        this.weight_inorder = weight_inorder;
    }

    public String getvege_name(){
        return vege_name;
    }

    public void setvege_name(){
        this.vege_name = vege_name;
    }

    public double getinstock_total(){ return instock_total; }

    public void setinstock_total(){
        this.instock_total = instock_total;
    }

    public double getweight_inorder(){
        return weight_inorder;
    }

    public void setweight_inorder(){
        this.weight_inorder = weight_inorder;
    }
}
