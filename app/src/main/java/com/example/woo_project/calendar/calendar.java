package com.example.woo_project.calendar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.woo_project.AutoSplitTextView;
import com.example.woo_project.DownloadImageTask;
import com.example.woo_project.GlobalVariable;

import com.example.woo_project.webservice;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.example.woo_project.R;


public class calendar extends AppCompatActivity {
    List<choose_calendar_cardview> cardviewList;
    CalendarView cv1;
    TextView tv1,tv2,act_tv;
    String[] listItems,everyvege,sl,everyday;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    Button edit,update,cancel;
    Spinner spi,spiday;
    Switch swi;
    View sugcal_view;
    Intent intent;
    private static  final  int REQUEST_CODE=1;
    GlobalVariable action_item_value,action_item_value2,choose_calendar_info;
    String date,decide_edit="edit",cal_data,Allvege="",Select_vege_day="",pictureurl,Select_vege_name,gmail,choose_calendar_string,firstday="2019/12/12";

    String plant_id;
    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;
    RecyclerView recyclerView;
    ImageView calendar_picture;
    Bitmap myBitmap,nopicture;
    URL url;
    long temp2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calendar);

        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

        recyclerView= findViewById(R.id.calendar_info_recyclerview);
        calendar_picture = findViewById(R.id.calendar_picture);
        edit =(Button)findViewById(R.id.edit);
        update = (Button)findViewById(R.id.update);
        cancel = (Button)findViewById(R.id.cancel);
        cv1 = (CalendarView)findViewById(R.id.CV01);
        tv1 = (TextView)findViewById(R.id.TV01);
        tv2 = (TextView)findViewById(R.id.TV02);
        act_tv = (TextView)findViewById(R.id.act_tv);
        action_item_value= (GlobalVariable)getApplicationContext();
        action_item_value2= (GlobalVariable)getApplicationContext();
        spi = (Spinner)findViewById(R.id.spinner);
