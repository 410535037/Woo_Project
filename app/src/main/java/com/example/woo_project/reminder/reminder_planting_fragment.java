package com.example.woo_project.reminder;
//提醒主頁--定植的fragment
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.example.woo_project.R;
import com.example.woo_project.record.record;
import com.example.woo_project.record.record_webservice;
import com.example.woo_project.reminder.json.split_planting_setting;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class reminder_planting_fragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    private OptionsPickerView pvOptions;
    private TextView vege_name_tv,planting_confirm_tv,last_num_unit,last_num;
    List<reminder_cardview_planting> reminderList;
    RecyclerView reminder_rv;
    String reminder_vegetable_data,reminder_unit_data,Vege,Tag1,currentDateString;
    CharSequence todaydate;
    ArrayList<Integer> counter = new ArrayList<>();
    Spinner record_canopy_area_sp,record_canopy_sp,remind_amount_unit_sp;
    TextInputEditText num_ed,date_tiet,greenhouse_tiet,planting_num;
    String[] Amount;
    Calendar c,c2;
    private List<List<String>> reminder_planting_data = new ArrayList<>();
    boolean split_planting_status=false;
    public reminder_planting_fragment() {
        // Requires empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reminder_seedling_fragment_layout, container, false);

        reminder_rv = view.findViewById(R.id.rv1);
        reminderList=new ArrayList<>();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        reminderList=new ArrayList<>();
        mThreadHandler.post(getReminder_planting_data);


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

        mThreadHandler.post(getRemind_select_canopy);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
        c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayofmonth);

        currentDateString =  new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(c.getTime());

        date_tiet.setText(currentDateString);
    }

    private Runnable getReminder_planting_data=new Runnable () {

        public void run() {
            switch (main_reminder.time_range_sp.getSelectedItemPosition())
            {
                case 0:
                    //顯示"全部"未收成的作物CardviewList(包含過期未收成以及未來要收成的)
                    reminder_planting_data = reminder_webservice.reminder_planting_data_list("39");
                    mUI_Handler.post(setReminder_planting_data);
                    break;
                case 1:
                    //顯示"今日"要收成的作物CardviewList
                    reminder_planting_data = reminder_webservice.reminder_today_planting_data_list("39");
                    mUI_Handler.post(setReminder_planting_data);
                    Log.v("test","reminder_seedling_data的長度: "+reminder_planting_data);
                    break;
                case 2:
                    //顯示"這週"要收成的作物CardviewList
                    reminder_planting_data = reminder_webservice.reminder_thisweek_planting_data_list("39");
                    mUI_Handler.post(setReminder_planting_data);
                    break;
                case 3:
                    //顯示"下週"要收成的作物CardviewList
                    reminder_planting_data = reminder_webservice.reminder_nextweek_planting_data_list("39");
                    mUI_Handler.post(setReminder_planting_data);
                    break;
                case 4:
                    //顯示"自訂"區間的作物CardviewList
                    reminder_planting_data = reminder_webservice.reminder_custom_planting_data_list("39",main_reminder.StartDateTiet.getText().toString(),main_reminder.EndDateTiet.getText().toString());
                    mUI_Handler.post(setReminder_planting_data);
                    break;
                default:
                    break;
            }

        }

    };

    private Runnable setReminder_planting_data = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {

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
                    List<String> canopy_name = new ArrayList<>();
                    //png_list
                    Log.v("planting","reminder_planting_data的長度: "+reminder_planting_data.size());
                    Log.v("planting","reminder_planting_data:" +reminder_planting_data);
                    for(int i=0;i<reminder_planting_data.size();i++) {

                        seedling_vege_id.add(reminder_planting_data.get(i).get(0));
                        seedling_vege_name.add(reminder_planting_data.get(i).get(1));
                        seedling_day.add(reminder_planting_data.get(i).get(2));
                        seedling_number.add(reminder_planting_data.get(i).get(3));
                        seedling_number_unit.add(reminder_planting_data.get(i).get(4));
                        if(reminder_planting_data.get(i).get(6).equals("True")) {
                            seedling_checkornot.add("checked");
                        }
                        else {
                            seedling_checkornot.add("unchecked");
                        }
                        seedling_vege_image.add(reminder_planting_data.get(i).get(7));

                        seedling_vendor.add(reminder_planting_data.get(i).get(8));
                        seedling_remark.add(reminder_planting_data.get(i).get(9));
                        seedling_preharvest.add(reminder_planting_data.get(i).get(10));
                        seedling_pregrowing.add(reminder_planting_data.get(i).get(11));
                        seedling_preday_num.add(Integer.parseInt(reminder_planting_data.get(i).get(12)));
                        seedling_pregrowing_num.add(Integer.parseInt(reminder_planting_data.get(i).get(13)));
                        canopy_name.add(reminder_planting_data.get(i).get(14));


                    }
                    reminderList = new ArrayList<>();
                    for(int i=0;i<seedling_vege_name.size();i++){
                        reminderList.add(new reminder_cardview_planting(seedling_vege_id.get(i), seedling_vege_image.get(i),seedling_vege_name.get(i), seedling_day.get(i).substring(0,10),
                                seedling_number.get(i), seedling_number_unit.get(i), seedling_checkornot.get(i), seedling_vendor.get(i), seedling_remark.get(i), seedling_preharvest.get(i),
                                seedling_pregrowing.get(i),seedling_preday_num.get(i),seedling_pregrowing_num.get(i), canopy_name.get(i)));
                    }

                    reminder_rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    reminder_rv.setHasFixedSize(true);
                    reminder_rv.setAdapter(new reminder_planting_fragment.reminder_first_layer_fragment_adapter(getActivity(), reminderList));


                }
            });

        }
    };





