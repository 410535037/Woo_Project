package com.example.woo_project.reminder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.woo_project.R;
import com.example.woo_project.record.record_webservice;
import com.example.woo_project.remind.farm_record;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class reminder_setting_other_things extends Fragment implements DatePickerDialog.OnDateSetListener {

    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    View view;
    TextInputEditText date_tiet, greenhouse_tiet, vegename_tiet, whichworking_tiet, remark_tiet;
    private OptionsPickerView pvOptions;
    int vege_id;
    Calendar c;
    String today_date;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    boolean[] checkedItems;
    String[] listItems;

    public reminder_setting_other_things(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.reminder_setting_other_things,container,false);

        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

        date_tiet = view.findViewById(R.id.date_tiet);
        greenhouse_tiet = view.findViewById(R.id.greenhouse_tiet);
        vegename_tiet = view.findViewById(R.id.vegename_tiet);
        whichworking_tiet = view.findViewById(R.id.whichworking_tiet);
        remark_tiet = view.findViewById(R.id.remark_tiet);
        vegename_tiet.setKeyListener(null);

        //顯示作物列表
        vegename_tiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mThreadHandler.post(getVege_list);
            }
        });

        //顯示日期的dialog
        date_tiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(reminder_setting_other_things.this,0);
                datePicker.show(getActivity().getSupportFragmentManager(),"date picker");

            }
        });


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

        mThreadHandler.post(getRemind_select_canopy);

        //選取棚架位置
        greenhouse_tiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        String vege = canopy_name.get(options1).get(options2);
                        greenhouse_tiet.setText(vege);

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
                pvOptions.show();

            }
        });

        ///把活動放在Dialog List裡
        listItems = getResources().getStringArray(R.array.calendar_action_list);
        checkedItems = new boolean[listItems.length];


        whichworking_tiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                mBuilder.setTitle("請選擇當日活動");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            if(!mUserItems.contains(which)){
                                mUserItems.add(which);
                            }
                        }
                        else if(mUserItems.contains(which)){
                            mUserItems.remove(mUserItems.indexOf(which));
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item="";
                        for(int i = 0; i< mUserItems.size(); i++){
                            item = item + listItems[mUserItems.get(i)];
                            if(i != mUserItems.size() -1){
                                item = item + ",";
                            }
                        }
                        whichworking_tiet.setText(item);
                    }
                });
                mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuilder.setNeutralButton("清除全部", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0;i < checkedItems.length; i++){
                            checkedItems[i] = false;
                            mUserItems.clear();
                            whichworking_tiet.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }

    List<List<String>> record_select_list_plant = new ArrayList<>();
    List<List<String>> plant_name = new ArrayList<>();
    List<String> plant_name_split = new ArrayList<>();
    List<String> plant_kind = new ArrayList<>();

    List<List<String>> all_vege_id = new ArrayList<>();

    Runnable getVege_list = new Runnable() {
        @Override
        public void run() {
            record_select_list_plant = record_webservice.record_select_list_plant();
            all_vege_id = reminder_webservice.reminder_vege_id();
            mUI_Handler.post(setVege_list);
        }
    };


    Runnable setVege_list = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {

                    //作物清單
                    for(int i = 0 ; i <record_select_list_plant.size();i++)
                    {
                        plant_kind.add(record_select_list_plant.get(i).get(0));
                        Log.v("test","record_select_list_plant:  "+record_select_list_plant.get(i).get(0));
                        Log.v("test","record_select_list_plant1:  "+record_select_list_plant.get(i));

                        for(int j=1;j<record_select_list_plant.get(i).size();j++){

                            plant_name_split.add(record_select_list_plant.get(i).get(j));

                        }
                        plant_name.add(plant_name_split);
                        plant_name_split = new ArrayList<>();

                    }
                    initOptionPicker_vegename();
                    pvOptions.show();
                    plant_kind = new ArrayList<>();

                }
            });

        }
    };

    private void initOptionPicker_vegename() {

        pvOptions = new OptionsPickerBuilder(view.getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String vege = record_select_list_plant.get(options1).get(options2+1);
                vege_id = Integer.valueOf(all_vege_id.get(options1).get(options2+1));
                Log.v("test","vege:  "+record_select_list_plant.get(options1).get(options2+1));
                Log.v("test","vegeid:  "+vege_id);

                vegename_tiet.setText(vege);
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

        pvOptions.setPicker(plant_kind, plant_name);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayofmonth) {
        c = Calendar.getInstance();

        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayofmonth);

        today_date=  new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(c.getTime());
        date_tiet.setText(today_date);



    }

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
}