//        spiday = (Spinner)findViewById(R.id.spinner2);
        swi = (Switch) findViewById(R.id.switch1);

        final Calendar calendar= Calendar.getInstance();
        date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        tv1.setText(date);
        mThreadHandler.post(r11);
        swi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recyclerView.setVisibility(View.VISIBLE);
                    mThreadHandler.post(r9);
                } else {
                    cardviewList.clear();
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
        choose_calendar_info = (GlobalVariable) getApplicationContext() ;

        spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Select_vege_name=spi.getSelectedItem().toString();
                plant_id = String.valueOf(spi.getSelectedItemPosition());
                Log.v("test","plant_id spinner: "+plant_id);
                action_item_value.setPlant_id(plant_id);
                action_item_value.setSelect_vege_name(Select_vege_name);
                mThreadHandler.post(r3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        spiday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectdate=spiday.getSelectedItem().toString();
//                Log.v("test","plant_id spinner: "+plant_id);
//                mThreadHandler.post(r3);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        listItems = getResources().getStringArray(R.array.action_item);
        checkedItems = new boolean[listItems.length];

        mThreadHandler.post(r7);

        cv1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int y, int m, int dm) {
                date = y+"/"+(m+1)+"/"+dm;
                tv1.setText(date);
                Log.v("test","WTTTTTTTH: "+firstday+"><"+date);
                mThreadHandler.post(r3);
                try{
                    SimpleDateFormat sim = new SimpleDateFormat("yyyy/MM/dd");//定義日期時間格式，一定要進行ParseException的例外處理
                    Date start = sim.parse(firstday);
                    long startTime = start.getTime();//取得時間的unix時間
                    Date end = sim.parse(date);//取得目前即時的時間
                    long endTime = end.getTime();//取得時間的unix時間
                    temp2 = (endTime-startTime)/(1000*60*60*24)+1;
                }catch(Exception e){
                    Log.v("test","天數錯誤: "+e);
                }
                if(swi.isChecked())
                {
                    cardviewList.clear();
                    mThreadHandler.post(r9);
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(calendar.this,calendar_memo.class);
                //給message起一個名字，並傳給另一個activity
                intent2.putExtra("EXTRA_DATE",date);
                intent2.putExtra("decide_edit",decide_edit);
                intent2.putExtra("plant_id",plant_id);
                if(cal_data.equals("還未新增資料"))
                {
                    startActivity(intent2);
                    calendar.this.finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(calendar.this);
                    builder.setTitle(tv1.getText()+"的"+Select_vege_name+"已有紀錄囉!");
                    builder.setMessage("請按修改按鈕");
                    builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calendar.this, calendar_memo2.class);
                intent.putExtra("EXTRA_DATE",date);
                if(cal_data.equals("還未新增資料")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(calendar.this);
                    builder.setTitle(tv1.getText()+"的"+Select_vege_name+"沒紀錄喔!");
                    builder.setMessage("請按新增按鈕");
                    builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
                else
                {
                    startActivity(intent);
                    calendar.this.finish();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(cal_data.equals("還未新增資料"))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(calendar.this);
                    builder.setTitle("注意!");
                    builder.setMessage("確定要刪除" + date + Select_vege_name + "的內容嗎?");
                    builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mThreadHandler.post(r1);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(calendar.this);
                    builder.setTitle(tv1.getText()+"的"+Select_vege_name+"沒資料喔!");
                    builder.setMessage("");
                    builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();}

            }
        });


        intent = getIntent();
        //把傳送進來的String型別的Message的值賦給新的變數message
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        //所勾選的活動
        String s = intent.getStringExtra("EXTRA_Activity");
        //把佈局檔案中的文字框和textview連結起來
        //在textview中顯示出來message
        tv2.setText(message);
        if((!tv2.getText().equals("")) || tv2.getText().equals("")) {
            act_tv.setText(s);
        }

        cardviewList = new ArrayList<>();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.vege_info);
    }

    ///刪除某個日期的某個作物
    private Runnable r1=new Runnable () {

        public void run() {
            webservice.Delete_cal_vege(date,Select_vege_name,gmail);
            //請經紀人指派工作名稱 r，給工人做
            mUI_Handler.post(r2);

        }

    };

    private Runnable r2=new Runnable () {

        public void run() {
            tv2.setText("");
            act_tv.setText("");
            calendar_picture.setImageBitmap(nopicture);
        }

    };

    ///點擊某個日期查看某個作物
    private Runnable r3=new Runnable () {

        public void run() {

            gmail=action_item_value.getUser_gmail();
            Select_vege_name=action_item_value.getSelect_vege_name();
            cal_data = webservice.select_cal(gmail,Select_vege_name,date);
            Log.v("test","cal_data: "+cal_data);
            //請經紀人指派工作名稱 r，給工人做
            mUI_Handler.post(r4);

        }

    };


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
                if(sl[2].equals("nopicture"))
                {
                    calendar_picture.setImageResource(R.drawable.notapic);
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
                calendar_picture.setImageDrawable(null);
               // Toast.makeText(calendar.this, "未新增資料", Toast.LENGTH_SHORT).show();

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

    ///顯示使用者種過的作物
    private Runnable r7=new Runnable () {

        public void run() {

            String gmail=action_item_value.getUser_gmail();
            Allvege=webservice.Select_user_vege(gmail);
            Log.v("test",":::::::::: "+Allvege);
            mUI_Handler.post(r8);
        }

    };

    private Runnable r8=new Runnable () {

        public void run() {

            String[]  split = Allvege.split("分開");
            everyvege = split[0].split("%");
            if (Allvege.equals("找不到")) {
                String[] novege = {"你還未開始種植喔!"};
                final String[] lunch = novege;
                ArrayAdapter<String> lunchList = new ArrayAdapter<>(calendar.this,
                        R.layout.login2_select_dropdown_item,
                        lunch);

                spi.setAdapter(lunchList);
//                mThreadHandler.post(r13);
            } else {
                final String[] lunch = everyvege;
                for(int i=0;i<lunch.length;i++)
                {
                    lunch[i] = i + lunch[i];
                }
                ArrayAdapter<String> lunchList = new ArrayAdapter<>(calendar.this,
                        R.layout.login2_select_dropdown_item,
                        lunch);

                spi.setAdapter(lunchList);
//                mThreadHandler.post(r13);
            }

        }
    };
//    private Runnable r13=new Runnable () {
//
//        public void run() {
//            gmail=action_item_value.getUser_gmail();
//            Select_vege_name=action_item_value.getSelect_vege_name();
//            plant_id=action_item_value.getPlant_id();
//            Log.v("test123456",":::::::::: "+gmail+"//"+plant_id+"///"+Select_vege_name);
//            Select_vege_day=webservice.Select_all_vege_day(gmail,plant_id,Select_vege_name);
//            Log.v("test123456",":::::::::: "+Select_vege_day);
//            mUI_Handler.post(r14);
//        }
//
//    };
//
//    private Runnable r14=new Runnable () {
//
//        public void run() {
//
//            everyday = Select_vege_day.split("%");
//            if (Select_vege_day.equals("找不到")) {
//                String[] noday = {"還沒有任何紀錄喔!"};
//
//                final String[] lunch2 = noday;
//                ArrayAdapter<String> lunchList2 = new ArrayAdapter<>(calendar.this,
//                        R.layout.login2_select_dropdown_item,
//                        lunch2);
//
//                spiday.setAdapter(lunchList2);
//            } else {
//                final String[] lunch2 = everyday;
//                ArrayAdapter<String> lunchList2 = new ArrayAdapter<>(calendar.this,
//                        R.layout.login2_select_dropdown_item,
//                        lunch2);
//
//                spiday.setAdapter(lunchList2);
//            }
//
//        }
//    };
    Runnable r9 = new Runnable() {
        @Override
        public void run() {
           // String[] split=choose_calendar_info.getChoose_calendar_info().split("%");
            String[] split = new String[3] ;
            split[0] = "a123@gmail.com";
            split[1] = "A01";
            split[2] = "小白菜";
            Log.v("test","split for ALL: " + choose_calendar_info.getChoose_calendar_info());
            Log.v("test","split for everyone: " + split[0]+"           "+split[1]+"           "+split[2]);
            choose_calendar_string = webservice.choose_calendar_info_cardview(split[0],split[1],split[2]);
            Log.v("test","choose_calendar_info_cardview(split[0],split[1],split[2]) :  " + split[0]+"           "+split[1]+"           "+split[2]);
            mUI_Handler.post(r10);

        }
    };

    Runnable r10 = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    String[] all,split,start_date;
                    if (!choose_calendar_string.equals(""))
                    {
                        all = choose_calendar_string.split("切");
                        start_date = all[0].split("%");
                        Log.v("test","choose_calendar_string:"+choose_calendar_string);
                        for(int num = 0; num < all.length ;num ++)
                        {
                            split = all[num].split("%");

                            Log.v("test","split:"+split[0]+"         "+split[1]+"         "+split[2]);
                            //算天數
                            long temp = 0;
                            try{
                                SimpleDateFormat sim = new SimpleDateFormat("yyyy/MM/dd");//定義日期時間格式，一定要進行ParseException的例外處理
                                Date start = sim.parse(start_date[0]);
                                long startTime = start.getTime();//取得時間的unix時間
                                Date end = sim.parse(split[0]);//取得目前即時的時間
                                long endTime = end.getTime();//取得時間的unix時間
                                temp = (endTime-startTime)/(1000*60*60*24)+1;
                                Log.v("test","天數: "+temp);
                            }catch(Exception e){
                                Log.v("test","天數錯誤: "+e);
                            }

                            //cardviewList.add(new choose_calendar_cardview(0,"2","2019-12-06","快長",bitmap));
                            if(temp==temp2)
                            { cardviewList.add(new choose_calendar_cardview(num,String.valueOf(temp),split[0],split[1],split[2]));}
                            else{
                              //  Toast.makeText(calendar.this, "今天沒紀錄", Toast.LENGTH_SHORT).show();
                            }
                        }


                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                        recyclerView.setAdapter(new calendar.CardAdapter(calendar.this, cardviewList));

                    }
                }
            });
        }
    };
    private Runnable r11=new Runnable () {

        public void run() {

//            firstday=webservice.select_cal_firstday("a123@gmail.com","A01","小白菜");
        }

    };


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //移除工人上的工作
        if (mThreadHandler != null) {
            mThreadHandler.removeCallbacks(r1);
            mThreadHandler.removeCallbacks(r3);
            mThreadHandler.removeCallbacks(r5);
            mThreadHandler.removeCallbacks(r7);
            mThreadHandler.removeCallbacks(r9);
            mThreadHandler.removeCallbacks(r11);
//            mThreadHandler.removeCallbacks(r13);
        }
        //解聘工人 (關閉Thread)
        if (mThread != null) {
            mThread.quit();
        }
    }
    private class CardAdapter extends  RecyclerView.Adapter<CardAdapter.ViewHolder>
    {
        private Context context;
        List<choose_calendar_cardview> cardviewList;

        CardAdapter(Context context, List<choose_calendar_cardview> cardviewList) {
            this.context = context;
            this.cardviewList = cardviewList;
        }

        @Override
        public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
          View view = LayoutInflater.from(context).inflate(R.layout.activity_choose_calendar_cardview,viewGroup,false);
          return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CardAdapter.ViewHolder viewHolder, int i) {
            final choose_calendar_cardview cardview = cardviewList.get(i);
            viewHolder.day.setText(String.valueOf(cardview.getDay()));
            if(!cardview.getImage().equals("無圖片"))
            {
                //viewHolder.downloadImageTask.execute(cardview.getImage());
                Glide.with(calendar.this).load(cardview.getImage()).into(viewHolder.img);
            }
            else
            {
                Glide.with(calendar.this).load(R.drawable.gender).into(viewHolder.img);
            }

            viewHolder.time.setText(String.valueOf((cardview.getTime())));
            viewHolder.message.setText(String.valueOf((cardview.getMessage())));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
            DownloadImageTask downloadImageTask;
            ImageView img;
            TextView day,time;
            AutoSplitTextView message;

            ViewHolder(View itemView){
                super(itemView);
                //downloadImageTask = new DownloadImageTask((ImageView)itemView.findViewById(R.id.choose_calendar_img));
                img = itemView.findViewById(R.id.choose_calendar_img);
                day =  itemView.findViewById(R.id.choose_calendar_day);
                time =  itemView.findViewById(R.id.choose_calendar_time);
                message =  itemView.findViewById(R.id.choose_calendar_message);
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
}
