package com.example.woo_project.reminder;
//提醒主頁--收成的fragment
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.woo_project.R;
import com.example.woo_project.record.record_webservice;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class reminder_harvest_fragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    private TextView vege_name_tv,edit_tv,delete_tv,harvest_cancel_tv;
    private TextInputEditText harvest_btm_date_tiet,harvest_btm_num_tiet;
    private Calendar c,today_cal,c2;
    private OptionsPickerView pvOptions;
    private ImageView harvest_btm_plus;
    CharSequence planting_date;
    View view;
    String Vege,Tag1,currentDateString;
    List<reminder_cardview_harvest> reminderList;
    RecyclerView reminder_rv;
    String reminder_vegetable_data;
    long real_date_index;
    private List<List<String>> reminder_harvest_data = new ArrayList<>();
    private List<List<String>> reminder_today_seedling_data = new ArrayList<>();

    TextView bottom_vege_name_tv;

    public reminder_harvest_fragment() {
        // Requires empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.reminder_seedling_fragment_layout, container, false);
        reminder_rv=view.findViewById(R.id.rv1);

        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

        mThreadHandler.post(getReminder_harvest_data);

    }


    @Override
    public void onStart() {

        reminderList=new ArrayList<>();

        mThreadHandler.post(getReminder_harvest_data);

        Log.v("test","目前所篩選的時間"+main_reminder.time_range_sp.getSelectedItemPosition());

        super.onStart();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
        c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayofmonth);

        currentDateString =  new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(c.getTime());

        harvest_btm_date_tiet.setText(currentDateString);


        //定義時間格式

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //取的兩個時間

        Date dt1 = null;
        try {
            dt1 = sdf.parse(planting_date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date dt2 = null;
        try {
            dt2 = sdf.parse(currentDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //取得兩個時間的Unix時間

        Long ut1=dt1.getTime();

        Long ut2=dt2.getTime();

        //相減獲得兩個時間差距的毫秒

        long timeP=ut2-ut1;//毫秒差
        real_date_index=timeP/(1000*60*60*24);//日差


    }

    //去資料庫抓資料---預設會顯示全部收成CardviewList，點擊Spinner可以切換全部、今日、這週、下週、自訂
    Runnable getReminder_harvest_data=new Runnable () {

        public void run() {


            switch (main_reminder.time_range_sp.getSelectedItemPosition())
            {
                case 0:
                    //顯示"全部"未收成的作物CardviewList(包含過期未收成以及未來要收成的)
                    reminder_harvest_data = reminder_webservice.reminder_harvest_data_list("39");
                    mUI_Handler.post(setReminder_harvest_data);
                    break;
                case 1:
                    //顯示"今日"要收成的作物CardviewList
                    reminder_harvest_data = reminder_webservice.reminder_today_harvest_data_list("39");
                    mUI_Handler.post(setReminder_harvest_data);
                    break;
                case 2:
                    //顯示"這週"要收成的作物CardviewList
                    reminder_harvest_data = reminder_webservice.reminder_thisweek_harvest_data_list("39");
                    mUI_Handler.post(setReminder_harvest_data);
                    break;
                case 3:
                    //顯示"下週"要收成的作物CardviewList
                    reminder_harvest_data = reminder_webservice.reminder_nextweek_harvest_data_list("39");
                    mUI_Handler.post(setReminder_harvest_data);
                    break;
                case 4:
                    //顯示"自訂"區間的作物CardviewList
                    reminder_harvest_data = reminder_webservice.reminder_custom_harvest_data_list("39",main_reminder.StartDateTiet.getText().toString(),main_reminder.EndDateTiet.getText().toString());
                    mUI_Handler.post(setReminder_harvest_data);
                    break;
                default:
                    break;
            }


        }

    };

    //從資料庫抓完並顯示收成的CardviewList資料
    private Runnable setReminder_harvest_data = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {

                    List<String> harvest_vege_id = new ArrayList<>();
                    List<String> harvest_vege_name = new ArrayList<>();
                    List<String> harvest_day = new ArrayList<>();
                    List<String> harvest_number = new ArrayList<>();
                    List<String> harvest_number_unit = new ArrayList<>();
                    List<String> harvest_checkornot = new ArrayList<>();
                    List<String> harvest_vege_image = new ArrayList<>();

                    List<String> harvest_vendor = new ArrayList<>();
                    List<String> harvest_remark = new ArrayList<>();
                    List<String> harvest_preharvest = new ArrayList<>();
                    List<String> harvest_pregrowing = new ArrayList<>();
                    List<Integer> harvest_preday_num = new ArrayList<>();
                    List<Integer> harvest_pregrowing_num = new ArrayList<>();
                    List<String> canopy_name = new ArrayList<>();
                    //png_list
                    Log.v("test","reminder_harvest_data的長度: "+reminder_harvest_data.size());
                    for(int i=0;i<reminder_harvest_data.size();i++) {

                        harvest_vege_id.add(reminder_harvest_data.get(i).get(0));
                        Log.v("test","tese::::"+reminder_harvest_data.get(i).get(0));
                        harvest_vege_name.add(reminder_harvest_data.get(i).get(1));
                        harvest_day.add(reminder_harvest_data.get(i).get(2));
                        harvest_number.add(reminder_harvest_data.get(i).get(3));
                        harvest_number_unit.add(reminder_harvest_data.get(i).get(4));
                        if(reminder_harvest_data.get(i).get(6).equals("True")) {
                            harvest_checkornot.add("checked");
                        }
                        else {
                            harvest_checkornot.add("unchecked");
                        }
                        harvest_vege_image.add(reminder_harvest_data.get(i).get(7));

                        harvest_vendor.add(reminder_harvest_data.get(i).get(8));

                        harvest_remark.add(reminder_harvest_data.get(i).get(9));
                        harvest_preharvest.add(reminder_harvest_data.get(i).get(10));
                        harvest_pregrowing.add(reminder_harvest_data.get(i).get(11));
                        harvest_preday_num.add(Integer.parseInt(reminder_harvest_data.get(i).get(12)));
                        harvest_pregrowing_num.add(Integer.parseInt(reminder_harvest_data.get(i).get(13)));
                        canopy_name.add(reminder_harvest_data.get(i).get(14));
                    }

                    reminderList = new ArrayList<>();

                    for(int i=0;i<harvest_vege_name.size();i++) {
                        reminderList.add(new reminder_cardview_harvest(harvest_vege_id.get(i), harvest_vege_image.get(i), harvest_vege_name.get(i), harvest_day.get(i).substring(0, 10),
                                harvest_number.get(i), harvest_number_unit.get(i), harvest_checkornot.get(i), harvest_vendor.get(i), harvest_remark.get(i), harvest_preharvest.get(i),
                                harvest_pregrowing.get(i), harvest_preday_num.get(i), harvest_pregrowing_num.get(i),canopy_name.get(i)));

                    }
//                    for (int i = 0; i < reminderList.size(); i++) {
//                        counter.add(0);
//                    }
                    reminder_rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    reminder_rv.setHasFixedSize(true);
                    reminder_rv.setAdapter(new reminder_harvest_fragment.reminder_first_layer_fragment_adapter(getActivity(), reminderList));


                }
            });

        }
    };

    //從資料庫抓棚架list
    List<List<String>> record_select_list_canopy = new ArrayList<>();
    List<String> canopy_split = new ArrayList<>();
    List<List<String>> canopy_name = new ArrayList<>();
    List<String> canopy_area = new ArrayList<>();
    Runnable getRemind_select_canopy = new Runnable() {
        @Override
        public void run() {
            record_select_list_canopy = record_webservice.record_select_list_canopy();
            mUI_Handler.post(setRemind_select_canopy);
        }
    };
    Runnable setRemind_select_canopy = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {

                    canopy_area = new ArrayList<>();
                    //棚架
                    for(int i = 0 ; i <record_select_list_canopy.size();i++)
                    {
                        canopy_area.add(record_select_list_canopy.get(i).get(0));
                        Log.v("test","record_select_list_canopy:  "+record_select_list_canopy.get(i).get(0));
                        Log.v("test","record_select_list_canopy1:  "+record_select_list_canopy.get(i));
                        //record_select_list_canopy.get(i).remove(0);

                        for(int j = 1; j < record_select_list_canopy.get(i).size();j++)
                        {
                            canopy_split.add(record_select_list_canopy.get(i).get(j));
                        }
                        canopy_name.add(canopy_split);
                        canopy_split = new ArrayList<>();
                    }
//                    initOptionPicker_canopy_list();
//                    pvOptions.show();


                }
            });

        }
    };


    private class reminder_first_layer_fragment_adapter extends RecyclerView.Adapter<reminder_harvest_fragment.reminder_first_layer_fragment_adapter.viewholder>  {

        private Context mctx;
        private List<reminder_cardview_harvest> reminderList;
        public reminder_first_layer_fragment_adapter(Context mctx, List<reminder_cardview_harvest> reminderList) {
            this.mctx = mctx;
            this.reminderList = reminderList;
        }

        @Override
        public reminder_harvest_fragment.reminder_first_layer_fragment_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mctx);
            View view=inflater.inflate(R.layout.reminder_cardview_harvest,viewGroup,false);
            return new reminder_harvest_fragment.reminder_first_layer_fragment_adapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(final reminder_harvest_fragment.reminder_first_layer_fragment_adapter.viewholder holder, final int position)
        {
            final reminder_cardview_harvest vege=reminderList.get(position);

            holder.vege.setText(String.valueOf(vege.getName()));
            Vege=String.valueOf(vege.getName());
            int drawableResourceId = mctx.getResources().getIdentifier(vege.getVege_img(), "drawable", mctx.getPackageName());
            Glide.with(mctx)
                    .load(drawableResourceId)
                    .into(holder.vege_img);

            int drawableResourceId2 = mctx.getResources().getIdentifier(vege.getCheck_img(), "drawable", mctx.getPackageName());
            Glide.with(mctx)
                    .load(drawableResourceId2)
                    .into(holder.check_img);
            holder.tag1.setText(String.valueOf(vege.getTag1()));
            Tag1=String.valueOf(vege.getTag1());
            holder.tag2.setText(String.valueOf(vege.getTag2()));
            holder.unit.setText(vege.getUnit());
            holder.canopy_name_cv.setText(vege.getCanopy_name());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

//            holder.plus_imb.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                }
//            });

            holder.more_imb.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);//初始化BottomSheet
                    View root = LayoutInflater.from(getContext()).inflate(R.layout.reminder_vege_data_bottomsheetdialog3,null);//連結的介面
                    bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                    ((View) root.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底

                    bottom_vege_name_tv = root.findViewById(R.id.vege_data_name_tv);
                    edit_tv = root.findViewById(R.id.edit_tv);
                    delete_tv = root.findViewById(R.id.delete_tv);
                    bottom_vege_name_tv.setText(vege.getName());

                    //dialog詳細資訊
                    TextView in_vege_name_tv = root.findViewById(R.id.textView10);
                    TextView in_canopy_tv = root.findViewById(R.id.textView21);
                    TextView in_seedling_num_tv = root.findViewById(R.id.textView11);
                    TextView in_preharvest_tv = root.findViewById(R.id.textView17);
                    TextView in_vendor_tv = root.findViewById(R.id.textView20);
                    TextView in_remark_tv = root.findViewById(R.id.textView12);
                    TextView in_seedling_days_tv = root.findViewById(R.id.textView13);
                    TextView in_growing_days_tv = root.findViewById(R.id.textView14);
                    TextView in_seedling_tv = root.findViewById(R.id.textView16);
                    TextView in_growing_tv = root.findViewById(R.id.textView15);


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
//                            //建立意圖物件
//                            Intent intent = new Intent(getContext(),reminder_setting_edit.class);
//                            //設定傳遞鍵值
//                            intent.putExtra("str_id",vege.getId());
//                            intent.putExtra("str_vege_til",vege.getName());
//                            intent.putExtra("str_seedling_num",vege.getTag2());
//                            Log.v("edit","Integer.parseInt(vege.getTag2()): "+Integer.parseInt(vege.getTag2()));
//                            intent.putExtra("str_vendor_tiet",vege.getVendor());
//                            intent.putExtra("str_remark_edit",vege.getRemark());
//                            intent.putExtra("str_day_of_harvest_tiet",vege.getPreharvest());
//                            intent.putExtra("str_days_of_growing_tiet",String.valueOf(vege.getPregrowing_num()));
//                            intent.putExtra("str_days_of_raising_seedling_tiet",String.valueOf(vege.getPreday_num()));
//                            Log.v("edit","vege.getPregrowing_num():"+vege.getPregrowing_num());
//
//                            //啟用意圖
//                            startActivity(intent);
//                            bottomSheetDialog.dismiss();
                        }
                    });
                    delete_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
