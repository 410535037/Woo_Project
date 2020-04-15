package com.example.woo_project.home;



import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import java.util.Objects;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

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

    String gmail;
    Spinner canopy_area;
    ProgressDialog mLoadingDialog;



    private home2_canopy_A  home2_canopy_A = new home2_canopy_A();
    private home2_canopy_B  home2_canopy_B = new home2_canopy_B();
    private home2_canopy_C  home2_canopy_C = new home2_canopy_C();
    private home2_canopy_North  home2_canopy_North = new home2_canopy_North();
    private home2_canopy_Middle  home2_canopy_Millde = new home2_canopy_Middle();

    List<String> canopyarea_list = new ArrayList<>();

    ViewPager home2_viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_home2 );




        canopy_area = findViewById(R.id.canopy_area);




        GV = (GlobalVariable) getApplicationContext();
        gmail = GV.getUser_gmail();


        mLoadingDialog = new ProgressDialog(home2.this);
//        showLoadingDialog("載入中...");

        edit_pot= findViewById(R.id.edit_pot);
        edit_pot.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                add_canopy_or_canopyArea();

            }
        } );

        createBottomSheetDialog();

//        home2_viewpager =  findViewById(R.id.home2_viewpager);
//        home2_viewpager.addOnPageChangeListener(this);
//        setupViewPager(home2_viewpager);

        canopy_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.findFragmentById(R.id.canopy_fg)==null)
                {
                    selected_canopyarea();
                }
                else
                {
                    remove_selected_canopyarea();
                    selected_canopyarea();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

        mThreadHandler.post(getCanopyarea_list);
    }



    Runnable getCanopyarea_list= new Runnable() {
        @Override
        public void run() {
            canopyarea_list = home2_webservice.canopyarea_list();
            mThreadHandler.post(setCanopyarea_list);
        }
    };

    Runnable setCanopyarea_list= new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //給予對應item的資料
                    ArrayAdapter<String> canopyarea_adapter = new ArrayAdapter<String>(home2.this,
                            R.layout.home2_canopyarea_dropdown_item,                            //選項資料內容
                            canopyarea_list);   //自訂getView()介面格式(Spinner介面未展開時的View)
                    canopy_area.setAdapter(canopyarea_adapter);
                    //Toast.makeText(home2.this,"刪除成功", Toast.LENGTH_SHORT).show();
                }

            });
        }
    };

    //棚架fragment刷新
    private void selected_canopyarea(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Log.v("test","canopy_area.getSelectedItem():  "+canopy_area.getSelectedItem()+"         "+canopy_area.getSelectedItemId());
        home2_canopy_A = new home2_canopy_A();
        home2_canopy_A.setViewpager_id((int) canopy_area.getSelectedItemId(),canopy_area.getSelectedItem().toString().substring(0,1));
        fragmentTransaction.add(R.id.canopy_fg, home2_canopy_A).commit();
    }
    //移除棚架fragment
    private void remove_selected_canopyarea(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(home2_canopy_A).commit();
    }


    //建立功能Button
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

    //viewpager函式
    private void setupViewPager(ViewPager viewPager) {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 0:
                        home2_canopy_A.setViewpager_id(0,"A");
                        return home2_canopy_A;
                    case 1:
                        home2_canopy_B.setViewpager_id(1,"B");
                        return home2_canopy_B;
                    case 2:
                        home2_canopy_C.setViewpager_id(2,"C");
                        return home2_canopy_C;
                    case 3:
                        home2_canopy_North.setViewpager_id(3,"北");
                        return home2_canopy_North;
                    case 4:
                        home2_canopy_Millde.setViewpager_id(4,"中");
                        return home2_canopy_Millde;
                }



                return null;


            }
            @Override
            public int getCount() {
                return 5;
            }
            @Override
            public int getItemPosition(@NonNull Object object) {
                return POSITION_NONE;
            }

        });


    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageSelected(int position) {

        switch (position) {
//            case 0:
//                canopy_area.("A區棚架");
//                break;
//            case 1:
//                canopy_area.setText("B區棚架");
//                break;
//            case 2:
//                canopy_area.setText("C區棚架");
//                break;
//            case 3:
//                canopy_area.setText("北區棚架");
//                break;
//            case 4:
//                canopy_area.setText("中區棚架");
//                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {}


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
