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
import android.widget.Toast;

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
                    //感應觸控時間長度
//                    if(endTime > 0)
//                    {
//                        if( ((int) pressTime) < 500)
//                        {
//                            removeAllItem(cardviewList.size());
//                            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
//                            home2_fg1 home2_fg1= new home2_fg1();
//                            FragmentTransaction fragmentTransaction;
//                            fragmentTransaction = fragmentManager.beginTransaction();
//                            fragmentTransaction.add(R.id.canopy_fg, home2_fg1);
//                            fragmentTransaction.commit();
//                            Log.v("test","fg presstime: "+ pressTime );
//                            endTime = -1;
//                        }
//                        else if(500 <= ((int) pressTime) || ((int) pressTime) < 2500)
//                        {
//                            Log.v("test","createinfo presstime: "+ pressTime );
//                            createPlantInfo(context);
//                            endTime = -1;
//                        }
//                        else
//                        {}
//                    }

                    createPlantInfo(context,cardview.getName());
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

        private TextView set_plant_name,set_plant_num,set_plant_date;


        private canopy_dialog canopy_dialog;
        private List<home2_dialog_cardview> canopy_plant_cardviewList = new ArrayList<>();
        public  void createPlantInfo(final Context context,String canopy_name){

            View view = LayoutInflater.from( context ).inflate( R.layout.home_canopy_dialog, null );

            TextView home_canopy_name,home_canopy_mode;
            ImageView add_info;
            home_canopy_name = view.findViewById(R.id.home_canopy_name);
            home_canopy_mode = view.findViewById(R.id.home_canopy_mode);
            add_info = view.findViewById(R.id.add_info);
            set_plant_name = view.findViewById(R.id.set_plant_name);
            set_plant_num = view.findViewById(R.id.set_plant_num);
            set_plant_date = view.findViewById(R.id.set_plant_date);

            //棚架名稱
            home_canopy_name.setText(canopy_name);


            //棚架內蔬菜
             final RecyclerView home_canopy_dialog_recyclerview = view.findViewById(R.id.home_canopy_dialog_recyclerview);


            home_canopy_dialog_recyclerview.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
            //canopy_plant_cardviewList.clear();


            int index = 0;

            //按+ 把蔬菜加入
            add_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!set_plant_name.getText().toString().equals("") &&
                    Integer.parseInt(set_plant_num.getText().toString())> 0 && !set_plant_date.getText().toString().equals(""))
                    {
                        Log.v("test","canopy_plant_cardviewList.size(): "+canopy_plant_cardviewList.size());
                        canopy_plant_cardviewList.add(new home2_dialog_cardview(canopy_plant_cardviewList.size(),set_plant_name.getText().toString(),
                                Integer.parseInt(set_plant_num.getText().toString()),set_plant_date.getText().toString()));
                        Log.v("test","canopy_plant_cardviewList.size()2: "+canopy_plant_cardviewList.size());
                        canopy_dialog = new canopy_dialog(context, canopy_plant_cardviewList);
                        Log.v("test","canopy_plant_cardviewList.size()3: "+canopy_plant_cardviewList.size());
                        home_canopy_dialog_recyclerview.setAdapter(canopy_dialog);
                        Log.v("test","canopy_plant_cardviewList.size()4: "+canopy_plant_cardviewList.size());

                    }
                    else
                    {
                        Toast.makeText(context,"格式錯誤!",Toast.LENGTH_SHORT).show();
                    }

                }
            });







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


