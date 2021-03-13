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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private TextView vege_name_tv,planting_confirm_tv;
    List<reminder_cardview> reminderList;
    RecyclerView reminder_rv;
    String reminder_vegetable_data,reminder_unit_data,Vege,Tag1,currentDateString,todaydate;
    ArrayList<Integer> counter = new ArrayList<>();
    Spinner record_canopy_area_sp,record_canopy_sp,remind_amount_unit_sp;
    TextInputEditText num_ed,date_tiet,greenhouse_tiet,planting_num;
    String[] Amount;
    Calendar c,c2;
    private List<List<String>> reminder_planting_data = new ArrayList<>();
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
            reminder_planting_data=reminder_webservice.reminder_planting_data_list("39");
            //請經紀人指派工作名稱 r，給工人做
            Log.v("test","data:"+reminder_planting_data);
            mUI_Handler.post(setReminder_planting_data);

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


                    }
                    for(int i=0;i<seedling_vege_name.size();i++){
                        reminderList.add(new reminder_cardview(seedling_vege_id.get(i),seedling_vege_image.get(i), seedling_vege_name.get(i), "預計定植日 :  " + seedling_day.get(i).substring(0,10), "#"+seedling_number.get(i)+seedling_number_unit.get(i), seedling_checkornot.get(i)));
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


//    private void initOptionPicker_canopy_list() {
//
//        pvOptions = new OptionsPickerBuilder(view.getContext(), new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                String vege = canopy_name.get(options1).get(options2);
//                canopy_area_tiet.setText(vege);
//            }
//        }).setTitleText("請選擇") // 選擇器標題
//                .setContentTextSize(18)//設定滾輪文字大小
//                .setTitleSize(18)
//                .setDividerColor(getResources().getColor(R.color.Gainsboro))//設定分割線顏色
//                .setSelectOptions(0, 1)//默認選中值
//                .setBgColor(Color.WHITE)
//                .setTitleBgColor(getResources().getColor(R.color.WhiteSmoke))
//                .setTitleColor(Color.BLACK)
//                .setCancelColor(getResources().getColor(R.color.Azure))
//                .setSubmitColor(getResources().getColor(R.color.Azure))
//                .setCancelText("取消")
//                .setSubmitText("確定")
//                .setTextColorCenter(getResources().getColor(R.color.Dimgray))
//                .setBackgroundId(0x66000000) //設定外部遮罩顏色
//                .build();
//
//        pvOptions.setPicker(canopy_area, canopy_name);
//    }

    private class reminder_first_layer_fragment_adapter extends RecyclerView.Adapter<reminder_first_layer_fragment_adapter.viewholder>  {

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
                    greenhouse_tiet = root.findViewById(R.id.greenhouse_tiet);
                    planting_num = root.findViewById(R.id.planting_num);
                    //顯示今天的日期
                    c2 = Calendar.getInstance();
                    todaydate =  new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(c2.getTime());
                    date_tiet.setText(todaydate);

                    vege_name_tv.setText(vege.getName());

                    bottomSheetDialog.show();


                    date_tiet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            DialogFragment datePicker = new DatePickerFragment();
                            datePicker.setTargetFragment(reminder_planting_fragment.this,0);
                            datePicker.show(getFragmentManager(),"date picker");

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

                            Runnable Reminder_insert_greenhouse_and_planting_num_data=new Runnable () {

                                public void run() {
                                    String YorN="";  //確認有無傳到資料庫

                                    if(date_tiet.getText().toString().equals("") || greenhouse_tiet.getText().toString().equals("") || planting_num.getText().toString().equals("")){
                                        Toast.makeText(getContext(),"#定植日期\n#棚架位置\n定植數量\n一定要填 !",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        //YorN=reminder_webservice.Insert_greenhouse_and_planting_num(vege.getName(), variety_of_vege, seedling_num, seedling_unit, vendor, reminder_text, harvest_num, seedling_day, planting_day, harvest_day, days_of_seedling, days_of_planting, real_seedling_day, greenhouse, do_seedling, do_planting, do_harvest, "39");
                                        //請經紀人指派工作名稱 r，給工人做
                                        Log.v("test", "data:" + YorN);
                                    }

                                }

                            };
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
            TextView vege,tag1,tag2;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                vege_img = itemView.findViewById(R.id.vege_img);
                plus_imb = itemView.findViewById(R.id.plus_imb);
                more_imb = itemView.findViewById(R.id.more_imb);
                vege = itemView.findViewById(R.id.vegename);
                tag1 = itemView.findViewById(R.id.tag1_tv);
                tag2 = itemView.findViewById(R.id.tag2_tv);
                check_img = itemView.findViewById(R.id.check_img);

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


