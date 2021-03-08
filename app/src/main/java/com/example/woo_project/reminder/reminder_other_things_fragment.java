package com.example.woo_project.reminder;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.woo_project.R;
import java.util.ArrayList;
import java.util.List;

public class reminder_other_things_fragment extends Fragment {
    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    private TextView mTextTitle;
    List<reminder_cardview> reminderList;
    List<reminder_second_layer_cardview> remindList2=new ArrayList<>();
    RecyclerView reminder_rv;
    ArrayList<Integer> counter = new ArrayList<>();
    public reminder_other_things_fragment() {
        // Requires empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.reminder_seedling_fragment_layout, container, false);
        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

        reminder_rv=root.findViewById(R.id.rv1);
        reminderList=new ArrayList<>();
        reminderList.add(new reminder_cardview("1", "fertilizer","施肥","#施肥日 :  2020/12/29", "#棚架 :  A10", "checked"));

        reminder_rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        reminder_rv.setHasFixedSize(true);
        reminder_rv.setAdapter(new reminder_other_things_fragment.reminder_first_layer_fragment_adapter(getActivity(), reminderList));

        return root;
    }

    private class reminder_first_layer_fragment_adapter extends RecyclerView.Adapter<com.example.woo_project.reminder.reminder_other_things_fragment.reminder_first_layer_fragment_adapter.viewholder>  {

        private Context mctx;
        private List<reminder_cardview> reminderList;
        String s="";
        public reminder_first_layer_fragment_adapter(Context mctx, List<reminder_cardview> reminderList) {
            this.mctx = mctx;
            this.reminderList = reminderList;
        }

        @Override
        public com.example.woo_project.reminder.reminder_other_things_fragment.reminder_first_layer_fragment_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mctx);
            View view=inflater.inflate(R.layout.reminder_cardview,viewGroup,false);
            return new com.example.woo_project.reminder.reminder_other_things_fragment.reminder_first_layer_fragment_adapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(final com.example.woo_project.reminder.reminder_other_things_fragment.reminder_first_layer_fragment_adapter.viewholder holder, final int position) {
            final reminder_cardview vege=reminderList.get(position);

            holder.vege.setText(String.valueOf(vege.getName()));
            int drawableResourceId = mctx.getResources().getIdentifier(vege.getVege_img(), "drawable", mctx.getPackageName());
            Glide.with(mctx)
                    .load(drawableResourceId)
                    .into(holder.vege_img);

            holder.tag1.setText(String.valueOf(vege.getTag1()));
            holder.tag2.setText(String.valueOf(vege.getTag2()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (counter.get(position) % 2 == 0) {
                        remindList2.clear();
                        remindList2.add(new reminder_second_layer_cardview(1,"#A01","25盤"));
                        remindList2.add(new reminder_second_layer_cardview(2,"#B01","30盤"));
                        holder.secondrecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                        holder.secondrecyclerview.setHasFixedSize(true);
                        holder.secondrecyclerview.setAdapter(new com.example.woo_project.reminder.reminder_other_things_fragment.reminder_second_layer_fragment_adapter(getActivity(), remindList2));
                        holder.secondrecyclerview.setVisibility(View.VISIBLE);
                    } else {
                        holder.secondrecyclerview.setVisibility(View.GONE);
                    }

                    counter.set(position, counter.get(position) + 1);
                    holder.secondrecyclerview.smoothScrollToPosition(position);

                }
            });

        }

        @Override
        public int getItemCount() {
            return reminderList.size();
        }



        class viewholder extends RecyclerView.ViewHolder {
            ImageView vege_img;
            TextView vege,tag1,tag2;
            ImageButton plus_imb,more_imb;
            RecyclerView secondrecyclerview;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                vege_img = (ImageView) itemView.findViewById(R.id.vege_img);
                vege = (TextView) itemView.findViewById(R.id.vegename);
                tag1 = (TextView) itemView.findViewById(R.id.tag1_tv);
                tag2 = (TextView) itemView.findViewById(R.id.tag2_tv);
                plus_imb = itemView.findViewById(R.id.plus_imb);
                more_imb = itemView.findViewById(R.id.more_imb);
                secondrecyclerview=itemView.findViewById(R.id.innerRecyclerview);

            }


        }

    }


    private class reminder_second_layer_fragment_adapter extends RecyclerView.Adapter<com.example.woo_project.reminder.reminder_other_things_fragment.reminder_second_layer_fragment_adapter.viewholder>  {

        private Context mctx;
        private List<reminder_second_layer_cardview> remindList2;
        String s="";
        public reminder_second_layer_fragment_adapter(Context mctx, List<reminder_second_layer_cardview> remindList2) {
            this.mctx = mctx;
            this.remindList2 = remindList2;
        }

        @Override
        public com.example.woo_project.reminder.reminder_other_things_fragment.reminder_second_layer_fragment_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mctx);
            View view=inflater.inflate(R.layout.reminder_second_layer_cardview,viewGroup,false);
            return new com.example.woo_project.reminder.reminder_other_things_fragment.reminder_second_layer_fragment_adapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(final com.example.woo_project.reminder.reminder_other_things_fragment.reminder_second_layer_fragment_adapter.viewholder holder, int position) {
            final reminder_second_layer_cardview vege2=remindList2.get(position);

            holder.vege2.setText(String.valueOf(vege2.getGreenhouse()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return remindList2.size();
        }



        class viewholder extends RecyclerView.ViewHolder {
            TextView vege2;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                vege2 = (TextView) itemView.findViewById(R.id.itemTextView);
            }


        }

    }
}