//                            AlertDialog delete_reminder = null;
//                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                            builder//.setIcon(R.drawable.icon) //設定標題圖片
//                                    // .setTitle("TITLE") //設定標題文字
//                                    .setMessage("確認要刪除嗎?!") //設定內容文字
//                                    .setPositiveButton("確認", new DialogInterface.OnClickListener()
//                                    { //設定確定按鈕
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which)
//                                        {
//                                            // mThreadHandler.post(getSeedling_delete);
//                                            bottomSheetDialog.dismiss();
//                                        }
//                                    })
//                                    .setNegativeButton("取消", new DialogInterface.OnClickListener()
//                                    { //設定取消按鈕
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which)
//                                        {
//                                            // TODO Auto-generated method stub
//                                        }
//                                    });
//
//                            delete_reminder = builder.create(); //建立對話方塊並存成 dialog
//                            delete_reminder.show();
//                            //把button背景改為白色
//                            Button nbutton = delete_reminder.getButton(DialogInterface.BUTTON_NEGATIVE);
//                            nbutton.setBackgroundColor(Color.WHITE);
//                            Button pbutton = delete_reminder.getButton(DialogInterface.BUTTON_POSITIVE);
//                            pbutton.setBackgroundColor(Color.WHITE);
                        }
                    });
                }
            });

            holder.check_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);//初始化BottomSheet
                    View root = LayoutInflater.from(getContext()).inflate(R.layout.reminder_harvest_bottomsheetdialog,null);//連結的介面
                    bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                    ((View) root.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底

                    final RecyclerView planting_RV;
                    final List<reminder_harvest_bottomsheetdialog_cardview> bottomsheetList = new ArrayList<>();
                    planting_RV = root.findViewById(R.id.planting_RV);



                    vege_name_tv = root.findViewById(R.id.vege_name_tv);
                    harvest_cancel_tv = root.findViewById(R.id.btmsheet_cancel_tv);
                    harvest_btm_date_tiet = root.findViewById(R.id.harvest_btm_date_tiet);
                    harvest_btm_num_tiet = root.findViewById(R.id.harvest_btm_num_tiet);
                    Button go_wait_ship_bt = root.findViewById(R.id.go_wait_ship_bt);
                    Button go_ship_bt = root.findViewById(R.id.go_ship_bt);
                    //harvest_btm_plus = root.findViewById(R.id.harvest_btm_plus);
                    vege_name_tv.setText(vege.getName());


                    bottomSheetDialog.show();


                    //看日曆
                    harvest_btm_date_tiet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            DialogFragment datePicker = new DatePickerFragment();
                            datePicker.setTargetFragment(reminder_harvest_fragment.this,0);
                            datePicker.show(getFragmentManager(),"date picker");

                        }

                    });

