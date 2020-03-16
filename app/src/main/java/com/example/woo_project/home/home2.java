package com.example.woo_project.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.woo_project.GlobalVariable;
import com.example.woo_project.R;

import com.example.woo_project.user_setting.user_setting;
import com.example.woo_project.webservice;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class home2 extends AppCompatActivity {

    BottomSheetDialog bottomSheetDialog;
    ImageButton record,calendar,discuss,store,setting,user,hat,edit_pot;
    Dialog banboo_hat_level;
    String delete_cardview_id;
    GlobalVariable GV; //首頁作物照片(暫時)
    int cardview_id;

    List<home2_plant_img_cardview> cardviewList;
    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private android.os.Handler mUI_Handler = new android.os.Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    String user_vege,gmail;
    RecyclerView recyclerView;
    ProgressDialog mLoadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_home2 );

       // banboo_hat_level=new Dialog(this);

        cardviewList = new ArrayList<>();

        recyclerView = findViewById(R.id.home2_recyclerview);

        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");


        GV = (GlobalVariable) getApplicationContext();
        gmail = GV.getUser_gmail();
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());
        mThreadHandler.post(home2_cardview_r1);
        mLoadingDialog = new ProgressDialog(home2.this);
        showLoadingDialog("載入中...");

        edit_pot=(ImageButton) findViewById(R.id.edit_pot);
        edit_pot.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(home2.this);
                builder.setTitle("編輯盆栽☆即將推出，敬請期待!");
                builder.setMessage("查看所有盆栽，點擊可更改盆栽編號");
                builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        } );
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        //cardviewList.add(new home2_plant_img_cardview(0,"",R.drawable.gender,""));
        for(int i = 0 ;i < 20;i++)
        {
            cardviewList.add(new home2_plant_img_cardview(1,"B  01",R.drawable.home_canopy,""));
        }
        recyclerView.setAdapter(new home2.CardAdapter(home2.this, cardviewList));
        createBottomSheetDialog();




    }

    Runnable  home2_cardview_r1 = new Runnable() {
        @Override
        public void run() {
            user_vege = webservice.Select_user_vege(gmail);

            mThreadHandler.post(home2_cardview_r2);
        }
    };

    Runnable  home2_cardview_r2 = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                /**
                    Log.v("test","user_vege: "+user_vege);
                    if(user_vege.contains("找不到"))
                    {
                        cardviewList.clear();
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
                        //cardviewList.add(new home2_plant_img_cardview(0,"",R.drawable.gender,""));
                        cardviewList.add(new home2_plant_img_cardview(1,"B  01",R.drawable.home_canopy,""));
                        recyclerView.setAdapter(new home2.CardAdapter(home2.this, cardviewList));
                    }
                    else
                    {
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        recyclerView.setAdapter(new home2.CardAdapter(home2.this, cardviewList));
                    }
                 **/
                    dismissLoadingDialog();


                }
            });

        }
    };

    Runnable delete_cardview_r1= new Runnable() {
        @Override
        public void run() {
            webservice.Delete_home2_cardview(delete_cardview_id);
            mThreadHandler.post(home2_cardview_r1);
            mThreadHandler.post(delete_cardview_r2);
        }
    };

    Runnable delete_cardview_r2= new Runnable() {
        @Override
        public void run() {

            Toast.makeText(home2.this,"刪除成功", Toast.LENGTH_SHORT).show();
        }
    };


    private void createBottomSheetDialog()
    {

        if (bottomSheetDialog == null) {
            View view = LayoutInflater.from( this ).inflate( R.layout.bottom_sheet, null );

            record= view.findViewById( R.id.record );
            record.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent a = new Intent(home2.this, com.example.woo_project.record.record.class);
                    startActivity(a);
                }
            } );
            calendar= view.findViewById( R.id.calendar );
            calendar.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent a = new Intent(home2.this, com.example.woo_project.calendar.calendar.class);
                    startActivity(a);
                }
            } );
            setting= view.findViewById( R.id.setting );
            setting.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //Intent a = new Intent(home2.this, search.class);
                    //startActivity(a);
                }
            } );
            user= view.findViewById( R.id.user );
            user.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent a = new Intent(home2.this, user_setting.class);
                    startActivity(a);
                }
            } );


            bottomSheetDialog = new BottomSheetDialog( this );
            bottomSheetDialog.setContentView( view );

            //設置bottomsheet圓角
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        }

    }

    public void showDialog(View view)
    {
        bottomSheetDialog.show();
    }


    private class CardAdapter extends  RecyclerView.Adapter<CardAdapter.ViewHolder>
    {
        private Context context;
        public List<home2_plant_img_cardview> cardviewList;

        CardAdapter(Context context, List<home2_plant_img_cardview> cardviewList) {
            this.context = context;
            this.cardviewList = cardviewList;
        }

        @Override
        public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_home2_plant_img,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CardAdapter.ViewHolder viewHolder, int i) {
            final home2_plant_img_cardview cardview = cardviewList.get(i);
            if(!cardview.getName().equals(""))
            {
                //viewHolder.plant_name.setText(cardview.getId()+"  "+cardview.getName());
            }
           // viewHolder.plant_img.setImageResource(cardview.getImage());
            cardview_id = i;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(home2.this);
                    builder.setMessage("確定要刪除盆栽嗎?");
                    builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            delete_cardview_id = cardview.getIndex();
                            mThreadHandler.post(delete_cardview_r1);

                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
//                    addItem(cardviewList.size());
//                    record_name.setRecord_vege_name(cardview.getName());
//                    Intent intent = new Intent(record.this, record_Information2.class);
//                    startActivity(intent);
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

    private void showLoadingDialog(String message){
        message = "載入中...";
        mLoadingDialog.setMessage(message);
        if(mLoadingDialog==null){
            mLoadingDialog = new ProgressDialog(this);
            mLoadingDialog.setMessage(message);
        }
        mLoadingDialog.show();
    }

    private void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

}
