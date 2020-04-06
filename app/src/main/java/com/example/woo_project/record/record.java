package com.example.woo_project.record;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.woo_project.GlobalVariable;
import com.example.woo_project.R;
import com.example.woo_project.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class record extends AppCompatActivity
{
    boolean fg = false;
    int num = 0,id=2;
    Button  select_item , traceability;
    ImageButton showDialog ;
    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;
    AutoCompleteTextView record_search;
    String record_search_string="",record_month_select_string="",record_month_select_info="can't not found";


    List<record_Cardview> cardviewList;
    Spinner mSpinner2;
    GlobalVariable record_name;

    //篩選的物件
    Spinner record_canopy_sp,record_canopy_area_sp,record_plant_kind_sp,reocrd_plant_name_sp;

    //生產履歷的物件
    Spinner record_traceability_area_sp, record_traceability_canopy_sp ;
    Button record_traceability_commit ;
    List<List<String>> record_traceability_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_record);

        //生產履歷button
        traceability = findViewById(R.id.record_traceability);
        traceability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTraceability();
            }
        });

        record_name = (GlobalVariable) getApplicationContext();
        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

        record_search = findViewById(R.id.search_record);
        record_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //點擊後抓searchview的文字並跳轉到作物資訊
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("test","record TextView:"+record_search.getText());

                Intent x=new Intent(record.this,record_Information2.class);
                startActivity(x);
            }
        });

        //cardview 建立

        cardviewList = new ArrayList<>();
        cardviewList.add(new record_Cardview("0","非十字花科 皇宮菜","20/03/11"));

        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new record.CardAdapter(record.this, cardviewList));

        //Dialog 建立
        showDialog =  findViewById(R.id.Select_item);
        showDialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setSelect_dialog();
            }
        });

    }



    //生產履歷操作
    private void getTraceability()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(record.this);
        View mView = getLayoutInflater().inflate(R.layout.record_traceability_dialog, null);
        record_traceability_area_sp = mView.findViewById(R.id.record_traceability_area_sp);
        record_traceability_canopy_sp = mView.findViewById(R.id.record_traceability_canopy_sp);
        record_traceability_commit = mView.findViewById(R.id.record_traceability_commit);


        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

