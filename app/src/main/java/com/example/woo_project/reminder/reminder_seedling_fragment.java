package com.example.woo_project.reminder;
//提醒主頁--育苗的fragment
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.woo_project.R;
import com.example.woo_project.record.record_webservice;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class reminder_seedling_fragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    TextView confirm,vege_name_tv,seedling_confirm_tv,edit_tv,delete_tv;
    TextInputEditText day1,day2,date_tiet;
    List<reminder_cardview> reminderList;
    RecyclerView reminder_rv;
    String reminder_vegetable_data,currentDateString,currentDateString2;
    Calendar c,c2;
    CharSequence todaydate;
    private Spinner unit_number_sp;
    int dayweek,day,mmonth;
    private List<String> unit_number_list = new ArrayList<>();
    private List<List<String>> reminder_seedling_data = new ArrayList<>();
    private List<List<String>> reminder_today_seedling_data = new ArrayList<>();
    public reminder_seedling_fragment() {
        // Requires empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.reminder_seedling_fragment_layout, container, false);

        reminder_rv=root.findViewById(R.id.rv1);


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        reminderList=new ArrayList<>();
        mThreadHandler.post(getReminder_seedling_data);

    }

    @Override
    public void onResume(){
        super.onResume();
        mThreadHandler.post(getReminder_seedling_data);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());


    }



    //去資料庫抓資料---預設會顯示全部育苗CardviewList，點擊Spinner可以切換全部、今日、這週、下週、自訂
    Runnable getReminder_seedling_data=new Runnable () {

        public void run() {

            switch (main_reminder.time_range_sp.getSelectedItemPosition())
            {
                case 0:
                    //顯示"全部"未收成的作物CardviewList(包含過期未收成以及未來要收成的)
                    reminder_seedling_data = reminder_webservice.reminder_seedling_data_list("39");
                    mUI_Handler.post(setReminder_seedling_data);
                    break;
                case 1:
                    //顯示"今日"要收成的作物CardviewList
                    reminder_seedling_data = reminder_webservice.reminder_today_seedling_data_list("39");
                    mUI_Handler.post(setReminder_seedling_data);
                    Log.v("test","reminder_seedling_data的長度: "+reminder_seedling_data);
                    break;
                case 2:
                    //顯示"這週"要收成的作物CardviewList
                    reminder_seedling_data = reminder_webservice.reminder_thisweek_seedling_data_list("39");
                    mUI_Handler.post(setReminder_seedling_data);
                    break;
                case 3:
                    //顯示"下週"要收成的作物CardviewList
                    reminder_seedling_data = reminder_webservice.reminder_nextweek_seedling_data_list("39");
                    mUI_Handler.post(setReminder_seedling_data);
                    break;
                case 4:
                    //顯示"自訂"區間的作物CardviewList
                    reminder_seedling_data = reminder_webservice.reminder_thisweek_seedling_data_list("39");
                    mUI_Handler.post(setReminder_seedling_data);
                    break;
                default:
                    break;
            }

        }

    };

    //從資料庫抓完並顯示育苗的CardviewList資料
    Runnable setReminder_seedling_data = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {

                   // 育苗編號 , 作物名稱 , 預計育苗日 , 育苗數量 , 育苗數量單位 , 育苗是否執行完 , 作物編號 , 圖片 ,
                    // 廠商名稱 , 備註 ,預計收成日 ,預計定植日, 預計育苗天數, 預計成長天數
                    List<String> seedling_vege_id = new ArrayList<>();
                    List<String> seedling_vege_name = new ArrayList<>();
                    List<String> seedling_day = new ArrayList<>();
                    List<String> seedling_number = new ArrayList<>();
                    List<String> seedling_number_unit = new ArrayList<>();
                    List<String> seedling_checkornot = new ArrayList<>();
                    List<String> seedling_vege_image = new ArrayList<>();

                    List<String> seedling_vendor = new ArrayList<>();
                    List<String> seedling_remark = new ArrayList<>();
                    List<String> seedling_preharvest = new ArrayList<>();
                    List<String> seedling_pregrowing = new ArrayList<>();
                    List<Integer> seedling_preday_num = new ArrayList<>();
                    List<Integer> seedling_pregrowing_num = new ArrayList<>();


                    Log.v("test","reminder_seedling_data的長度: "+reminder_seedling_data.size());
                    for(int i=0;i<reminder_seedling_data.size();i++) {

                        seedling_vege_id.add(reminder_seedling_data.get(i).get(0));
                        seedling_vege_name.add(reminder_seedling_data.get(i).get(1));
                        seedling_day.add(reminder_seedling_data.get(i).get(2));
                        seedling_number.add(reminder_seedling_data.get(i).get(3));
                        seedling_number_unit.add(reminder_seedling_data.get(i).get(4));
                        if(reminder_seedling_data.get(i).get(5).equals("True")) {
                            seedling_checkornot.add("checked");
                        }
                        else {
                            seedling_checkornot.add("unchecked");
                        }
                        seedling_vege_image.add(reminder_seedling_data.get(i).get(7));

                        seedling_vendor.add(reminder_seedling_data.get(i).get(8));
                        seedling_remark.add(reminder_seedling_data.get(i).get(9));
                        seedling_preharvest.add(reminder_seedling_data.get(i).get(10));
                        seedling_pregrowing.add(reminder_seedling_data.get(i).get(11));
                        seedling_preday_num.add(Integer.parseInt(reminder_seedling_data.get(i).get(12)));
                        seedling_pregrowing_num.add(Integer.parseInt(reminder_seedling_data.get(i).get(13)));

                    }
                    reminderList = new ArrayList<>();

                    //reminder_cardview(String id, String vege_img, String name, String tag1,
                    // String tag2,unit,String check_img, String vendor, String remark,String preharvest,String preseedling,
                    // String pregrowing,int preday_num, int pregrowing_num))
                    for(int i=0;i<seedling_vege_name.size();i++){
                        reminderList.add(new reminder_cardview(seedling_vege_id.get(i), seedling_vege_image.get(i),seedling_vege_name.get(i), seedling_day.get(i).substring(0,10),
                                seedling_number.get(i), seedling_number_unit.get(i), seedling_checkornot.get(i), seedling_vendor.get(i), seedling_remark.get(i), seedling_preharvest.get(i),
                                seedling_pregrowing.get(i),seedling_preday_num.get(i),seedling_pregrowing_num.get(i)));
                    }

                    reminder_rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    reminder_rv.setHasFixedSize(true);
                    reminder_rv.setAdapter(new reminder_seedling_fragment.reminder_first_layer_fragment_adapter(getActivity(), reminderList));


                }
            });

        }
    };



    //取得育苗數量所有的單位 EX:盤、株
    private Runnable getUnit_number_list=new Runnable () {

        public void run() {
            unit_number_list=reminder_webservice.Unit_number_list();
            //請經紀人指派工作名稱 r，給工人做
            Log.v("test","unit_number_list:"+unit_number_list);
            mUI_Handler.post(setUnit_number_list);

        }

    };

    private Runnable setUnit_number_list=new Runnable () {

        public void run() {

            ArrayAdapter<String> UnitNumberList = new ArrayAdapter<>(getContext(),
                    R.layout.record_select_dropdown_item,
                    unit_number_list);

            unit_number_sp.setAdapter(UnitNumberList);
            unit_number_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    amount_tv.setText(unit_number_sp.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }

    };


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
        c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayofmonth);

        currentDateString =  new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(c.getTime());

        date_tiet.setText(currentDateString);
    }

    private class reminder_first_layer_fragment_adapter extends RecyclerView.Adapter<reminder_seedling_fragment.reminder_first_layer_fragment_adapter.viewholder>  {

        private Context mctx;
        private List<reminder_cardview> reminderList;
        public reminder_first_layer_fragment_adapter(Context mctx, List<reminder_cardview> reminderList) {
            this.mctx = mctx;
            this.reminderList = reminderList;
        }

        @Override
        public reminder_first_layer_fragment_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mctx);
            View view=inflater.inflate(R.layout.reminder_cardview,viewGroup,false);
            return new reminder_first_layer_fragment_adapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(final reminder_first_layer_fragment_adapter.viewholder holder, final int position) {
            final reminder_cardview vege=reminderList.get(position);

            int drawableResourceId = mctx.getResources().getIdentifier(vege.getVege_img(), "drawable", mctx.getPackageName());
            Glide.with(mctx)
                    .load(drawableResourceId)
                    .into(holder.vege_img);

            holder.vegename.setText(vege.getName());
            int drawableResourceId2 = mctx.getResources().getIdentifier(vege.getCheck_img(), "drawable", mctx.getPackageName());
            Glide.with(mctx)
                    .load(drawableResourceId2)
                    .into(holder.check_img);
            holder.tag1.setText(vege.getTag1());
            holder.tag2.setText(vege.getTag2());
            holder.unit.setText(vege.getUnit());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            holder.plus_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);//初始化BottomSheet
                    View root = LayoutInflater.from(getContext()).inflate(R.layout.reminder_seedling_bottomsheetdialog,null);//連結的介面
                    bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                    ((View) root.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底

                    vege_name_tv = root.findViewById(R.id.vege_name_tv);
                    seedling_confirm_tv = root.findViewById(R.id.btmsheet_confirm_tv);
                    date_tiet = root.findViewById(R.id.date_tiet);
                    unit_number_sp = root.findViewById(R.id.unit_number_sp);

                    mThreadHandler.post(getUnit_number_list);

                    vege_name_tv.setText(vege.getName());

                    c2 = Calendar.getInstance();
                    todaydate = DateFormat.format("yyyy/MM/dd", c2.getTime());
                    date_tiet.setText(todaydate);

                    bottomSheetDialog.show();


                    date_tiet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            DialogFragment datePicker = new DatePickerFragment();
                            datePicker.setTargetFragment(reminder_seedling_fragment.this,0);
                            datePicker.show(getFragmentManager(),"date picker");

                        }

                    });

                    seedling_confirm_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bottomSheetDialog.dismiss();
                        }
                    });

                }
            });


            final boolean[] Check_delete_vege = new boolean[1];
            // 刪除育苗提醒
            final Runnable setSeedling_delete = new Runnable () {

                public void run() {
                    if(Check_delete_vege[0])
                    {
                        Toast.makeText(mctx,"變更成功!",Toast.LENGTH_SHORT).show();
                        mThreadHandler.post(getReminder_seedling_data);
                    }
                    else
                    {
                        Toast.makeText(mctx,"發生錯誤，請再試一次!",Toast.LENGTH_SHORT).show();
                    }
                }

            };

            final Runnable getSeedling_delete = new Runnable () {

                public void run() {

                    //need user id , vege id
                    Check_delete_vege[0] = reminder_webservice.reminder_seedling_delete(39,Integer.parseInt(vege.getId()));
                    //請經紀人指派工作名稱 r，給工人做
                    mUI_Handler.post(setSeedling_delete);
                }

            };



            holder.more_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);//初始化BottomSheet
                    View root = LayoutInflater.from(getContext()).inflate(R.layout.reminder_vege_data_bottomsheetdialog,null);//連結的介面
                    bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                    ((View) root.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底

                    vege_name_tv = root.findViewById(R.id.vege_data_name_tv);
                    edit_tv = root.findViewById(R.id.edit_tv);
                    delete_tv = root.findViewById(R.id.delete_tv);
                    vege_name_tv.setText(vege.getName());

                    //dialog詳細資訊
                    TextView in_vege_name_tv = root.findViewById(R.id.textView10);
                    TextView in_seedling_num_tv = root.findViewById(R.id.textView11);
                    TextView in_preharvest_tv = root.findViewById(R.id.textView17);
                    TextView in_vendor_tv = root.findViewById(R.id.textView20);
                    TextView in_remark_tv = root.findViewById(R.id.textView12);
                    TextView in_seedling_days_tv = root.findViewById(R.id.textView13);
                    TextView in_growing_days_tv = root.findViewById(R.id.textView14);
                    TextView in_seedling_tv = root.findViewById(R.id.textView15);
                    TextView in_growing_tv = root.findViewById(R.id.textView16);


                    in_vege_name_tv.setText(vege.getName());
                    in_seedling_num_tv.setText(vege.getTag2());
                    in_vendor_tv.setText(vege.getVendor());
                    in_remark_tv.setText(vege.getRemark());
                    in_seedling_days_tv.setText(String.valueOf(vege.getPreday_num()));
                    in_growing_days_tv.setText(String.valueOf(vege.getPregrowing_num()));
                    in_seedling_tv.setText(vege.getTag1());
                    in_growing_tv.setText(vege.getPregrowing());
                    in_preharvest_tv.setText(vege.getPreharvest());




                    bottomSheetDialog.show();


                    edit_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //建立意圖物件
                            Intent intent = new Intent(getContext(),reminder_setting_edit.class);
                            //設定傳遞鍵值
                            intent.putExtra("str_id",vege.getId());
                            intent.putExtra("str_vege_til",vege.getName());
                            intent.putExtra("str_seedling_num",vege.getTag2());
                            Log.v("edit","Integer.parseInt(vege.getTag2()): "+Integer.parseInt(vege.getTag2()));
                            intent.putExtra("str_vendor_tiet",vege.getVendor());
                            intent.putExtra("str_remark_edit",vege.getRemark());
                            intent.putExtra("str_day_of_harvest_tiet",vege.getPreharvest());
                            intent.putExtra("str_days_of_growing_tiet",String.valueOf(vege.getPregrowing_num()));
                            intent.putExtra("str_days_of_raising_seedling_tiet",String.valueOf(vege.getPreday_num()));
                            Log.v("edit","vege.getPregrowing_num():"+vege.getPregrowing_num());

                            //啟用意圖
                            startActivity(intent);
                            bottomSheetDialog.dismiss();
                        }
                    });
                    delete_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog delete_reminder = null;
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder//.setIcon(R.drawable.icon) //設定標題圖片
                                   // .setTitle("TITLE") //設定標題文字
                                    .setMessage("確認要刪除嗎?!") //設定內容文字
                                    .setPositiveButton("確認", new DialogInterface.OnClickListener()
                                    { //設定確定按鈕
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            mThreadHandler.post(getSeedling_delete);
                                            bottomSheetDialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener()
                                    { //設定取消按鈕
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            // TODO Auto-generated method stub
                                        }
                                    });

                            delete_reminder = builder.create(); //建立對話方塊並存成 dialog
                            delete_reminder.show();
                            //把button背景改為白色
                            Button nbutton = delete_reminder.getButton(DialogInterface.BUTTON_NEGATIVE);
                            nbutton.setBackgroundColor(Color.WHITE);
                            Button pbutton = delete_reminder.getButton(DialogInterface.BUTTON_POSITIVE);
                            pbutton.setBackgroundColor(Color.WHITE);



                        }
                    });
                }
            });

            // check_img 更換
            // check_status 為是否成功更新資料庫
            final boolean[] check_status = new boolean[1];


            final Runnable setCheck_result = new Runnable () {

                public void run() {
                    if(check_status[0])
                    {
                        if(vege.getCheck_img().equals("checked"))
                        {
                            vege.setCheck_img("unchecked");
                        }
                        else
                        {
                            vege.setCheck_img("checked");
                        }
                        int drawableResourceId3 = mctx.getResources().getIdentifier(vege.getCheck_img(), "drawable", mctx.getPackageName());
                        Glide.with(mctx)
                                .load(drawableResourceId3)
                                .into(holder.check_img);
                        Toast.makeText(mctx,"變更成功!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(mctx,"發生錯誤，請再試一次!",Toast.LENGTH_SHORT).show();
                    }
                }

            };

            final Runnable getCheck_result = new Runnable () {

                public void run() {
                    boolean Check_img_change;
                    // Check_img_change 為改變後的狀態
                    Check_img_change = !vege.getCheck_img().equals("checked");
                    Log.v("test","check_img_change: "+ Check_img_change);
                    //need user id , vege id, check fg
                    check_status[0] = reminder_webservice.reminder_seedling_data_list_checkornot("39",vege.getId(),Check_img_change);
                    //請經紀人指派工作名稱 r，給工人做
                    mUI_Handler.post(setCheck_result);
                }

            };



            holder.check_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //還沒寫好><
                    Log.v("test","vege.getId():" +vege.getId());
                    mThreadHandler.post(getCheck_result);
                }
            });



        }

        @Override
        public int getItemCount() {
            return reminderList.size();
        }



        class viewholder extends RecyclerView.ViewHolder {
            ImageView vege_img,check_img;
            ImageButton plus_imb,more_imb;
            TextView vegename,tag1,tag2,unit;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                vege_img = (ImageView) itemView.findViewById(R.id.vege_img);
                vegename = (TextView) itemView.findViewById(R.id.vegename);
                tag1 = (TextView) itemView.findViewById(R.id.tag1_tv);
                tag2 = (TextView) itemView.findViewById(R.id.tag2_tv);
                unit = itemView.findViewById(R.id.unit);
                plus_imb = itemView.findViewById(R.id.plus_imb);
                more_imb = itemView.findViewById(R.id.more_imb);
                check_img = (ImageView) itemView.findViewById(R.id.check_img);

            }


        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        //移除工人上的工作
        if (mThreadHandler != null) {
            mThreadHandler.removeCallbacks(getReminder_seedling_data);
        }
        //解聘工人 (關閉Thread)
        if (mThread != null) {
            mThread.quit();
        }
    }
}

