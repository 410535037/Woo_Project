package com.example.woo_project.shipping;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.example.woo_project.R;
import com.example.woo_project.home.canopy_dialog;
import com.example.woo_project.home.home2_dialog_cardview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class shipping_add_data extends AppCompatActivity
{
    Context context;

    Spinner vege_name_spinner ;
    Spinner vendor_name_spinner ;

    //回傳出貨資料進DB相關
    String vege_name_tx="",vendor_name_tx="";
    String set_date_str = "";
    String sale_num_str;
    String total_earnings_str;
    boolean ship_status;
    String add_ship_data_result ="";



    AppCompatButton add_vendor,shipping_add_vendor_name_bt,shipping_check;
    private RadioButton[] radioButton=new RadioButton[2];
    private LinearLayout[] linearLayout=new LinearLayout[2];
    private RadioGroup radioGroup;


    /*** 作物相關webservice變數宣告 ***/
    private List<String> corp_list = new ArrayList<>();

    /*** 廠商相關webservice變數宣告 ***/
    private List<String> vendor_list = new ArrayList<>();


    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private android.os.Handler mUI_Handler = new android.os.Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;
    private String getNewVendor="";
    private EditText new_vendor_name;//新增廠商dialog中的edittext
    private EditText set_ship_date,sale_num,total_earnings;//出貨資料:(預計)出貨日期,數量_公斤


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.shipping_add_data);
        add_vendor=(AppCompatButton)findViewById(R.id.add_vendor);
        add_vendor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                set_dialog();
            }
        });

        linearLayout[0]= (LinearLayout) findViewById(R.id.shipping_layout_left);
        linearLayout[1]= (LinearLayout) findViewById(R.id.shipping_layout_right);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioButton[0]=(RadioButton)findViewById(R.id.rbLeft);
        radioButton[1]=(RadioButton)findViewById(R.id.rbRight);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
            {
                Log.v("msg",checkedId+"checkedId");
                switch(checkedId)
                {
                    case R.id.rbLeft:
                        Log.e("msg",R.id.rbLeft+"R.id.radioButton1");
                        changeradio(0);
                        break;
                    case R.id.rbRight:
                        Log.e("msg",R.id.rbRight+"R.id.radioButton2");
                        changeradio(1);
                        break;

                }
            }});

        //選擇日期相關
        set_ship_date = (EditText)findViewById(R.id.set_ship_date);
        ImageButton select_ship_date =(ImageButton)findViewById(R.id.select_ship_date);
        select_ship_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_ship_date(shipping_add_data.this);
            }
        });

        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());
        mThreadHandler.post(getCrop_list);
        mThreadHandler.post(getVendor_list);
        vege_name_spinner=(Spinner)findViewById(R.id.vege_name_spinner);
        vege_name_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
                vege_name_tx = vege_name_spinner.getSelectedItem().toString();
                Log.v("tst","vege_name_tx: " + vege_name_tx);
            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        });
        vendor_name_spinner=(Spinner)findViewById(R.id.vendor_name_spinner);
        vendor_name_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
                vendor_name_tx = vendor_name_spinner.getSelectedItem().toString();
                Log.v("tst","vendor_name_tx: " + vendor_name_tx);
            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        });


        //按"確定" 把出貨資料加入
        shipping_check = findViewById(R.id.shipping_check);
        sale_num = (EditText)findViewById(R.id.sale_num);
        total_earnings = (EditText)findViewById(R.id.total_earnings);

        shipping_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!vege_name_tx.equals("") && !vendor_name_tx.equals("") && radioGroup.getCheckedRadioButtonId() == R.id.rbLeft &&
                        Double.parseDouble(sale_num.getText().toString())> 0.0 && !set_ship_date.getText().toString().equals(""))
                {
                    total_earnings_str = total_earnings.getText().toString();
                    ship_status = false;
                    sale_num_str = sale_num.getText().toString();
                    mThreadHandler.post(setShipping_data);
                    shipping_add_data.this.finish();
                    Log.v("test","sale_num_double  1 = "+ sale_num_str );
                }
                else if (!vege_name_tx.equals("") && !vendor_name_tx.equals("") && radioGroup.getCheckedRadioButtonId() == R.id.rbRight &&
                        Double.parseDouble(sale_num.getText().toString())> 0 && !set_ship_date.getText().toString().equals("")
                        )
                {
                    total_earnings_str = total_earnings.getText().toString();
                    ship_status = true;
                    sale_num_str = sale_num.getText().toString();
                    Log.v("test","sale_num_double  2 = "+ sale_num_str );
                    mThreadHandler.post(setShipping_data);
                    shipping_add_data.this.finish();
                }
                else
                {
                    Toast.makeText(context,"格式錯誤!",Toast.LENGTH_SHORT).show();
                }

            }
        });
}

    //抓作物list
    private Runnable getCrop_list= new Runnable() {
        @Override
        public void run() {
            corp_list = shipping_webservice.crop_list();
            Log.v("test","getCrop "+corp_list);
            mThreadHandler.post(setCrop_list);
        }
    };

    //把作物list放進spinner
    private Runnable setCrop_list = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //給予對應item的資料
                    ArrayAdapter <String> crop_adapter = new ArrayAdapter<String>(Objects.requireNonNull(shipping_add_data.this),
                            R.layout.shipping_select_dropdown_item,                            //選項資料內容
                            corp_list);   //自訂getView()介面格式(Spinner介面未展開時的View)
                    vege_name_spinner.setAdapter(crop_adapter);
                }

            });
        }
    };


    //抓廠商list
    private Runnable getVendor_list= new Runnable() {
        @Override
        public void run() {
            vendor_list = shipping_webservice.vendor_list();
            Log.v("test","getVendor "+vendor_list);
            mThreadHandler.post(setVendor_list);
        }
    };

    //把廠商list放進spinner
    private Runnable setVendor_list = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //給予對應item的資料
                    ArrayAdapter <String> vendor_adapter = new ArrayAdapter<String>(Objects.requireNonNull(shipping_add_data.this),
                            R.layout.shipping_select_dropdown_item,                            //選項資料內容
                            vendor_list);   //自訂getView()介面格式(Spinner介面未展開時的View)
                    vendor_name_spinner.setAdapter(vendor_adapter);
                }

            });
        }
    };



    /**切換狀態按鈕**/
    int old=0;
    public void changeradio(int i)
    {
        Log.v("msg",i+"");
        linearLayout[old].setVisibility(View.VISIBLE);
        linearLayout[i].setVisibility(View.INVISIBLE);
        old=i;
    }


    //顯示Dialog
    private void set_dialog()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.shipping_add_vendor_dialog, null);
        shipping_add_vendor_name_bt = mView.findViewById(R.id.shipping_add_vendor_name_bt);
        new_vendor_name=mView.findViewById(R.id.new_vendor_name);

        //確定新增廠商按鈕
        AppCompatButton shipping_add_vendor_name_bt = mView.findViewById(R.id.shipping_add_vendor_name_bt);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        shipping_add_vendor_name_bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getNewVendor = new_vendor_name.getText().toString();
                mThreadHandler.post(r3);
                dialog.dismiss();
            }
        });
    }


    //工作名稱 r3 的工作內容
    private Runnable r3=new Runnable () {
        public void run()
        {
            shipping_webservice.add_vendor_name(getNewVendor);
            mUI_Handler.post(r4);
        }
    };

    //工作名稱 r4的工作內容
    private Runnable r4=new Runnable ()
    {
        public void run()
        {
            Toast.makeText(shipping_add_data.this,"新增成功",Toast.LENGTH_SHORT).show();
        }
    };


    //工作名稱 setShipping_data的工作內容
    private Runnable setShipping_data=new Runnable ()
    {
        public void run()
        {
            Log.v("test","vege_name "+vege_name_tx+" vendor_name "+vendor_name_tx+" num "+sale_num_str+" date "+set_date_str+" fg "+ship_status+" price "+total_earnings_str);
            add_ship_data_result = shipping_webservice.add_shipping_data(vege_name_tx,vendor_name_tx,sale_num_str,set_date_str,ship_status,total_earnings_str);
            mUI_Handler.post(getShipping_data);
            Log.v("test","setShipping_data" + add_ship_data_result );
        }
    };


    //工作名稱 getShipping_data的工作內容
    private Runnable getShipping_data=new Runnable ()
    {
        public void run()
        {
            Toast.makeText(shipping_add_data.this,"出貨資料新增成功",Toast.LENGTH_SHORT).show();
        }
    };


    //選擇日期相關
    public void set_ship_date(Context date_context)
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(date_context);
        View mView = LayoutInflater.from( date_context ).inflate( R.layout.home_canopy_dialog_calendar, null );
        mBuilder.setView(mView);
        final AlertDialog select_date_dialog = mBuilder.create();
        select_date_dialog.show();
        CalendarView get_date = mView.findViewById(R.id.select_date);
        get_date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int dm) {
                if (m<9 && dm<10) //月份日期皆個位數
                {   set_date_str = String.valueOf(y)+'0'+String.valueOf(m+1)+'0'+String.valueOf(dm); }
                else if (m<9 && dm>9) //月份個位數
                {   set_date_str = String.valueOf(y)+'0'+String.valueOf(m+1)+String.valueOf(dm); }
                else if(m>8 && dm<10) //日期個位數
                {   set_date_str = String.valueOf(y)+String.valueOf(m+1)+'0'+String.valueOf(dm);  }
                else //月份日期都不是個位數
                {   set_date_str = String.valueOf(y)+String.valueOf(m+1)+String.valueOf(dm); }

                if(!set_date_str.equals(""))
                {
                    set_ship_date.setText(set_date_str);
                    Log.v("test","onClick: "+set_date_str);
                    Log.v("test","set_ship_date: "+set_ship_date.getText());
                }
                select_date_dialog.dismiss();
            }
        });

    }

}