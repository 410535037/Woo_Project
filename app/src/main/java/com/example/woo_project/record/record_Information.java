package com.example.woo_project.record;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woo_project.R;

import java.util.ArrayList;
import java.util.List;

public class record_Information extends AppCompatActivity {
    boolean fg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_information);

        List<record_information_cardview> cardviewList = new ArrayList<>();
        cardviewList.add(new record_information_cardview(0, "第一週(19/04/27~19/04/28)", "澆水25次", "施肥1次", "間拔5次", "澆水25次", "施肥1次", "間拔5次", "澆水25次"));
        cardviewList.add(new record_information_cardview(0, "第一週(19/04/27~19/04/28)", "澆水25次", "施肥1次", "間拔5次", "澆水25次", "施肥1次", "間拔5次", "澆水25次"));
        cardviewList.add(new record_information_cardview(0, "第一週(19/04/27~19/04/28)", "澆水25次", "施肥1次", "間拔5次", "澆水25次", "施肥1次", "間拔5次", "澆水25次"));
        cardviewList.add(new record_information_cardview(0, "第一週(19/04/27~19/04/28)", "澆水25次", "施肥1次", "間拔5次", "澆水25次", "施肥1次", "間拔5次", "澆水25次"));
        cardviewList.add(new record_information_cardview(0, "第一週(19/04/27~19/04/28)", "澆水25次", "施肥1次", "間拔5次", "澆水25次", "施肥1次", "間拔5次", "澆水25次"));



        RecyclerView recyclerView = findViewById(R.id.record_info_RecyclerView);
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
            View view = LayoutInflater.from(context).inflate(R.layout.record_information_cardview, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CardAdapter.ViewHolder viewHolder, int i) {
            final record_information_cardview cardview = cardviewList.get(i);
            viewHolder.week.setText(String.valueOf(cardview.getWeek()));
            viewHolder.day1.setText(String.valueOf(cardview.getDay1()));
            viewHolder.day2.setText(String.valueOf(cardview.getDay2()));
            viewHolder.day3.setText(String.valueOf(cardview.getDay3()));
            viewHolder.day4.setText(String.valueOf(cardview.getDay4()));
            viewHolder.day5.setText(String.valueOf(cardview.getDay5()));
            viewHolder.day6.setText(String.valueOf(cardview.getDay6()));
            viewHolder.day7.setText(String.valueOf(cardview.getDay7()));

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

            ViewHolder(View itemView) {
                super(itemView);
                week = itemView.findViewById(R.id.record_weeks_date);
                day1 = itemView.findViewById(R.id.tv_day_1);
                day2 = itemView.findViewById(R.id.tv_day_2);
                day3 = itemView.findViewById(R.id.tv_day_3);
                day4 = itemView.findViewById(R.id.tv_day_4);
                day5 = itemView.findViewById(R.id.tv_day_5);
                day6 = itemView.findViewById(R.id.tv_day_6);
                day7 = itemView.findViewById(R.id.tv_day_7);
            }
        }
    }
}
