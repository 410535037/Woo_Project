package com.example.woo_project.user_setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.woo_project.GlobalVariable;
import com.example.woo_project.R;
import com.example.woo_project.webservice;

public class personal_info extends AppCompatActivity {

    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;
    GlobalVariable gl;
    ImageButton user_info_edit,back_user;
    String user_email,user_data;
    TextView name_tv,phone_tv,email_tv,addr_tv,age_tv,gender_tv,expri_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_personal_info);

        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");

        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();

        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

        gl = (GlobalVariable)getApplicationContext();
        user_email=gl.getUser_gmail();
        Log.v("test","user_email: "+user_email);

        user_info_edit=findViewById(R.id.user_info_edit);
        back_user=findViewById(R.id.back_user);
        name_tv=findViewById(R.id.name_tv);
        phone_tv=findViewById(R.id.phone_tv);
        email_tv=findViewById(R.id.email_tv);
        addr_tv=findViewById(R.id.addr_tv);
        age_tv=findViewById(R.id.age_tv);
        gender_tv=findViewById(R.id.gender_tv);
        expri_tv=findViewById(R.id.expri_tv);

        back_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(personal_info.this, user_setting.class);
                startActivity(x);
                personal_info.this.finish();
            }
        });

        user_info_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(personal_info.this, personal_info_edit.class);
                startActivity(x);
                personal_info.this.finish();
            }
        });
        mThreadHandler.post(r1);


    }
    //工作名稱 r1 的工作內容

    private Runnable r1=new Runnable () {

        public void run() {
            user_data= webservice.Select_user_info(user_email);
            Log.v("test","user: "+user_data);
            //請經紀人指派工作名稱 r，給工人做
            mUI_Handler.post(r2);

        }

    };
    //工作名稱 r2 的工作內容
    private Runnable r2=new Runnable () {

        public void run() {
            String can = "未填寫";
            if(!user_data.equals(""))
            {
                String[] split_user_data = user_data.split("%");

                name_tv.setText(split_user_data[0]);
                phone_tv.setText(split_user_data[1]);
                email_tv.setText(split_user_data[2]);
                addr_tv.setText(split_user_data[3]);
                age_tv.setText(split_user_data[4]);
                gender_tv.setText(split_user_data[5]);
                expri_tv.setText(split_user_data[6]);
            }
            else
            {
                name_tv.setText(can);
                phone_tv.setText(can);
                email_tv.setText(can);
                addr_tv.setText(can);
                age_tv.setText(can);
                gender_tv.setText(can);
                expri_tv.setText(can);
            }
        }

    };

    private Runnable r3=new Runnable () {

        public void run() {

        }

    };

    private Runnable r4=new Runnable () {

        public void run() {

        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //移除工人上的工作
        if (mThreadHandler != null) {
            mThreadHandler.removeCallbacks(r1);
            mThreadHandler.removeCallbacks(r3);
        }
        //解聘工人 (關閉Thread)
        if (mThread != null) {
            mThread.quit();
        }
    }
}
