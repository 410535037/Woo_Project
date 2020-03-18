package com.example.woo_project.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woo_project.R;

import java.util.List;


    public  class canopy_CardAdapter extends  RecyclerView.Adapter<canopy_CardAdapter.ViewHolder>
    {


        Context context;
       public List<home2_plant_img_cardview> cardviewList;

        canopy_CardAdapter(Context context,List<home2_plant_img_cardview> cardviewList) {
            this.context = context;
            this.cardviewList = cardviewList;
        }

        @Override
        public canopy_CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_home2_plant_img,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(canopy_CardAdapter.ViewHolder viewHolder, int i) {
            final home2_plant_img_cardview cardview = cardviewList.get(i);

            viewHolder.plant_name.setText(cardview.getName());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    removeAllItem(cardviewList.size());
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    home2_fg1 home2_fg1= new home2_fg1();
                    FragmentTransaction fragmentTransaction;
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.canopy_fg, home2_fg1);
                    fragmentTransaction.commit();

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

        // 刪除數據
        public void removeItem(int position) {
            cardviewList.remove(position);
            //刪除動畫
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }

        // 刪除數據
        public void removeAllItem(int position) {
            for(int i = 0; i< position;i++)
            {
                cardviewList.remove(0);
                //刪除動畫
                notifyItemRemoved(0);
                notifyDataSetChanged();
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


