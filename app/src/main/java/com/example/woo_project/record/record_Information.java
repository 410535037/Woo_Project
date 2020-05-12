package com.example.woo_project.record;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woo_project.R;

import java.util.ArrayList;
import java.util.List;

public class record_Information extends AppCompatActivity {
    boolean fg = true;
    List<record_information_cardview> cardviewList2 = new ArrayList<>();
    RecyclerView recyclerView;
    List<String> cardview_index = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_information);

        List<record_information_cardview> cardviewList = new ArrayList<>();
       // cardviewList.add(new record_information_cardview(0, "第一週(19/04/27~19/04/28)", "澆水25次", "施肥1次", "間拔5次", "澆水25次", "施肥1次", "間拔5次", "澆水25次"));
        cardviewList.add(new record_information_cardview(0,"01","2020.04.13 10:01","第一週"));
        cardviewList.add(new record_information_cardview(1,"02","2020.04.20 11:20","第二週"));
        cardviewList.add(new record_information_cardview(2,"03","2020.04.27 12:35","第三週"));
        //public record_information_cardview(int id,String day_or_week_num,String datetime,String content)


        recyclerView = findViewById(R.id.record_info_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new CardAdapter(this, cardviewList));

    }

    private class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
        private Context context;
        public List<record_information_cardview> cardviewList;

        CardAdapter(Context context, List<record_information_cardview> cardviewList) {
            this.context = context;
            this.cardviewList = cardviewList;
        }

        @Override
        public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_choose_calendar_cardview,parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final CardAdapter.ViewHolder viewHolder,  int i) {
            final record_information_cardview cardview = cardviewList.get(i);
//            viewHolder.week.setText(String.valueOf(cardview.getWeek()));
//            viewHolder.day1.setText(String.valueOf(cardview.getDay1()));
//            viewHolder.day2.setText(String.valueOf(cardview.getDay2()));
//            viewHolder.day3.setText(String.valueOf(cardview.getDay3()));
//            viewHolder.day4.setText(String.valueOf(cardview.getDay4()));
//            viewHolder.day5.setText(String.valueOf(cardview.getDay5()));
//            viewHolder.day6.setText(String.valueOf(cardview.getDay6()));
//            viewHolder.day7.setText(String.valueOf(cardview.getDay7()));

            viewHolder.day_or_week_num.setText(cardview.getDay_or_week_num());
            viewHolder.datetime.setText(cardview.getDatetime());
            viewHolder.content.setText(cardview.getContent());
            viewHolder.day_or_week.setText("週");


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("test","i的值: " +cardview.getId());
                    if(cardview_index.indexOf(String.valueOf(cardview.getId())) < 0) {
                        cardview_index.add(String.valueOf(cardview.getId()));
                        cardviewList2.clear();
                        cardviewList2.add(new record_information_cardview(0,"02","2020.04.14 10:01","好吃"));
                        cardviewList2.add(new record_information_cardview(1,"03","2020.04.15 11:20","好吃好吃"));
                        cardviewList2.add(new record_information_cardview(2,"04","2020.04.16 12:35","好吃好吃好吃"));
                        cardviewList2.add(new record_information_cardview(3,"05","2020.04.17 10:01","好吃好吃好吃"));
                        cardviewList2.add(new record_information_cardview(4,"06","2020.04.18 11:20","好吃好吃"));
                        cardviewList2.add(new record_information_cardview(5,"07","2020.04.19 12:35","好吃"));
                        viewHolder.record_info2_RecyclerView.setLayoutManager(new LinearLayoutManager(record_Information.this, LinearLayoutManager.VERTICAL, false));
                        viewHolder.record_info2_RecyclerView.setAdapter(new CardAdapter2(record_Information.this, cardviewList2));


                        Log.v("test","第 "+ cardview.getId() +" cardview1  list長度" +cardviewList.size());

                    }
                    else{
                        cardview_index.remove(String.valueOf(cardview.getId()));
                        cardviewList2.clear();
                        viewHolder.record_info2_RecyclerView.setLayoutManager(new LinearLayoutManager(record_Information.this, LinearLayoutManager.VERTICAL, false));
                        viewHolder.record_info2_RecyclerView.setAdapter(new CardAdapter2(record_Information.this, cardviewList2));

                    }


                }
            });
        }

        @Override
        public int getItemCount() {
            return cardviewList.size();
        }

        //Adapter 需要一個 ViewHolder，只要實作它的 constructor 就好，保存起來的view會放在itemView裡面
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView week,day1,day2,day3,day4,day5,day6,day7;

            TextView day_or_week_num,day_or_week,datetime,content;
            ImageView img;
            RecyclerView record_info2_RecyclerView;


            ViewHolder(View itemView) {
                super(itemView);
//                week = itemView.findViewById(R.id.record_weeks_date);
//                day1 = itemView.findViewById(R.id.tv_day_1);
//                day2 = itemView.findViewById(R.id.tv_day_2);
//                day3 = itemView.findViewById(R.id.tv_day_3);
//                day4 = itemView.findViewById(R.id.tv_day_4);
//                day5 = itemView.findViewById(R.id.tv_day_5);
//                day6 = itemView.findViewById(R.id.tv_day_6);
//                day7 = itemView.findViewById(R.id.tv_day_7);

                day_or_week_num = itemView.findViewById(R.id.day_or_week_num);
                day_or_week = itemView.findViewById(R.id.day_or_week);
                datetime = itemView.findViewById(R.id.datetime);
                content = itemView.findViewById(R.id.content);
                img = itemView.findViewById(R.id.img);
                record_info2_RecyclerView = itemView.findViewById(R.id.record_info2_RecyclerView);
            }
        }
    }




    private class CardAdapter2 extends RecyclerView.Adapter<CardAdapter2.ViewHolder> {
        private Context context;
        public List<record_information_cardview> cardviewList;

        CardAdapter2(Context context, List<record_information_cardview> cardviewList) {
            this.context = context;
            this.cardviewList = cardviewList;
        }

        @Override
        public CardAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_choose_calendar_cardview,parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CardAdapter2.ViewHolder viewHolder, int i) {
            final record_information_cardview cardview = cardviewList.get(i);

            viewHolder.day_or_week_num.setText(cardview.getDay_or_week_num());
            viewHolder.datetime.setText(cardview.getDatetime());
            viewHolder.content.setText(cardview.getContent());
            viewHolder.day_or_week.setText("天");

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //        addItem(cardviewList.size());

//                    ImageView imageView = new ImageView(context);
//                    imageView.setImageResource(cardview.getImage());
//                    Toast toast = new Toast(context);
//                    toast.setView(imageView);
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return cardviewList.size();
        }

        //Adapter 需要一個 ViewHolder，只要實作它的 constructor 就好，保存起來的view會放在itemView裡面
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView week,day1,day2,day3,day4,day5,day6,day7;

            TextView day_or_week_num,day_or_week,datetime,content;
            ImageView img;
            RecyclerView record_info2_RecyclerView;


            ViewHolder(View itemView) {
                super(itemView);

                day_or_week_num = itemView.findViewById(R.id.day_or_week_num);
                day_or_week = itemView.findViewById(R.id.day_or_week);
                datetime = itemView.findViewById(R.id.datetime);
                content = itemView.findViewById(R.id.content);
                img = itemView.findViewById(R.id.img);
            }
        }
    }
}
