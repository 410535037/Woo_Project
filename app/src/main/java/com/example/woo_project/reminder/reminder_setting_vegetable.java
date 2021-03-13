package com.example.woo_project.reminder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.woo_project.R;
import com.example.woo_project.home.home;
import com.example.woo_project.record.record_webservice;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.loopj.android.http.AsyncHttpClient.log;

public class reminder_setting_vegetable extends Fragment implements DatePickerDialog.OnDateSetListener  {

    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    private OptionsPickerView pvOptions;
    View view;
    ViewGroup viewGroup;
    Button date_bt,estimate_bt,productivity_bt,confirm_bt;
    ImageView close_btmsheetdialog;
    TextView seedling_day_tv,planting_day_tv,harvest_day_tv,confirm_tv,confirm,cancel_tv,amount_tv,weight_tv;
    ListView lv;
    String day_of_harvest,day_of_planting,day_of_seedling,YorN,remind_unit_data,remind_productivity_data;
    TextInputEditText choose_vege_tiet,day_of_harvest_tiet,num_ed,kg_ed,num_ed2,kg_ed2,vendor_tiet;
    Calendar c;
    TextInputEditText days_of_growing_tiet,days_of_seedling_tiet,seedling_num,kg_tiet,remark_edit,num_edit,choose_variety_ed;
    TextInputLayout vege_til,variety_til,seedlimg_num_til,kg_til,vendor_til,remark_til,growimg_til,seedling_til;
    ExpandableListView exp_list_view;
    int myear,mday,mmonth;
    boolean numberFocus,kgFocus;
    int checkedItems;
    int unit_productivity,unit_productivity2;
    ArrayList<String> itemlist;
    ArrayAdapter<String> adapter;
    String[] Amount,Weight;
    private Spinner record_plant_kind_sp,reocrd_plant_name_sp,seedling_unit_sp,reminder_vendor_sp,record_canopy_sp,remind_amount_unit_sp,remind_weight_unit_sp,remind_amount_unit_sp2,remind_weight_unit_sp2;
    FragmentManager fragmentManager = getFragmentManager();
    private main_reminder getreminder = new main_reminder();
    public reminder_setting_vegetable(){ }
    boolean fg=true;
    private List<String> unit_number_list = new ArrayList<>();
    private List<String> vendor_list = new ArrayList<>();

    private ArrayList<String> mainArray = new ArrayList<>();//父層總陣列
    private ArrayList<String> itemName = new ArrayList<>();//父層標題
    private HashMap<String, ArrayList<String>> childArray = new HashMap<>();//子層陣列
    ExpandableListAdapter expandableListAdapter;
    Button up_year,down_year;
    TextView text;
    int vege_id;

    //確認是否有按日期推算
    boolean estimate_bt_status=false;

