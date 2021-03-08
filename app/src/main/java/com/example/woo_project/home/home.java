package com.example.woo_project.home;



import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.woo_project.GlobalVariable;
import com.example.woo_project.R;

import com.example.woo_project.chart.chart_1;
import com.example.woo_project.record.record;
import com.example.woo_project.reminder.main_reminder;
import com.example.woo_project.shipping.shipping_main;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import static com.loopj.android.http.AsyncHttpClient.log;

public class home extends AppCompatActivity {

    BottomSheetDialog bottomSheetDialog;

    GlobalVariable GV; //首頁作物照片(暫時)
    int cardview_id;


    String gmail;
    ProgressDialog mLoadingDialog;


    private home2  getHome2 = new home2();
    private main_reminder getReminder=new main_reminder();
    private record  getRecord = new record();
    private chart_1 getChart_1 = new chart_1();
    private shipping_main getShipping_main = new shipping_main();
    FragmentManager fragmentManager = getSupportFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
//        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
    Button goto_home,goto_inform,goto_record,goto_chart,goto_shipping;
    private void createBottomButton()
    {

        goto_home = findViewById(R.id.goto_home);
        goto_inform = findViewById(R.id.goto_inform);
        goto_record = findViewById(R.id.goto_record);
        goto_chart = findViewById(R.id.goto_chart);
        goto_shipping = findViewById(R.id.goto_shipping);
        goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style2));




        goto_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style2));
                goto_inform.setBackground(getResources().getDrawable(R.drawable.bottom_button_inform_style));
                goto_record.setBackground(getResources().getDrawable(R.drawable.bottom_button_record_style));
                goto_chart.setBackground(getResources().getDrawable(R.drawable.bottom_button_chart_style));
                goto_shipping.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style));


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
                goto_shipping.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style));

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //Fragment切換
                getReminder = new main_reminder();
                fragmentTransaction.replace(R.id.home_fg, getReminder).commit();
            }
        });


        goto_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style));
                goto_inform.setBackground(getResources().getDrawable(R.drawable.bottom_button_inform_style));
                goto_record.setBackground(getResources().getDrawable(R.drawable.bottom_button_record_style2));
                goto_chart.setBackground(getResources().getDrawable(R.drawable.bottom_button_chart_style));
                goto_shipping.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style));


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
                goto_shipping.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style));


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //Fragment切換
                getChart_1 = new chart_1();
                fragmentTransaction.replace(R.id.home_fg, getChart_1).commit();
//                Intent intent = new Intent(home.this, com.example.woo_project.chart.chart_1.class);
//                startActivity(intent);
            }
        });


        goto_shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style));
                goto_inform.setBackground(getResources().getDrawable(R.drawable.bottom_button_inform_style));
                goto_record.setBackground(getResources().getDrawable(R.drawable.bottom_button_record_style));
                goto_chart.setBackground(getResources().getDrawable(R.drawable.bottom_button_chart_style));
                goto_shipping.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style2));

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //Fragment切換
                getShipping_main = new shipping_main();
                fragmentTransaction.replace(R.id.home_fg, getShipping_main).commit();
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