//    private Runnable getRemind_select_unit=new Runnable () {
//
//        public void run() {
//            reminder_unit_data=reminder_webservice.Select_remind_unit();
//            //請經紀人指派工作名稱 r，給工人做
//            Log.v("test","remind_unit_data:"+reminder_unit_data);
//            mUI_Handler.post(setRemind_select_unit);
//
//        }
//
//    };
//
//    private Runnable setRemind_select_unit=new Runnable () {
//
//        public void run() {
//
//            String[] amount_and_weight=reminder_unit_data.split("分隔");
//            Amount=amount_and_weight[0].split("%");
//
//            ArrayAdapter<String> AmountList = new ArrayAdapter<>(getContext(),
//                    R.layout.record_select_dropdown_item,
//                    Amount);
//
//            remind_amount_unit_sp.setAdapter(AmountList);
//            remind_amount_unit_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
//
//
//
//
//        }
//
//    };

    private Runnable Insert_and_select_planting_day_num=new Runnable () {

        public void run() {

            reminder_webservice.Insert_reminder_vegetable_planting_day(Vege,Tag1,record_canopy_sp.getSelectedItem().toString(),num_ed.getText().toString(),remind_amount_unit_sp.getSelectedItem().toString(),"666");


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


    private class reminder_first_layer_fragment_adapter extends RecyclerView.Adapter<reminder_first_layer_fragment_adapter.viewholder> {

        private Context mctx;
        private List<reminder_cardview_planting> reminderList;


        public reminder_first_layer_fragment_adapter(Context mctx, List<reminder_cardview_planting> reminderList) {
            this.mctx = mctx;
            this.reminderList = reminderList;
        }

        @Override
        public reminder_first_layer_fragment_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(mctx);
            View view = inflater.inflate(R.layout.reminder_cardview_planting, viewGroup, false);
            return new reminder_first_layer_fragment_adapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(final reminder_first_layer_fragment_adapter.viewholder holder, final int position) {
            final reminder_cardview_planting vege = reminderList.get(position);


            holder.vege.setText(String.valueOf(vege.getName()));
            Vege = String.valueOf(vege.getName());
            holder.canopy_name.setText(vege.getCanopy_name());
            int drawableResourceId = mctx.getResources().getIdentifier(vege.getVege_img(), "drawable", mctx.getPackageName());
            Glide.with(mctx)
                    .load(drawableResourceId)
                    .into(holder.vege_img);

            int drawableResourceId2 = mctx.getResources().getIdentifier(vege.getCheck_img(), "drawable", mctx.getPackageName());
            Glide.with(mctx)
                    .load(drawableResourceId2)
                    .into(holder.check_img);
            holder.tag1.setText(String.valueOf(vege.getTag1()));        //tag1是日期
            Tag1 = String.valueOf(vege.getTag1());
            holder.tag2.setText(String.valueOf(vege.getTag2()));        //tag2是有幾盤
            holder.unit.setText(vege.getUnit());                        //單位
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            //定植cardview點選+產生dialog
            holder.plus_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);//初始化BottomSheet
                    View root = LayoutInflater.from(getContext()).inflate(R.layout.reminder_planting_bottomsheetdialog, null);//連結的介面
                    bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                    ((View) root.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底

                    //bottomsheet cardview
                    //bottomsheet cardview
                    final RecyclerView planting_RV;
                    final List<reminder_planting_bottomsheetdialog_cardview> bottomsheetList;
                    planting_RV = root.findViewById(R.id.planting_RV);
                    bottomsheetList = new ArrayList<>();        //定植詳細棚架&數量 cardview




                    vege_name_tv = root.findViewById(R.id.vege_name_tv);
                    last_num = root.findViewById(R.id.last_num);
                    last_num_unit = root.findViewById(R.id.last_num_unit);
                    planting_confirm_tv = root.findViewById(R.id.btmsheet_confirm_tv);
                    date_tiet = root.findViewById(R.id.date_tiet);
                    greenhouse_tiet = root.findViewById(R.id.greenhouse_tiet);  //棚架位置
                    planting_num = root.findViewById(R.id.planting_num);        //定植數量
                    ImageView plus = root.findViewById(R.id.cardview_plus);     //加資料

                    vege_name_tv.setText(vege.getName());       //作物名
                    last_num.setText(vege.getTag2());           //剩下有幾盤未分配
                    last_num_unit.setText(vege.getUnit());      //單位

                    //輸入data那格
                    plus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //表示有輸入棚架 數量 日期 會自動新增cardview
                            if (!greenhouse_tiet.getText().toString().trim().equals("")          //判斷棚架是否有資料
                                    && !planting_num.getText().toString().trim().equals("")     //判斷數量是否有資料
                                    && !date_tiet.getText().toString().trim().equals(""))       //判斷日期是否有資料
                            {
                                if((Integer.parseInt(last_num.getText().toString())-Integer.parseInt(planting_num.getText().toString())) >= 0)
                                {
                                    //按下+直接進資料庫
                                    setReminder_planting(39,Integer.parseInt(vege.getId()), Integer.parseInt(planting_num.getText().toString()),
                                            date_tiet.getText().toString(),greenhouse_tiet.getText().toString());


                                    //加入cardview
                                    bottomsheetList.add(new reminder_planting_bottomsheetdialog_cardview(bottomsheetList.size(), greenhouse_tiet.getText().toString(),
                                                Integer.parseInt(planting_num.getText().toString()), date_tiet.getText().toString()));

                                    planting_RV.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                                    planting_RV.setHasFixedSize(true);
                                    planting_RV.setAdapter(new reminder_planting_bottomsheetdialog_Adapter(reminder_planting_fragment.this, canopy_area, canopy_name, bottomsheetList));


                                    int last_num_new = Integer.parseInt(last_num.getText().toString())-Integer.parseInt(planting_num.getText().toString());
                                    last_num.setText(String.valueOf(last_num_new));

                                    //清空
                                    greenhouse_tiet.setText("");
                                    planting_num.setText("");
                                }


                            } else {
                                Toast.makeText(getContext(), "棚架&數量&日期不得為空 !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    //把育苗日給c2
                    List<String> time_list = Arrays.asList(vege.getTag1().split("-"));
                    Log.v("date", time_list.get(0) + "  " + time_list.get(1) + "  " + time_list.get(2));
                    c2 = Calendar.getInstance();
                    c2.set(Integer.parseInt(time_list.get(0)), Integer.parseInt(time_list.get(1)) - 1, Integer.parseInt(time_list.get(2)));

                    todaydate = DateFormat.format("yyyy-MM-dd", c2.getTime());


                    date_tiet.setText(todaydate);
                    // 顯示dialog
                    bottomSheetDialog.show();


                    //看日曆
                    date_tiet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            DialogFragment datePicker = new DatePickerFragment(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH));
                            datePicker.setTargetFragment(reminder_planting_fragment.this, 0);
                            datePicker.show(getFragmentManager(), "date picker");
                        }

                    });

                    //選取棚架位置
                    greenhouse_tiet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                                @Override
                                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                    String vege = canopy_name.get(options1).get(options2);
                                    greenhouse_tiet.setText(vege);
                                    bottomSheetDialog.show(); //當pickerview按下確認會再開啟btmdialog，讓使用者繼續輸入資料
                                }
                            }).setTitleText("請選擇") // 選擇器標題
                                    .setContentTextSize(18)//設定滾輪文字大小
                                    .setTitleSize(18)
                                    .setDividerColor(getResources().getColor(R.color.Gainsboro))//設定分割線顏色
                                    .setSelectOptions(0, 1)//默認選中值
                                    .setBgColor(Color.WHITE)
                                    .setTitleBgColor(getResources().getColor(R.color.WhiteSmoke))
                                    .setTitleColor(Color.BLACK)
                                    .setCancelColor(getResources().getColor(R.color.Azure))
                                    .setSubmitColor(getResources().getColor(R.color.Azure))
                                    .setCancelText("取消")
                                    .setSubmitText("確定")
                                    .setTextColorCenter(getResources().getColor(R.color.Dimgray))
                                    .setBackgroundId(0x66000000) //設定外部遮罩顏色
                                    .build();

                            pvOptions.setPicker(canopy_area, canopy_name);
                            bottomSheetDialog.dismiss();//因為會擋到pickerview所以先關閉，當按下pickerview的確認就會再展開
                            pvOptions.show();
                        }
                    });

                    //按下確認
                    planting_confirm_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bottomSheetDialog.dismiss();
                            //刷新
                            mThreadHandler.post(getReminder_planting_data);
                        }
                    });

                }
            });

            holder.more_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
                    check_status[0] = reminder_webservice.reminder_planting_data_list_checkornot("39",vege.getId(),Check_img_change);
                    //請經紀人指派工作名稱 r，給工人做
                    mUI_Handler.post(setCheck_result);
                }

            };

            //打勾
            holder.check_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v("test","vege.getId():" +vege.getId());
                    mThreadHandler.post(getCheck_result);
                }
            });

        }

        @Override
        public int getItemCount() {
            return reminderList.size();
        }



        //把各棚架&數量丟到資料庫
        public void setReminder_planting(final int user, final int seedling_id, final int seedling_num, final String seedling_date, final String canopy)
        {
            Runnable getData= new Runnable() {
                @Override
                public void run() {
                    split_planting_status = reminder_webservice.split_planting_setting(user,seedling_id,seedling_num,seedling_date,canopy);
                    if(split_planting_status)
                    {
                        Toast.makeText(mctx,"輸入成功!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(mctx,"輸入失敗!",Toast.LENGTH_SHORT).show();
                    }
                }
            };

            mThreadHandler.post(getData);


        }


        //把各棚架&數量從資料庫抓出來
        public void getReminder_planting_List()
        {

        }


        class viewholder extends RecyclerView.ViewHolder {
            ImageView vege_img,check_img;
            ImageButton plus_imb,more_imb;
            TextView vege,tag1,tag2,unit;
            TextView canopy_name;


            public viewholder(@NonNull View itemView) {
                super(itemView);
                vege_img = itemView.findViewById(R.id.vege_img);
                plus_imb = itemView.findViewById(R.id.plus_imb);
                more_imb = itemView.findViewById(R.id.more_imb);
                vege = itemView.findViewById(R.id.vegename);
                tag1 = itemView.findViewById(R.id.tag1_tv);
                tag2 = itemView.findViewById(R.id.tag2_tv);
                unit = itemView.findViewById(R.id.unit);
                check_img = itemView.findViewById(R.id.check_img);
                canopy_name = itemView.findViewById(R.id.canopy);
            }


        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //移除工人上的工作
        if (mThreadHandler != null) {
            mThreadHandler.removeCallbacks(getReminder_planting_data);

        }
        //解聘工人 (關閉Thread)
        if (mThread != null) {
            mThread.quit();
        }
    }
}