    private main_reminder  getMain_reminder= new main_reminder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.reminder_setting_vegetable,container,false);

        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

        vege_til=view.findViewById(R.id.vege_til);
        variety_til=view.findViewById(R.id.variety_til);
        seedlimg_num_til=view.findViewById(R.id.seedlimg_num_til);
        kg_til=view.findViewById(R.id.kg_til);
        vendor_til=view.findViewById(R.id.vendor_til);
        remark_til=view.findViewById(R.id.remark_til);
        growimg_til=view.findViewById(R.id.growimg_til);
        seedling_til=view.findViewById(R.id.seedling_til);
        seedling_day_tv=view.findViewById(R.id.seedling_day);
        planting_day_tv=view.findViewById(R.id.planting_day);
        harvest_day_tv=view.findViewById(R.id.harvest_day);
        days_of_growing_tiet=view.findViewById(R.id.days_of_growing_tiet);
        days_of_seedling_tiet=view.findViewById(R.id.days_of_raising_seedling_tiet);
        amount_tv=view.findViewById(R.id.amount_tv);
        weight_tv=view.findViewById(R.id.weight_unit_tv);
        choose_vege_tiet=view.findViewById(R.id.choose_vege_ed);
        remark_edit = view.findViewById(R.id.remark_edit);

        date_bt=view.findViewById(R.id.date_bt);
        day_of_harvest_tiet=view.findViewById(R.id.day_of_harvest_tiet);
        estimate_bt=view.findViewById(R.id.estimate_bt);
        seedling_num=view.findViewById(R.id.seedling_num);
        productivity_bt=view.findViewById(R.id.productivity_bt);
        kg_tiet=view.findViewById(R.id.kg_tiet);
        seedling_unit_sp = view.findViewById(R.id.seedling_unit_sp);
        vendor_tiet = view.findViewById(R.id.vendor_tiet);

        choose_variety_ed = view.findViewById(R.id.choose_variety_ed);

        day_of_harvest_tiet.setKeyListener(null);
        choose_vege_tiet.setKeyListener(null);

        mThreadHandler.post(getUnit_number_list);

        //顯示作物列表
        choose_vege_tiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mThreadHandler.post(getVege_list);
            }
        });

        //未完成
        choose_variety_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initOptionPicker_vege_variety();

            }
        });

        //顯示廠商列表
        vendor_tiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mThreadHandler.post(getVendor_list);
            }
        });

        //顯示日期的dialog
        day_of_harvest_tiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(reminder_setting_vegetable.this,0);
                datePicker.show(getActivity().getSupportFragmentManager(),"date picker");

            }
        });

        //未完成
        productivity_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mThreadHandler.post(getRemind_vege_productivity);
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);//初始化BottomSheet
                View root = LayoutInflater.from(getContext()).inflate(R.layout.reminder_vege_record_of_productivity,null);//連結的介面
                bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                ((View) root.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底

                up_year = root.findViewById(R.id.upyear);
                down_year = root.findViewById(R.id.downyear);
                text = root.findViewById(R.id.yeartv);
                exp_list_view = root.findViewById(R.id.exp_list_view);

                for(int i=0 ; i<1 ;i++) {
                    mainArray.add("上半年平均產量 : 1盤 80公斤");

                    ArrayList<String> datalist = new ArrayList<>();
                    for(int j=0 ;j<5 ;j++) {
                        datalist.add("2020/03/05~2020/05/04\n#A03 30盤740公斤 => 1盤28公斤");

                    }
                    childArray.put(mainArray.get(i), datalist);

                }
                expandableListAdapter = new ExpandableListAdapter(mainArray,childArray);
                exp_list_view.setAdapter(expandableListAdapter);

                bottomSheetDialog.show();

            }
        });

