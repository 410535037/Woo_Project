package com.example.woo_project.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.woo_project.R;

import java.util.ArrayList;
import java.util.List;

public class home2_fg1 extends Fragment
{
    List<home2_plant_img_cardview> canopy_cardviewList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fg1, container, false);
        canopy_cardviewList = new ArrayList<>();


        RecyclerView recyclerView = view.findViewById(R.id.home2_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        for(int i = 0 ;i < 20;i++)
        {
            canopy_cardviewList.add(new home2_plant_img_cardview(1,"B  "+i,R.drawable.home_canopy,""));
        }
        recyclerView.setAdapter(new home2_fg1.canopy_item_CardAdapter(getContext(), canopy_cardviewList));


        return view;
    }

    private class canopy_item_CardAdapter extends  RecyclerView.Adapter<canopy_item_CardAdapter.ViewHolder>
    {
        private Context context;
        public List<home2_plant_img_cardview> cardviewList;

        canopy_item_CardAdapter(Context context, List<home2_plant_img_cardview> cardviewList) {
            this.context = context;
            this.cardviewList = cardviewList;
        }

        @Override
        public canopy_item_CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.home_canopy_item,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(canopy_item_CardAdapter.ViewHolder viewHolder, int i) {
            final home2_plant_img_cardview cardview = cardviewList.get(i);
            if(!cardview.getName().equals(""))
            {
                //viewHolder.plant_name.setText(cardview.getId()+"  "+cardview.getName());
            }
            // viewHolder.plant_img.setImageResource(cardview.getImage());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    cardviewList.clear();
                }
            });
        }

        @Override
        public int getItemCount() {
            return cardviewList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            ImageView plant_img;
            TextView plant_name;

            ViewHolder(View itemView){
                super(itemView);
                //  plant_img = itemView.findViewById(R.id.home2_plant_img);
                plant_name = itemView.findViewById(R.id.home2_plant_name);

            }
        }
        public  void addItem(int i){
//            fg = true;
//            num = cardviewList.size()-1;
//            //add(位置,資料)
//            cardviewList.add(i, new record_Cardview(id,"小白菜", R.drawable.icon201));
//            id=id+1;
//            notifyItemInserted(i);
        }
    }

}
