package com.example.woo_project.record;

public class record_information_cardview
{

    private int id;
    private String week,day1,day2,day3,day4,day5,day6,day7;
    private String day_or_week_num,datetime,content;

    public record_information_cardview(){
            super();
        }
        //public record_information_cardview(int id,String week,String day1,String day2,String day3,String day4,String day5,String day6,String day7)
        public record_information_cardview(int id, String day_or_week_num,String datetime,String content)
        {
            super();
            this.id = id;
            this.week = week;
            this.day1 = day1;
            this.day2 = day2;
            this.day3 = day3;
            this.day4 = day4;
            this.day5 = day5;
            this.day6 = day6;
            this.day7 = day7;
            this.day_or_week_num = day_or_week_num;
            this.datetime = datetime;
            this.content = content;



        }


    public String getDay_or_week_num(){
        return day_or_week_num;
    }
    public void setDay_or_week_num(){
        this.day_or_week_num = day_or_week_num;
    }

    public String getDatetime(){
        return datetime;
    }
    public void setDatetime(){
        this.datetime = datetime;
    }

    public String getContent(){
        return content;
    }
    public void setContent(){
        this.content = content;
    }





    public String getWeek(){
            return week;
        }
    public void setWeek(){
            this.week = week;
        }

    public int getId(){
            return id;
        }
    public void setId(){
            this.id = id;
        }

    public String getDay1(){
        day1 = getTV_HtoV.getTextHtoV(day1);
        return day1;
    }
    public void setDay1(){
        this.day1 = day1;
    }

    public String getDay2(){
        day2 = getTV_HtoV.getTextHtoV(day2);
        return day2;
    }
    public void setDay2(){
        this.day2 = day2;
    }

    public String getDay3(){
        day3 = getTV_HtoV.getTextHtoV(day3);
        return day3;
    }
    public void setDay3(){
        this.day3 = day3;
    }

    public String getDay4(){
        day4 = getTV_HtoV.getTextHtoV(day4);
        return day4;
    }
    public void setDay4(){
        this.day4 = day4;
    }

    public String getDay5(){
        day5 = getTV_HtoV.getTextHtoV(day5);
        return day5;
    }
    public void setDay5(){
        this.day5 = day5;
    }

    public String getDay6(){
        day6 = getTV_HtoV.getTextHtoV(day6);
        return day6;
    }
    public void setDay6(){
        this.day6 = day6;
    }

    public String getDay7(){
        day7 = getTV_HtoV.getTextHtoV(day7);
        return day7;
    }
    public void setDay7(){
        this.day7 = day7;
    }

}
