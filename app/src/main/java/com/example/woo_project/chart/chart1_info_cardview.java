package com.example.woo_project.chart;

public class chart1_info_cardview {


        private int id;
        private String canopy,harvest_date,sales_date,sales_vendor;
        private double sales_num,sales_price;

        public chart1_info_cardview(){
            super();
        }

        public chart1_info_cardview(int id, String canopy, String harvest_date, double sales_num, String sales_date, double sales_price, String sales_vendor)
        {
            super();
            this.id = id;
            this.canopy = canopy;
            this.harvest_date = harvest_date;
            this.sales_num = sales_num;
            this.sales_date = sales_date;
            this.sales_price = sales_price;
            this.sales_vendor = sales_vendor;
        }

        public String getHarvest_date(){
            return harvest_date;
        }
        public void setHarvest_date(){
            this.harvest_date = harvest_date;
        }

        public String getCanopy(){
            return canopy;
        }
        public void setCanopy(){
            this.canopy = canopy;
        }

        public double getSales_num(){ return sales_num;}
        public void setSales_num(){this.sales_num = sales_num;}

        public String getSales_date(){
        return sales_date;
    }
        public void setSales_date(){
        this.sales_date = sales_date;
    }


        public double getSales_price(){ return sales_price;}
        public void setSales_price(){this.sales_price = sales_price;}

        public String getSales_vendor(){
        return sales_vendor;
    }
        public void setSales_vendor(){
        this.sales_vendor = sales_vendor;
    }

        public int getId(){
            return id;
        }
        public void setId(){
            this.id = id;
        }



}