//        day_of_harvest_tiet.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                try {
//                    c.set(Calendar.MONTH,mmonth);
//                    c.set(Calendar.DAY_OF_MONTH,day);
//                    c.add(Calendar.DAY_OF_MONTH, -(Integer.valueOf(days_of_growing_tiet.getText().toString()))); //定植日=收成日-成長天數，但成長天數減到跨年份的話會出錯
//                    Log.v("test", "date:" + day);
//                    day_of_planting = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(c.getTime());
//
//                    c.add(Calendar.DAY_OF_MONTH, -(Integer.parseInt(days_of_seedling_tiet.getText().toString())));//育苗日=定植日-育苗天數，但育苗天數減到跨年份的話會出錯
//                    day_of_seedling = new SimpleDateFormat ("yyyy-MM-dd", Locale.getDefault()).format(c.getTime());
//
//                    seedling_day_tv.setText(day_of_seedling);
//                    planting_day_tv.setText(day_of_planting);
//
//                }
//                catch (Exception e){
//                    seedling_day_tv.setText("");
//                    planting_day_tv.setText("");
//
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
        //點擊預估算出育苗、定植日
        estimate_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(days_of_growing_tiet.getText().toString().equals("")||days_of_seedling_tiet.getText().toString().equals("")||day_of_harvest_tiet.getText().toString().equals("")){
                    Toast.makeText(getContext(),"*成長天數\n*育苗天數\n都要輸入喔!",Toast.LENGTH_LONG).show();
                }
                else {
                    estimate_bt_status=true;
                    Date_Calculate();
                }
            }
        });


        return view;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
        c = Calendar.getInstance();
        myear=year;
        mday=dayofmonth;
        mmonth=month;
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayofmonth);

        day_of_harvest =  new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(c.getTime());
        day_of_harvest_tiet.setText(day_of_harvest);
        harvest_day_tv.setText(day_of_harvest);

        if(!days_of_growing_tiet.getText().toString().equals("") && !days_of_seedling_tiet.getText().toString().equals("") && !day_of_harvest_tiet.getText().toString().equals("")){
            Date_Calculate();
        }

    }


    public boolean Confirm(){

        if(choose_vege_tiet.getText().toString().equals("")||harvest_day_tv.getText().toString().equals("")
                ||days_of_seedling_tiet.getText().toString().equals("")||days_of_growing_tiet.getText().toString().equals("")
                ||seedling_num.getText().toString().equals("") || seedling_num.getText().toString().equals("") )
        {
            Toast.makeText(getContext(),"#作物名稱\n#預計收成日\n成長天數\n育苗天數\n一定要填 !\n別忘了育苗數量要大於0哦 !",Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            if(estimate_bt_status)
            {
                mThreadHandler.post(setInsert_reminder_vegetable_setting);
                return true;
            }
            else
            {
                Toast.makeText(getContext(),"記得推算育苗日與定植日!",Toast.LENGTH_LONG).show();
                return false;
            }
        }

    }

    private void Date_Calculate(){
        c.set(Calendar.YEAR,myear);
        c.set(Calendar.MONTH,mmonth);
        c.set(Calendar.DAY_OF_MONTH,mday);
        c.add(Calendar.DAY_OF_MONTH, -(Integer.valueOf(days_of_growing_tiet.getText().toString()))); //定植日=收成日-成長天數
        Log.v("test", "date:" + mday);
        day_of_planting = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(c.getTime());

        c.add(Calendar.DAY_OF_MONTH, -(Integer.parseInt(days_of_seedling_tiet.getText().toString())));//育苗日=定植日-育苗天數
        day_of_seedling = new SimpleDateFormat ("yyyy-MM-dd", Locale.getDefault()).format(c.getTime());

        seedling_day_tv.setText(day_of_seedling);
        planting_day_tv.setText(day_of_planting);

    }

    private Runnable setInsert_reminder_vegetable_setting=new Runnable () {

        public void run() {
            if(vendor_tiet.getText().length()==0 || seedling_num.getText().length()==0 || remark_edit.getText().length()==0 || kg_tiet.getText().length()==0)
            {
                if(vendor_tiet.getText().length()==0)
                {
                    vendor_tiet.setText("無");
                }
                if(seedling_num.getText().length()==0)
                {
                    seedling_num.setText("0");

                }
                if(remark_edit.getText().length()==0)
                {
                    remark_edit.setText("無");

                }
                if(kg_tiet.getText().length()==0)
                {
                    kg_tiet.setText("0");
                }
                YorN = reminder_webservice.Insert_reminder_vegetable_setting(vege_id, choose_vege_tiet.getText().toString(), vendor_tiet.getText().toString(), remark_edit.getText().toString(), Integer.parseInt(seedling_num.getText().toString()), seedling_unit_sp.getSelectedItem().toString(), Integer.parseInt(kg_tiet.getText().toString()), seedling_day_tv.getText().toString(), planting_day_tv.getText().toString(), harvest_day_tv.getText().toString(), Integer.parseInt(days_of_seedling_tiet.getText().toString()), Integer.parseInt(days_of_growing_tiet.getText().toString()), null, Integer.parseInt(seedling_num.getText().toString()), false, false, false, "39");

            }
            else
            {

                YorN = reminder_webservice.Insert_reminder_vegetable_setting(vege_id, choose_vege_tiet.getText().toString(), vendor_tiet.getText().toString(), remark_edit.getText().toString(), Integer.parseInt(seedling_num.getText().toString()), seedling_unit_sp.getSelectedItem().toString(), Integer.parseInt(kg_tiet.getText().toString()), seedling_day_tv.getText().toString(), planting_day_tv.getText().toString(), harvest_day_tv.getText().toString(), Integer.parseInt(days_of_seedling_tiet.getText().toString()), Integer.parseInt(days_of_growing_tiet.getText().toString()), null, Integer.parseInt(seedling_num.getText().toString()), false, false, false, "39");
            }
            //請經紀人指派工作名稱 r，給工人做
            Log.v("test","YorN:"+YorN);
            mUI_Handler.post(getInsert_reminder_vegetable_setting);

        }

    };

    private Runnable getInsert_reminder_vegetable_setting=new Runnable () {

        public void run() {
            if(YorN.equals("YES")) {
//                Toast.makeText(getContext(), "輸入成功", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getActivity(), main_reminder.class);
//                startActivity(intent);



            }

        }

    };

    private Runnable getVendor_list=new Runnable () {

        public void run() {

            vendor_list=reminder_webservice.vendor_list();
            //請經紀人指派工作名稱 r，給工人做
            Log.v("test","data:"+vendor_list);
            mUI_Handler.post(setVendor_list);

        }

    };

    private Runnable setVendor_list=new Runnable () {

        public void run() {

            initOptionPicker_vendor();
            pvOptions.show();

        }

    };

    private Runnable getRemind_vege_productivity=new Runnable () {

        public void run() {
            remind_productivity_data=reminder_webservice.Select_remind_productivity(reocrd_plant_name_sp.getSelectedItem().toString());
            //請經紀人指派工作名稱 r，給工人做
            Log.v("test","remind_productivity_data:"+remind_productivity_data);
            mUI_Handler.post(setRemind_vege_productivity);

        }

    };
    private Runnable setRemind_vege_productivity=new Runnable () {

        public void run() {
            if(remind_productivity_data.equals("查無資料")) {

                //then we will inflate the custom alert dialog xml that we created
                final View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.reminder_vege_no_record_of_productivity, viewGroup, false);

                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                //setting the view of the builder to our custom view that we already inflated
                builder.setView(dialogView);

                //finally creating the alert dialog and displaying it
                final AlertDialog alertDialog = builder.create();

                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setContentView(R.layout.reminder_vege_no_record_of_productivity);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

            }
            else {
                mainArray.add("上半年平均產量 : 1盤 80公斤");
                mainArray.add("下半年平均產量 : 1盤 95公斤");
                ArrayList<String> datalist = new ArrayList<>();
                datalist.add("2020/03/20~2020/05/03 A1 30盤 740公斤 1盤 28公斤");
                childArray.put(mainArray.get(0),datalist);
                expandableListAdapter = new ExpandableListAdapter(mainArray,childArray);
                exp_list_view.setAdapter(expandableListAdapter);


//                productivity_bt.setVisibility(View.INVISIBLE);
//                final String[] productivity_data = remind_productivity_data.split("%");
//                unit_productivity=Integer.parseInt(String.valueOf(productivity_data[2]))/Integer.parseInt(String.valueOf(productivity_data[0]));
//                seedling_num.setText(productivity_data[0]);
//                amount_tv.setText(productivity_data[1]);
//                kg_tiet.setText(productivity_data[2]);
//                weight_tv.setText(productivity_data[3]);
//                seedling_num.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                        if (numberFocus) {
//                            try {
//                                int s = Integer.parseInt(String.valueOf(charSequence));
//                                kg_tiet.setText(String.valueOf(s * unit_productivity));
//                            } catch (Exception e) {
//                                kg_tiet.setText("");
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//
//                    }
//                });
//
//                kg_tiet.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                        if (kgFocus) {
//                            try {
//                                int s = Integer.parseInt(String.valueOf(charSequence));
//                                int c = s / unit_productivity;
//                                seedling_num.setText(String.valueOf(c));
//
//                            } catch (Exception e) {
//                                seedling_num.setText("");
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//
//                    }
//                });
//                seedling_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View view, boolean b) {
//                        numberFocus = b;
//                    }
//                });
//
//                kg_tiet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View view, boolean b) {
//                        kgFocus = b;
//                    }
//                });
            }
        }

    };

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

            seedling_unit_sp.setAdapter(UnitNumberList);
            seedling_unit_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    amount_tv.setText(seedling_unit_sp.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });



        }

    };

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

                choose_vege_tiet.setText(vege);
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

    private void initOptionPicker_vendor() {

        pvOptions = new OptionsPickerBuilder(view.getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String vendor = vendor_list.get(options1);
                vendor_tiet.setText(vendor);
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

        pvOptions.setPicker(vendor_list);
    }

    private List<String> vege_variety_list = new ArrayList<>();

    private void initOptionPicker_vege_variety() {
        vege_variety_list.add("花蓮一號");
        vege_variety_list.add("花蓮二號");
        vege_variety_list.add("花蓮三號");
        vege_variety_list.add("無");

        pvOptions = new OptionsPickerBuilder(view.getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String variety = vege_variety_list.get(options1);
                choose_variety_ed.setText(variety);
                vege_variety_list = new ArrayList<>();
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

        pvOptions.setPicker(vege_variety_list);
        pvOptions.show();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        //移除工人上的工作
        if (mThreadHandler != null) {
            mThreadHandler.removeCallbacks(setInsert_reminder_vegetable_setting);
            mThreadHandler.removeCallbacks(setVege_list);
            mThreadHandler.removeCallbacks(setVendor_list);
            mThreadHandler.removeCallbacks(setUnit_number_list);
        }
        //解聘工人 (關閉Thread)
        if (mThread != null) {
            mThread.quit();
        }
    }

}