//                    harvest_btm_plus.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            bottomsheetList.add(new reminder_harvest_bottomsheetdialog_cardview(bottomsheetList.size(), Integer.parseInt(harvest_btm_num_tiet.getText().toString()), harvest_btm_date_tiet.getText().toString()));
//
//                            planting_RV.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//                            planting_RV.setHasFixedSize(true);
//                            planting_RV.setAdapter(new reminder_harvest_bottomsheetdialog_Adapter(reminder_harvest_fragment.this, bottomsheetList));
//
//                        }
//                    });


                     //把育苗日給c2
                    List<String> time_list = Arrays.asList(vege.getTag1().split("-"));
                    Log.v("date", time_list.get(0) + "  " + time_list.get(1) + "  " + time_list.get(2));
                    c2 = Calendar.getInstance();
                    c2.set(Integer.parseInt(time_list.get(0)), Integer.parseInt(time_list.get(1)) - 1, Integer.parseInt(time_list.get(2)));

                    planting_date = DateFormat.format("yyyy-MM-dd", c2.getTime());
                    harvest_btm_date_tiet.setText(planting_date);


                    final Boolean[] harvest_check_status = {false};
                    final Runnable getReminder_harvest_data_list_wait=new Runnable () {
                        public void run() {
                            if(harvest_check_status[0])
                            {
                                Toast.makeText(getContext(),"輸入成功 !",Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                                mThreadHandler.post(getReminder_harvest_data);
                            }
                            else
                            {
                                Toast.makeText(getContext(),"輸入失敗 !",Toast.LENGTH_SHORT).show();
                            }

                        }
                    };

                   final Runnable setReminder_harvest_data_list_wait=new Runnable () {
                        public void run() {
                            harvest_check_status[0] = reminder_webservice.reminder_harvest_data_list_checkornot("39",Integer.parseInt(vege.getId()), currentDateString,Integer.parseInt(String.valueOf(real_date_index)),Integer.parseInt(harvest_btm_num_tiet.getText().toString()),false);
                            mUI_Handler.post(getReminder_harvest_data_list_wait);

                        }
                    };


                    final Runnable getReminder_harvest_data_list_ship=new Runnable () {
                        public void run() {
                            if(harvest_check_status[0])
                            {
                                Toast.makeText(getContext(),"輸入成功 !",Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                                mThreadHandler.post(getReminder_harvest_data);
                            }
                            else
                            {
                                Toast.makeText(getContext(),"輸入失敗 !",Toast.LENGTH_SHORT).show();
                            }

                        }
                    };

                    final Runnable setReminder_harvest_data_list_ship=new Runnable () {
                        public void run() {
                            harvest_check_status[0] = reminder_webservice.reminder_harvest_data_list_checkornot("39",Integer.parseInt(vege.getId()), currentDateString,Integer.parseInt(String.valueOf(real_date_index)),Integer.parseInt(harvest_btm_num_tiet.getText().toString()),true);
                            mUI_Handler.post(getReminder_harvest_data_list_ship);

                        }
                    };




                    //部分收成
                    go_wait_ship_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mThreadHandler.post(setReminder_harvest_data_list_wait);
                        }
                    });


                    //全部收成
                    go_ship_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mThreadHandler.post(setReminder_harvest_data_list_ship);
                        }
                    });



                    harvest_cancel_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bottomSheetDialog.dismiss();
                        }
                    });
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
            TextView vege,tag1,tag2,unit;
            TextView canopy_name_cv;

            public viewholder(View itemView) {
                super(itemView);
                vege_img = itemView.findViewById(R.id.vege_img);
                //plus_imb = itemView.findViewById(R.id.plus_imb);
                more_imb = itemView.findViewById(R.id.more_imb);
                vege = itemView.findViewById(R.id.vegename);
                tag1 = itemView.findViewById(R.id.tag1_tv);
                tag2 = itemView.findViewById(R.id.tag2_tv);
                unit = itemView.findViewById(R.id.unit);
                check_img = itemView.findViewById(R.id.check_img);
                canopy_name_cv = itemView.findViewById(R.id.canopy);

            }


        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //移除工人上的工作
        if (mThreadHandler != null) {
            mThreadHandler.removeCallbacks(getReminder_harvest_data);
        }
        //解聘工人 (關閉Thread)
        if (mThread != null) {
            mThread.quit();
        }
    }
}
