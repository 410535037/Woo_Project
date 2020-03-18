package com.example.woo_project.home;

import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.woo_project.GlobalVariable;
import com.example.woo_project.R;

import com.example.woo_project.user_setting.user_setting;
import com.example.woo_project.webservice;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class home2 extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    BottomSheetDialog bottomSheetDialog;
    ImageButton record,calendar,setting,user,edit_pot;

    String delete_cardview_id;
    GlobalVariable GV; //首頁作物照片(暫時)
    int cardview_id;

    List<home2_plant_img_cardview> canopy_cardviewList;
    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private android.os.Handler mUI_Handler = new android.os.Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    String user_vege,gmail;
    TextView canopy_area;
    ProgressDialog mLoadingDialog;

    private home2_canopy_A  home2_canopy_A = new home2_canopy_A();
    private home2_canopy_B  home2_canopy_B = new home2_canopy_B();
    private home2_canopy_C  home2_canopy_C = new home2_canopy_C();
    private home2_canopy_North  home2_canopy_North = new home2_canopy_North();
    private home2_canopy_Middle  home2_canopy_Millde = new home2_canopy_Middle();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_home2 );

        canopy_area = findViewById(R.id.canopy_area);


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

        edit_pot= findViewById(R.id.edit_pot);
        edit_pot.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                add_canopy_or_canopyArea();

            }
        } );
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        for(int i = 0 ;i < 20;i++)
//        {
//            canopy_cardviewList.add(new home2_plant_img_cardview(1,"B  "+i,R.drawable.home_canopy,""));
//        }
//        recyclerView.setAdapter(new home2.canopy_CardAdapter(home2.this, canopy_cardviewList));
        createBottomSheetDialog();

        ViewPager home2_viewpager =  findViewById(R.id.home2_viewpager);
        home2_viewpager.addOnPageChangeListener(this);
        setupViewPager(home2_viewpager);


    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0: ;
                        return home2_canopy_A;
                    case 1:
                        return home2_canopy_B;
                    case 2:
                        return home2_canopy_C;
                    case 3:
                        return home2_canopy_North;
                    case 4:
                        return home2_canopy_Millde;
                }
                return null;
            }
            @Override
            public int getCount() {
                return 5;
            }
        });

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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                canopy_area.setText("A區棚架");
                break;
            case 1:
                canopy_area.setText("B區棚架");
                break;
            case 2:
                canopy_area.setText("C區棚架");
                break;
            case 3:
                canopy_area.setText("北區棚架");
                break;
            case 4:
                canopy_area.setText("中區棚架");
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void add_canopy_or_canopyArea(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(home2.this);
        View mView = getLayoutInflater().inflate(R.layout.home_add_canopy, null);
        //Dialog的標題
        mBuilder.setTitle("");

        Button add_canopy,add_area;
        add_canopy = mView.findViewById(R.id.add_canopy);
        add_area = mView.findViewById(R.id.add_area);

        mBuilder.setView(mView);
        final AlertDialog canopy_dialog = mBuilder.create();
        canopy_dialog.show();

        add_canopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canopy_dialog.dismiss();
            }
        });

        add_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canopy_dialog.dismiss();
            }
        });

        // 調整Dialog從哪開始
        Window dialogWindow = canopy_dialog.getWindow();
        assert dialogWindow != null;
        dialogWindow.setGravity(Gravity.CENTER );

        // 去除四角黑色背景
        dialogWindow.setBackgroundDrawable(new BitmapDrawable());

        /* 將Dialog用螢幕大小百分比方式設置 */
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 取得螢幕寬和高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 取得對話框目前數值
    //    p.height = (int) (d.getHeight() * 0.8); // 高度設為螢幕的0.8
        p.width = (int) (d.getWidth() * 0.75);  // 寬度設為螢幕的0.75
        dialogWindow.setAttributes(p);

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