//        record_traceability_commit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                record_month_select_string = mSpinner2.getSelectedItem().toString();
//
//                if(!record_month_select_string.equals("月份…"))
//                {
//                    record_month_select_string = record_month_select_string.replace("月", "");
//                    Log.v("test", "record month:  " + record_month_select_string);
//                    //請經紀人指派工作名稱 r，給工人做
//                    //mThreadHandler.post(record_month_select_r1);
//                }
//                dialog.dismiss();
//            }
//        });



        // 調整Dialog從哪開始
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);

        // 去除四角黑色背景
        dialogWindow.setBackgroundDrawable(new BitmapDrawable());

        /* 將Dialog用螢幕大小百分比方式設置 */
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 取得螢幕寬和高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 取得對話框目前數值
        //p.height = (int) (d.getHeight() * 0.8); // 高度設為螢幕的0.8
        p.width = (int) (d.getWidth() * 0.6);  // 寬度設為螢幕的0.75
        dialogWindow.setAttributes(p);
        load_Traceability_sp();     //從資料庫抓棚架list



    }

    //跑生產履歷棚架資料
    private void load_Traceability_sp()
    {
        final Runnable set_Traceability = new Runnable() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable()
                {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {
                        List<String> canopy_area = new ArrayList<>();
                        //棚架
                        for (int i = 0; i < record_traceability_list.size(); i++) {
                            canopy_area.add(record_traceability_list.get(i).get(0));
                            Log.v("test", "record_select_list_canopy:  " + record_traceability_list.get(i).get(0));
                        }
                        ArrayAdapter<String> area_adapter = new ArrayAdapter<String>(record.this,
                                R.layout.record_select_dropdown_item,                            //選項資料內容
                                canopy_area);   //自訂getView()介面格式(Spinner介面未展開時的View)
                        record_traceability_area_sp.setAdapter(area_adapter);
                        record_traceability_area_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                //選中該區域的棚架list
                                List<String> record_plant_canopy = new ArrayList<>();
                                for (int i = 1; i < record_traceability_list.get(position).size(); i++) {
                                    record_plant_canopy.add(record_traceability_list.get(position).get(i));
                                }

                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(record.this,
                                        R.layout.record_select_dropdown_item,                            //選項資料內容
                                        record_plant_canopy);   //自訂getView()介面格式(Spinner介面未展開時的View)
                                record_traceability_canopy_sp.setAdapter(adapter1);
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                });

            }
        };

        Runnable get_Traceability = new Runnable() {
            @Override
            public void run() {
                record_traceability_list = record_webservice.record_select_list_canopy();
                mThreadHandler.post(set_Traceability);
            }
        };
        mThreadHandler.post(get_Traceability);
    }



    //篩選操作
    private void setSelect_dialog()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(record.this);
        View mView = getLayoutInflater().inflate(R.layout.record_select, null);
        record_canopy_area_sp = mView.findViewById(R.id.record_canopy_area_sp);
        record_canopy_sp = mView.findViewById(R.id.record_canopy_sp);
        reocrd_plant_name_sp = mView.findViewById(R.id.reocrd_plant_name_sp);
        record_plant_kind_sp = mView.findViewById(R.id.record_plant_kind_sp);

        //篩選
        select_item = mView.findViewById(R.id.record_select_item);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        select_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record_month_select_string = mSpinner2.getSelectedItem().toString();

                if(!record_month_select_string.equals("月份…"))
                {
                    record_month_select_string = record_month_select_string.replace("月", "");
                    Log.v("test", "record month:  " + record_month_select_string);
                    //請經紀人指派工作名稱 r，給工人做
                    //mThreadHandler.post(record_month_select_r1);
                }
                dialog.dismiss();
            }
        });



        // 調整Dialog從哪開始
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);

        // 去除四角黑色背景
        dialogWindow.setBackgroundDrawable(new BitmapDrawable());

        /* 將Dialog用螢幕大小百分比方式設置 */
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 取得螢幕寬和高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 取得對話框目前數值
        // p.height = (int) (d.getHeight() * 0.8); // 高度設為螢幕的0.8
        p.width = (int) (d.getWidth() * 0.75);  // 寬度設為螢幕的0.75
        dialogWindow.setAttributes(p);

        mThreadHandler.post(getRecord_select_list);
    }


    //從資料庫抓棚架list和作物list
    List<List<String>> record_select_list_canopy = new ArrayList<>();
    List<List<String>> record_select_list_plant = new ArrayList<>();
    Runnable getRecord_select_list = new Runnable() {
        @Override
        public void run() {
            record_select_list_canopy = record_webservice.record_select_list_canopy();
            record_select_list_plant = record_webservice.record_select_list_plant();
            mThreadHandler.post(setRecord_select_list);
        }
    };

    Runnable setRecord_select_list = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {
                    List<String> canopy_area = new ArrayList<>();
                    List<String> plant_kind = new ArrayList<>();

                    //棚架
                    for(int i = 0 ; i <record_select_list_canopy.size();i++)
                    {
                        canopy_area.add(record_select_list_canopy.get(i).get(0));
                        Log.v("test","record_select_list_canopy:  "+record_select_list_canopy.get(i).get(0));
                        Log.v("test","record_select_list_canopy1:  "+record_select_list_canopy.get(i));
                        //record_select_list_canopy.get(i).remove(0);
                    }
                    ArrayAdapter<String> area_adapter = new ArrayAdapter<String>(record.this,
                            R.layout.record_select_dropdown_item,                            //選項資料內容
                            canopy_area);   //自訂getView()介面格式(Spinner介面未展開時的View)
                    record_canopy_area_sp.setAdapter(area_adapter);

                    record_canopy_area_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            //選中該區域的棚架list
                            List<String> record_plant_canopy = new ArrayList<>();
                            for(int i = 1; i < record_select_list_canopy.get(position).size();i++)
                            {
                                record_plant_canopy.add(record_select_list_canopy.get(position).get(i));
                            }


                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(record.this,
                                    R.layout.record_select_dropdown_item,                            //選項資料內容
                                    record_plant_canopy);   //自訂getView()介面格式(Spinner介面未展開時的View)
                            record_canopy_sp.setAdapter(adapter1);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });


                    //作物
                    for(int i = 0 ; i <record_select_list_plant.size();i++)
                    {
                        plant_kind.add(record_select_list_plant.get(i).get(0));
                        Log.v("test","record_select_list_plant:  "+record_select_list_plant.get(i).get(0));
                        Log.v("test","record_select_list_plant1:  "+record_select_list_plant.get(i));
                        //record_select_list_canopy.get(i).remove(0);
                    }

                    //作物種類
                    ArrayAdapter<String> plant_adapter = new ArrayAdapter<String>(record.this,
                            R.layout.record_select_dropdown_item,                            //選項資料內容
                            plant_kind);   //自訂getView()介面格式(Spinner介面未展開時的View)
                    record_plant_kind_sp.setAdapter(plant_adapter);

                    //作物名稱
                    record_plant_kind_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            //選中作物類別的作物list
                            List<String> record_plant_name = new ArrayList<>();
                            for(int i = 1; i < record_select_list_plant.get(position).size();i++)
                            {
                                record_plant_name.add(record_select_list_plant.get(position).get(i));
                            }


                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(record.this,
                                    R.layout.record_select_dropdown_item,                            //選項資料內容
                                    record_plant_name);   //自訂getView()介面格式(Spinner介面未展開時的View)
                            reocrd_plant_name_sp.setAdapter(adapter1);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });


                }
            });

        }
    };


    /**
    Runnable record_search_r1 = new Runnable() {
        @Override
        public void run() {
            record_search_string = webservice.Record_list(record_name.getUser_gmail());
            mThreadHandler.post(record_search_r2);

        }
    };

    Runnable record_search_r2 = new Runnable() {
        @Override
        public void run()
    {
            new Handler(Looper.getMainLooper()).post(new Runnable()
        {
                @Override
                public void run() {
                    String[] all ,split,split_record_search_img;
                    List<String> split_record_search = new ArrayList<>();
                    List<String> split_record_search_date = new ArrayList<>();
                    List<String> split_record_search_id = new ArrayList<>();
                    if (!record_search_string.equals("can't not found"))
                    {
                        all = record_search_string.split("分開");
                        split = all[0].split("%");
                        split_record_search_img = all[1].split("切");
                        int index=0;
                        if(split.length < 3)
                        {
                            split_record_search_date.add(split[index]);
                            split_record_search.add(split[index+1]);
                            split_record_search_id.add("0");
                        }
                        else
                        {
                            for(int i = 0;i < split.length/3;i++)
                            {
                                split_record_search_date.add(split[index]);
                                split_record_search.add(split[index+1]);
                                split_record_search_id.add(split[index+2]);
                                index = index + 3;
                            }
                        }



                        //搜尋bar list view
                        Log.v("test","record的紀錄: "+ split_record_search.get(0));
                        record_search.setAdapter(new ArrayAdapter<>(record.this,
                                android.R.layout.simple_list_item_1, split_record_search));

                    int size1 = cardviewList.size();
                    for(int i=0;i < size1;i++)
                    {
                        Log.v("test","cardviewList.size(): "+cardviewList.size());
                        cardviewList.remove(0);
                    }

                    for(int num = 0; num < split_record_search.size();num++)
                    {
                        cardviewList.add(new record_Cardview(split_record_search_id.get(num),split_record_search.get(num),split_record_search_date.get(num), split_record_search_img[num]));
                        //cardviewList.add(new record_Cardview(String.valueOf(num),split_record_search.get(num),split_record_search_date.get(num), split_record_search_img[num]));
                    }

                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(new record.CardAdapter(record.this, cardviewList));
                    }
                }
            });
        }
    };


    Runnable  record_month_select_r1 = new Runnable() {
        @Override
        public void run() {
            record_month_select_info = webservice.Record_Select_month(record_month_select_string);
            mThreadHandler.post(record_month_select_r2);
        }
    };

    Runnable  record_month_select_r2 = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    if (!record_month_select_info.equals("can't not found"))
                    {

                        split_line3 = record_month_select_info.split("切"); //切割作物名和圖片編碼
                        split_record_vege = split_line3[0].split("%");   //切割各作物
                        split_record_date = split_line3[1].split("%");   //時間
                        split_record_img = split_line3[2].split("圖");  //切割各圖片編碼
                        split_record_id_string = split_line3[3].split("%");



                        int size1 = cardviewList.size();
                        for(int i=0;i < size1;i++)
                        {
                            Log.v("test","cardviewList.size(): "+cardviewList.size());
                            cardviewList.remove(0);
                        }

                        for(int num = 0; num < split_record_vege.length;num++)
                        {
                            cardviewList.add(new record_Cardview(split_record_id_string[num],split_record_vege[num],split_record_date[num], split_record_img[num]));
                        }

                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(new record.CardAdapter(record.this, cardviewList));
                    }
                    else
                    {
                        Toast.makeText(record.this,"查無資料!",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除工人上的工作
        if (mThreadHandler != null) {
            mThreadHandler.removeCallbacks(record_search_r1 );
            mThreadHandler.removeCallbacks(record_month_select_r1 );

        }
        //解聘工人 (關閉Thread)
        if (mThread != null) {
            mThread.quit();
        }

    }
**/


    private class CardAdapter extends  RecyclerView.Adapter<CardAdapter.ViewHolder>
    {
        private Context context;
        public List<record_Cardview> cardviewList;

        CardAdapter(Context context, List<record_Cardview> cardviewList) {
            this.context = context;
            this.cardviewList = cardviewList;
        }

        @Override
        public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.record_cardview,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CardAdapter.ViewHolder viewHolder, int i) {
            final record_Cardview cardview = cardviewList.get(i);
            viewHolder.tx1.setText(String.valueOf(cardview.getName()));
            //Glide.with(record.this).load(cardview.getImage()).into(viewHolder.plantId);
            viewHolder.tx2.setText(String.valueOf((cardview.getTime())));
            Log.v("test","cardview.getName():  "+cardview.getName());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

 //                   addItem(cardviewList.size());
                    //record_name.setRecord_vege_name(cardview.getName());

                    Intent intent = new Intent(record.this, record_Information.class);
                    intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return cardviewList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
                    ImageView plantId;
                    TextView tx1,tx2;

                    ViewHolder(View itemView){
                        super(itemView);
                        plantId = (ImageView) itemView.findViewById(R.id.plantId);
                        tx1 = (TextView) itemView.findViewById(R.id.tx1);
                        tx2 = (TextView) itemView.findViewById(R.id.tx2);
                    }
        }
        public  void addItem(int i){
            fg = true;
//            num = cardviewList.size()-1;
//            //add(位置,資料)
//            cardviewList.add(i, new record_Cardview(id,"小白菜", R.drawable.icon201));
//            id=id+1;
//            notifyItemInserted(i);
        }
    }

}
