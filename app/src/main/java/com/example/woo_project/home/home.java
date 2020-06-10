package com.example.woo_project.home;



import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.woo_project.GlobalVariable;
import com.example.woo_project.QRCode.Constant;
import com.example.woo_project.R;

import com.example.woo_project.chart.chart_1;
import com.example.woo_project.record.record;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {

    BottomSheetDialog bottomSheetDialog;

    GlobalVariable GV; //首頁作物照片(暫時)
    int cardview_id;


    String gmail;
    ProgressDialog mLoadingDialog;


    private home2  getHome2 = new home2();
    private record  getRecord = new record();
    private chart_1 getChart_1 = new chart_1();
    FragmentManager fragmentManager = getSupportFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_home );



        GV = (GlobalVariable) getApplicationContext();
        gmail = GV.getUser_gmail();


        mLoadingDialog = new ProgressDialog(home.this);
//        showLoadingDialog("載入中...");


        createBottomButton();

        getHome2 = new home2();
        switchContent2(getHome2);

    }

    //建立功能Button
    Button goto_home,goto_inform,goto_record,goto_chart,goto_transportation;
    private void createBottomButton()
    {
        goto_home = findViewById(R.id.goto_home);
        goto_inform = findViewById(R.id.goto_inform);
        goto_record = findViewById(R.id.goto_record);
        goto_chart = findViewById(R.id.goto_chart);
        goto_transportation = findViewById(R.id.goto_transportation);
        goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style2));




        goto_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style2));
                goto_inform.setBackground(getResources().getDrawable(R.drawable.bottom_button_inform_style));
                goto_record.setBackground(getResources().getDrawable(R.drawable.bottom_button_record_style));
                goto_chart.setBackground(getResources().getDrawable(R.drawable.bottom_button_chart_style));
                goto_transportation.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style));


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //Fragment切換
                getHome2 = new home2();
                fragmentTransaction.replace(R.id.home_fg, getHome2).commit();


            }
        });


        goto_inform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style));
                goto_inform.setBackground(getResources().getDrawable(R.drawable.bottom_button_inform_style2));
                goto_record.setBackground(getResources().getDrawable(R.drawable.bottom_button_record_style));
                goto_chart.setBackground(getResources().getDrawable(R.drawable.bottom_button_chart_style));
                goto_transportation.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style));

//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                //Fragment切換
//                getHome2 = new home2();
//                fragmentTransaction.replace(R.id.home_fg, getHome2).commit();
            }
        });


        goto_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style));
                goto_inform.setBackground(getResources().getDrawable(R.drawable.bottom_button_inform_style));
                goto_record.setBackground(getResources().getDrawable(R.drawable.bottom_button_record_style2));
                goto_chart.setBackground(getResources().getDrawable(R.drawable.bottom_button_chart_style));
                goto_transportation.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style));


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //Fragment切換
                getRecord = new record();
                fragmentTransaction.replace(R.id.home_fg, getRecord).commit();
            }
        });


        goto_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style));
                goto_inform.setBackground(getResources().getDrawable(R.drawable.bottom_button_inform_style));
                goto_record.setBackground(getResources().getDrawable(R.drawable.bottom_button_record_style));
                goto_chart.setBackground(getResources().getDrawable(R.drawable.bottom_button_chart_style2));
                goto_transportation.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style));


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //Fragment切換
                getChart_1 = new chart_1();
                fragmentTransaction.replace(R.id.home_fg, getChart_1).commit();
//                Intent intent = new Intent(home.this, com.example.woo_project.chart.chart_1.class);
//                startActivity(intent);
            }
        });


        goto_transportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style));
                goto_inform.setBackground(getResources().getDrawable(R.drawable.bottom_button_inform_style));
                goto_record.setBackground(getResources().getDrawable(R.drawable.bottom_button_record_style));
                goto_chart.setBackground(getResources().getDrawable(R.drawable.bottom_button_chart_style));
                goto_transportation.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style2));

//                Intent intent = new Intent(home2.this, com.example.woo_project.record.record.class);
//                startActivity(intent);
            }
        });

    }



    /**
     * 修改显示的内容 但会重新加载 *
     */
    public void switchContent2(Fragment to)
    {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home_fg,to);
        transaction.commit();
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
