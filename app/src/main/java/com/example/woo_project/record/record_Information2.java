package com.example.woo_project.record;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo_project.GlobalVariable;
import com.example.woo_project.R;
import com.example.woo_project.webservice;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class record_Information2 extends AppCompatActivity{
    CalendarView cv1;
    TextView tv1,tv2,act_tv;
    String[] listItems,everyvege,sl;
    boolean[] checkedItems;
    Spinner spi;

    private static  final  int REQUEST_CODE=1;
    GlobalVariable action_item_value,action_item_value2;
    String date,decide_edit="edit",cal_data,Allvege="",setdate,pictureurl,gmail;
    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    ImageView calendar_picture;
    Bitmap myBitmap,nopicture;
    URL url;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.record_information2);
            //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
            mThread = new HandlerThread("");
            //讓Worker待命，等待其工作 (開啟Thread)
            mThread.start();
            //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
            mThreadHandler=new Handler(mThread.getLooper());

            calendar_picture = findViewById(R.id.calendar_picture);


            cv1 = (CalendarView)findViewById(R.id.CV01);
            tv1 = (TextView)findViewById(R.id.TV01);
            tv2 = (TextView)findViewById(R.id.TV02);
            act_tv = (TextView)findViewById(R.id.act_tv);
            action_item_value= (GlobalVariable)getApplicationContext();
            action_item_value2= (GlobalVariable)getApplicationContext();
            Calendar calendar= Calendar.getInstance();
            setdate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
            tv1.setText(setdate);


            listItems = getResources().getStringArray(R.array.action_item);
            checkedItems = new boolean[listItems.length];



            cv1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int y, int m, int dm) {
                    date = y+"/"+(m+1)+"/"+dm;
                    tv1.setText(date);
                    mThreadHandler.post(r3);

                }
            });

        }


    private Runnable r3=new Runnable () {

        public void run() {

            gmail=action_item_value.getUser_gmail();
            cal_data = webservice.select_cal(gmail,action_item_value.getRecord_vege_name(),date);
            Log.v("test","(gmail,action_item_value.getRecord_vege_name(),date): "+ gmail + "    "+action_item_value.getRecord_vege_name()+"    "+date);
            Log.v("test","cal_data: "+cal_data);
            //請經紀人指派工作名稱 r，給工人做
            mUI_Handler.post(r4);

        }

    };
    //工作名稱 r1 的工作內容

    private Runnable r4=new Runnable () {

        public void run() {

            if(!cal_data.equals("還未新增資料"))
            {
                sl = cal_data.split("%");
                if(sl[0].equals(""))
                {
                    act_tv.setText("");
                }
                else
                {
                    act_tv.setText(sl[0]);
                }
                if(sl[1].equals(""))
                {
                    tv2.setText("");
                }
                else
                {
                    tv2.setText(sl[1]);
                }
                if(sl[2].equals(""))
                {
                    calendar_picture.setImageBitmap(nopicture);
                }
                else
                {
                    pictureurl=sl[2];
                    try
                    {
                        url = new URL(pictureurl);
                        if(url.toString().contains("http"))
                        {
                            mThreadHandler.post(r5);
                        }

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }

            }
            else
            {
                tv2.setText("");
                act_tv.setText("");
                calendar_picture.setImageBitmap(nopicture);
                Toast.makeText(record_Information2.this, "未新增資料", Toast.LENGTH_SHORT).show();

            }

        }

    };




    private Runnable r5=new Runnable () {

        public void run() {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //請經紀人指派工作名稱 r，給工人做
            mUI_Handler.post(r6);

        }

    };

    private Runnable r6=new Runnable () {

        public void run() {

            calendar_picture.setImageBitmap(myBitmap);
        }

    };


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //移除工人上的工作
        if (mThreadHandler != null) {
            mThreadHandler.removeCallbacks(r3);
            mThreadHandler.removeCallbacks(r5);
        }
        //解聘工人 (關閉Thread)
        if (mThread != null) {
            mThread.quit();
        }
    }
}