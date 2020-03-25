package com.example.woo_project.home;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.woo_project.R;

import java.util.ArrayList;
import java.util.List;




public  class canopy_CardAdapter extends  RecyclerView.Adapter<canopy_CardAdapter.ViewHolder>
    {


        Context context;
       public List<home2_plant_img_cardview> cardviewList;
        private long startTime ,endTime,pressTime;


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

            viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startTime = event.getDownTime();
                            Log.v("test","start:"+startTime);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_UP:
                            endTime = event.getEventTime();
                            pressTime = endTime - startTime;
                            Log.v("test","press:"+pressTime);
                            Log.v("test","end:"+endTime);
                            break;

                    }
                    if(endTime > 0)
                    {
                    if( ((int) pressTime) < 500)
                    {
                        removeAllItem(cardviewList.size());
                        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                        home2_fg1 home2_fg1= new home2_fg1();
                        FragmentTransaction fragmentTransaction;
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.canopy_fg, home2_fg1);
                        fragmentTransaction.commit();
                        Log.v("test","fg presstime: "+ pressTime );
                        endTime = -1;
                    }
                    else if(500 <= ((int) pressTime) || ((int) pressTime) < 2500)
                    {
                        Log.v("test","createinfo presstime: "+ pressTime );
                        createPlantInfo(context);
                        endTime = -1;
                    }
                    else
                    {}
                    }
                    return true;
                }
            });




//            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//
//
//                    removeAllItem(cardviewList.size());
//                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
//                    home2_fg1 home2_fg1= new home2_fg1();
//                    FragmentTransaction fragmentTransaction;
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.add(R.id.canopy_fg, home2_fg1);
//                    fragmentTransaction.commit();
//
//                }
//            });
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

        public  void createPlantInfo(Context context){

            View view = LayoutInflater.from( context ).inflate( R.layout.home_canopy_dialog, null );

            TextView home_canopy_name,home_canopy_mode;
            home_canopy_name = view.findViewById(R.id.home_canopy_name);
            home_canopy_mode = view.findViewById(R.id.home_canopy_mode);
            RecyclerView home_canopy_dialog_recyclerview = view.findViewById(R.id.home_canopy_dialog_recyclerview);
            List<home2_dialog_cardview> canopy_cardviewList = new ArrayList<>();

            home_canopy_dialog_recyclerview.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
            canopy_cardviewList.add(new home2_dialog_cardview(0,"高麗菜",10,"2020-03-23"));
            canopy_cardviewList.add(new home2_dialog_cardview(1,"乃白",20,"2020-03-22"));
            canopy_cardviewList.add(new home2_dialog_cardview(2,"青江菜",50,"2020-03-24"));

            canopy_dialog canopy_dialog = new canopy_dialog(context, canopy_cardviewList);
            home_canopy_dialog_recyclerview.setAdapter(canopy_dialog);




        Dialog createPlantInfoDialog = new Dialog(context);
            createPlantInfoDialog.setContentView(view);

            // 調整Dialog從哪開始
            Window dialogWindow = createPlantInfoDialog.getWindow();

//            // 去除四角黑色背景
//            assert dialogWindow != null;
//            dialogWindow.setBackgroundDrawable(new BitmapDrawable());
            /* 將Dialog用螢幕大小百分比方式設置 */
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 取得對話框目前數值
            p.height = (int) (dm.heightPixels * 0.8); // 高度設為螢幕的0.8
            p.width = (int) (dm.widthPixels * 0.9);  // 寬度設為螢幕的0.8
            dialogWindow.setAttributes(p);
            createPlantInfoDialog.show();

        }


    }


