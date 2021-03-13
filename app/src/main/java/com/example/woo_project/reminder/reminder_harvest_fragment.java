package com.example.woo_project.reminder;
//提醒主頁--收成的fragment
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class reminder_harvest_fragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    private TextView vege_name_tv,edit_tv,delete_tv,planting_confirm_tv;
    private TextInputEditText date_tiet,canopy_area_tiet;
    private Calendar c,today_cal;
    private OptionsPickerView pvOptions;
    View view;
    String Vege,Tag1,currentDateString,todaydate;
    List<reminder_cardview> reminderList;
    RecyclerView reminder_rv;
    String reminder_vegetable_data;
    private List<List<String>> reminder_harvest_data = new ArrayList<>();
    private List<List<String>> reminder_today_seedling_data = new ArrayList<>();

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

        date_tiet.setText(currentDateString);
    }

    //去資料庫抓資料---預設會顯示全部收成CardviewList，點擊Spinner可以切換全部、今日、這週、下週、自訂
    Runnable getReminder_harvest_data=new Runnable () {

        public void run() {


            switch (main_reminder.time_range_sp.getSelectedItemPosition())
            {
                case 0:
                    //顯示"全部"未收成的作物CardviewList(包含過期未收成以及未來要收成的)
                    reminder_harvest_data = reminder_webservice.reminder_seedling_data_list("39");
                    mUI_Handler.post(setReminder_harvest_data);
                    break;
                case 1:
                    //顯示"今日"要收成的作物CardviewList
                    reminder_harvest_data = reminder_webservice.reminder_today_seedling_data_list("39");
                    mUI_Handler.post(setReminder_harvest_data);
                    break;
                case 2:
                    //顯示"這週"要收成的作物CardviewList
                    reminder_harvest_data = reminder_webservice.reminder_thisweek_seedling_data_list("39");
                    mUI_Handler.post(setReminder_harvest_data);
                    break;
                case 3:
                    //顯示"下週"要收成的作物CardviewList
                    reminder_harvest_data = reminder_webservice.reminder_thisweek_seedling_data_list("39");
                    mUI_Handler.post(setReminder_harvest_data);
                    break;
                case 4:
                    //顯示"自訂"區間的作物CardviewList
                    reminder_harvest_data = reminder_webservice.reminder_thisweek_seedling_data_list("39");
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
                    //png_list
                    Log.v("test","reminder_harvest_data的長度: "+reminder_harvest_data.size());
                    for(int i=0;i<reminder_harvest_data.size();i++) {

                        harvest_vege_id.add(reminder_harvest_data.get(i).get(0));
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

                    }

                    reminderList = new ArrayList<>();

                    for(int i=0;i<harvest_vege_name.size();i++) {
                        reminderList.add(new reminder_cardview(harvest_vege_id.get(i), harvest_vege_image.get(i), harvest_vege_name.get(i), harvest_day.get(i).substring(0, 10),
                                harvest_number.get(i), harvest_number_unit.get(i), harvest_checkornot.get(i), harvest_vendor.get(i), harvest_remark.get(i), harvest_preharvest.get(i),
                                harvest_pregrowing.get(i), harvest_preday_num.get(i), harvest_pregrowing_num.get(i)));

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
        private List<reminder_cardview> reminderList;
        public reminder_first_layer_fragment_adapter(Context mctx, List<reminder_cardview> reminderList) {
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
        public void onBindViewHolder(final reminder_harvest_fragment.reminder_first_layer_fragment_adapter.viewholder holder, final int position) {
            final reminder_cardview vege=reminderList.get(position);

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
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.plus_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);//初始化BottomSheet
                    View root = LayoutInflater.from(getContext()).inflate(R.layout.reminder_planting_bottomsheetdialog,null);//連結的介面
                    bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                    ((View) root.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底

                    vege_name_tv = root.findViewById(R.id.vege_name_tv);
                    planting_confirm_tv = root.findViewById(R.id.btmsheet_confirm_tv);
                    date_tiet = root.findViewById(R.id.date_tiet);
                    canopy_area_tiet = root.findViewById(R.id.greenhouse_tiet);

                    vege_name_tv.setText(vege.getName());

                    today_cal = Calendar.getInstance();
                    todaydate =  new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(today_cal.getTime());
                    date_tiet.setText(todaydate);

                    bottomSheetDialog.show();


                    date_tiet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            DialogFragment datePicker = new DatePickerFragment();
                            datePicker.setTargetFragment(reminder_harvest_fragment.this,0);
                            datePicker.show(getFragmentManager(),"date picker");

                        }

                    });
                    canopy_area_tiet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                                @Override
                                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                    String vege = canopy_name.get(options1).get(options2);
                                    canopy_area_tiet.setText(vege);
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

                    planting_confirm_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }
            });

            holder.more_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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

            public viewholder(View itemView) {
                super(itemView);
                vege_img = itemView.findViewById(R.id.vege_img);
                plus_imb = itemView.findViewById(R.id.plus_imb);
                more_imb = itemView.findViewById(R.id.more_imb);
                vege = itemView.findViewById(R.id.vegename);
                tag1 = itemView.findViewById(R.id.tag1_tv);
                tag2 = itemView.findViewById(R.id.tag2_tv);
                unit = itemView.findViewById(R.id.unit);
                check_img = itemView.findViewById(R.id.check_img);

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
