package com.example.woo_project.shipping;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.example.woo_project.R;
import com.example.woo_project.reminder.DatePickerFragment;
import com.example.woo_project.reminder.reminder_seedling_fragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class shipping_stock_sum_Adapter extends RecyclerView.Adapter<shipping_stock_sum_Adapter.viewholder> implements DatePickerDialog.OnDateSetListener  {

    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    //庫存某一個cardview的詳細資訊
    List<List<String>> vege_timeline_list_content = new ArrayList<>();
    RecyclerView vege_timeline_data_recyclerview;


    //庫存某一個cardview的canopy list
    List<String> vege_timeline_list_canopy = new ArrayList<>();
    Spinner shipping_stock_spinner; //上面的spinner
    String canopy_selected; //選中的canopy

    private Context mctx;

    private List<shipping_stock_sum_cardview> shipping_stock_List;
    private Fragment fragment;
    private List<String> vendor_list;
    private OptionsPickerView pvOptions;
    private TextInputEditText vendor_input;

    FragmentManager fragmentManager;
    Calendar calendar;
    TextInputEditText out_date;
    DatePickerDialog  datePicker;

        public shipping_stock_sum_Adapter(Fragment fragment, Context mctx, List<shipping_stock_sum_cardview> shipping_stock_List, List<String> vendor_list, FragmentManager fragmentManager) {
            this.mctx = mctx;
            this.shipping_stock_List = shipping_stock_List;
            this.fragment = fragment;
            this.vendor_list = vendor_list;
            this.fragmentManager = fragmentManager;
        }

        @Override
        public shipping_stock_sum_Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mctx);
            View view=inflater.inflate(R.layout.shipping_stock_cardview,viewGroup,false);

            //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
            mThread = new HandlerThread("");
            //讓Worker待命，等待其工作 (開啟Thread)
            mThread.start();
            //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
            mThreadHandler=new Handler(mThread.getLooper());

            return new shipping_stock_sum_Adapter.viewholder(view);
        }



        @Override
        public void onBindViewHolder(final shipping_stock_sum_Adapter.viewholder holder, final int position) {
            final shipping_stock_sum_cardview vege=shipping_stock_List.get(position);
            holder.vege_name.setText(vege.getVege_name());
            int drawableResourceId = mctx.getResources().getIdentifier(vege.getVege_img(), "drawable", mctx.getPackageName());
            Glide.with(mctx)
                    .load(drawableResourceId)
                    .into(holder.vege_img);

            holder.vendor.setText(vege.getVendor());
            holder.harvest_num.setText(String.valueOf(vege.getHarvest_num()));



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            //庫存詳細資訊
            holder.more_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final BottomSheetDialog more_bottomSheetDialog = new BottomSheetDialog(mctx, R.style.BottomSheetDialogTheme);//初始化BottomSheet
                    View root = LayoutInflater.from(mctx).inflate(R.layout.shipping_vege_timeline_bottomsheetdialog, null);//連結的介面
                    more_bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                    ((View) root.getParent()).setBackgroundColor(mctx.getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底

                    //作物名稱
                    TextView vege_name_tv = root.findViewById(R.id.vege_name_tv);
                    vege_name_tv.setText(vege.getVege_name());

                    //關閉
                    TextView btmsheet_cancel_tv = root.findViewById(R.id.btmsheet_cancel_tv);
                    btmsheet_cancel_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            more_bottomSheetDialog.dismiss();
                        }
                    });

                    // 庫存某一cardview有哪些canopy
                    //取得cardview canopy list
                    final Runnable getVege_timeline_canopy=new Runnable () {

                        public void run() {
                            ArrayAdapter<CharSequence> adapter =
                                    new ArrayAdapter(mctx,                          //對應的Context
                                            R.layout.shipping_vege_timeline_bottomsheetdialog_spinner,   //預設Spinner未展開時的View(預設及選取後樣式)
                                            vege_timeline_list_canopy);             //資料選項內容
                            //下拉呈現畫面
                            adapter.setDropDownViewResource(R.layout.shipping_vege_timeline_bottomsheetdialog_spinner_item);
                            shipping_stock_spinner.setBackgroundColor(0x0); //取消右邊箭頭
                            shipping_stock_spinner.setDropDownVerticalOffset(dip2px(mctx,20)); //往下
                            shipping_stock_spinner.setAdapter(adapter);

                        }

                    };

                    final Runnable setVege_timeline_canopy=new Runnable () {
                        public void run() {
                            //請經紀人指派工作名稱 r，給工人做
                            vege_timeline_list_canopy = shipping_webservice.Inventory_canopy_list(39, vege.getVege_name(), vege.getVege_img(),vege.getVendor());
                            mUI_Handler.post(getVege_timeline_canopy);
                        }

                    };

                    shipping_stock_spinner = root.findViewById(R.id.greenhouse_sp);
                    mThreadHandler.post(setVege_timeline_canopy);

                    //育苗定植收成的詳細資訊
                    vege_timeline_data_recyclerview = root.findViewById(R.id.vege_timeline_data_recyclerview);
                    final List<shipping_stock_vege_timeline_cardview> vege_timeline_list = new ArrayList<>();

                    final Runnable getVege_timeline=new Runnable () {

                        public void run() {


                            List<String> more_seedling_date = new ArrayList<>();    //實際育苗日
                            List<Integer> more_seedling_days = new ArrayList<>();    //實際育苗天數
                            List<String> more_seedling_num = new ArrayList<>();     //育苗數量
                            List<String> more_remarks = new ArrayList<>();          //備註

                            List<String> more_planting_date = new ArrayList<>();    //實際定植日
                            List<Integer> more_planting_days = new ArrayList<>();    //實際成長天數
                            List<String> more_planting_num = new ArrayList<>();     //定植數量

                            List<String> more_harvest_date = new ArrayList<>();     //實際收成日
                            List<String> more_harvest_weight = new ArrayList<>();   //實際收成重量


                            for(int i=0;i<vege_timeline_list_content.size();i++)
                            {
                                more_seedling_date.add(vege_timeline_list_content.get(i).get(0));
                                more_seedling_days.add(Integer.parseInt(vege_timeline_list_content.get(i).get(1)));
                                more_seedling_num.add(vege_timeline_list_content.get(i).get(2));
                                more_remarks.add(vege_timeline_list_content.get(i).get(3));

                                more_planting_date.add(vege_timeline_list_content.get(i).get(4));
                                more_planting_days.add(Integer.parseInt(vege_timeline_list_content.get(i).get(5)));
                                more_planting_num.add(vege_timeline_list_content.get(i).get(6));

                                more_harvest_date.add(vege_timeline_list_content.get(i).get(7));
                                more_harvest_weight.add(vege_timeline_list_content.get(i).get(8));
                            }

                            //如果只有一個育苗or定植or收成就顯示一個日期，否則顯示第一個和最後一個日期
                            if(more_seedling_date.get(0).equals(more_seedling_date.get(more_seedling_date.size() - 1)))
                            {
                                vege_timeline_list.add(new shipping_stock_vege_timeline_cardview("育苗",more_seedling_date.get(0),"no_end_date", more_seedling_days.get(0)));
                            }
                            else
                            {
                                vege_timeline_list.add(new shipping_stock_vege_timeline_cardview("育苗",more_seedling_date.get(0),more_seedling_date.get(more_seedling_date.size()-1),
                                        Collections.max(more_seedling_days)));
                            }
                            if(more_planting_date.get(0).equals(more_planting_date.get(more_seedling_date.size() - 1)))
                            {
                                vege_timeline_list.add(new shipping_stock_vege_timeline_cardview("定植",more_planting_date.get(0),"no_end_date",more_planting_days.get(0)));
                            }
                            else
                            {
                                vege_timeline_list.add(new shipping_stock_vege_timeline_cardview("定植",more_planting_date.get(0),more_planting_date.get(more_planting_date.size()-1),
                                        Collections.max(more_planting_days)));
                            }
                            if(more_harvest_date.get(0).equals(more_harvest_date.get(more_seedling_date.size() - 1)))
                            {
                                vege_timeline_list.add(new shipping_stock_vege_timeline_cardview("收成",more_harvest_date.get(0),"no_end_date",0));
                            }
                            else
                            {
                                vege_timeline_list.add(new shipping_stock_vege_timeline_cardview("收成",more_harvest_date.get(0),more_harvest_date.get(more_harvest_date.size()-1),0));
                            }

                            vege_timeline_data_recyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                            vege_timeline_data_recyclerview.setHasFixedSize(true);
                            vege_timeline_data_recyclerview.setAdapter(new shipping_stock_vege_timeline_Adapter(mctx,vege_timeline_list,
                                    more_seedling_date,more_seedling_num,more_remarks,
                                    more_planting_date,more_planting_num,
                                    more_harvest_date,more_harvest_weight));

                        }

                    };

                    //取得資料
                    final Runnable setVege_timeline=new Runnable () {

                        public void run() {
                            vege_timeline_list_content = shipping_webservice.Inventory(39, vege.getVege_name(), vege.getVege_img(),vege.getVendor(),canopy_selected);
                            mUI_Handler.post(getVege_timeline);
                        }

                    };

                    shipping_stock_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            //選中某canopy就call websevice取得資料
                            canopy_selected = adapterView.getSelectedItem().toString();
                            mThreadHandler.post(setVege_timeline);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    // 顯示dialog
                    more_bottomSheetDialog.show();


                }
            });

            holder.go_shipping_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mctx, R.style.BottomSheetDialogTheme);//初始化BottomSheet
                    View root = LayoutInflater.from(mctx).inflate(R.layout.shipping_stock_bottomsheetdialog, null);//連結的介面
                    bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                    ((View) root.getParent()).setBackgroundColor(mctx.getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底


                    final TextInputEditText out_weight,out_price;
                    final boolean[] ship_status = new boolean[1];
                    TextView vege_name,stock_num;

                    vege_name = root.findViewById(R.id.vege_name_tv);
                    stock_num = root.findViewById(R.id.harvest_num_tv);
                    out_date = root.findViewById(R.id.shipping_date);
                    vendor_input = root.findViewById(R.id.shipping_vendor);
                    out_weight = root.findViewById(R.id.shipping_num);
                    out_price = root.findViewById(R.id.shipping_unit_of_price);


                    vege_name.setText(vege.getVege_name());
                    stock_num.setText(String.valueOf(vege.getHarvest_num())+" 公斤");

                    //抓到今天
                    calendar = Calendar.getInstance();
                    CharSequence todaydate = DateFormat.format("yyyy-MM-dd", calendar.getTime());

                    out_date.setText(todaydate);
                    //看日曆
                    out_date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            datePicker = new DatePickerDialog (mctx,null,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                            datePicker.setCancelable(true);
                            datePicker.setCanceledOnTouchOutside(true);
                            datePicker.setButton(DialogInterface.BUTTON_POSITIVE, "確認",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //確定的邏輯程式碼在監聽中實現
                                            DatePicker picker = datePicker.getDatePicker();
                                            int year = picker.getYear();
                                            int monthOfYear = picker.getMonth()+1;
                                            int dayOfMonth = picker.getDayOfMonth();
                                            out_date.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                                        }
                                    });
                            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //取消什麼也不用做
                                        }
                                    });
                            datePicker.show();

                        }
                    });



                    vendor_input.setText(vege.getVendor());
                    out_weight.setText(String.valueOf(vege.getHarvest_num()));
                    out_price.setText("");

                    //選擇廠商
                    ImageView select_vendor = root.findViewById(R.id.select_vendor);
                    select_vendor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            pvOptions = new OptionsPickerBuilder(mctx, new OnOptionsSelectListener() {
                                @Override
                                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                    String vendor = vendor_list.get(options1);
                                    vendor_input.setText(vendor);
                                }
                            }).setTitleText("請選擇") // 選擇器標題
                                    .setContentTextSize(18)//設定滾輪文字大小
                                    .setTitleSize(18)
                                    .setDividerColor(mctx.getResources().getColor(R.color.Gainsboro))//設定分割線顏色
                                    .setSelectOptions(0, 1)//默認選中值
                                    .setBgColor(Color.WHITE)
                                    .setTitleBgColor(mctx.getResources().getColor(R.color.WhiteSmoke))
                                    .setTitleColor(Color.BLACK)
                                    .setCancelColor(mctx.getResources().getColor(R.color.Azure))
                                    .setSubmitColor(mctx.getResources().getColor(R.color.Azure))
                                    .setCancelText("取消")

                                    .setSubmitText("確定")
                                    .setTextColorCenter(mctx.getResources().getColor(R.color.Dimgray))
                                    .setBackgroundId(0x66000000) //設定外部遮罩顏色
                                    .isDialog(true)//改成dialog就能顯示在最上面
                                    .build();

                            pvOptions.setPicker(vendor_list);
                            pvOptions.show();

                           // Toast.makeText(mctx,"成功",Toast.LENGTH_SHORT).show();
                        }
                    });

                    //賣出結果
                    final String[] go_ship_result = new String[1];

                    //針對輸入賣出成功於否做應對
                    final Runnable getGo_ship=new Runnable () {

                        public void run() {

                            if(go_ship_result[0].equals("Yes"))
                            {
                                Toast.makeText(mctx,"輸入成功 !", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(mctx,"輸入失敗，請再試一次 !", Toast.LENGTH_SHORT).show();
                            }
                            bottomSheetDialog.dismiss();

                        }

                    };

                    //輸入賣出
                    final Runnable setGo_ship=new Runnable () {

                        public void run() {
                       //     vendor_list=reminder_webservice.vendor_list();
                            //請經紀人指派工作名稱 r，給工人做
                            go_ship_result[0] = shipping_webservice.Go_Ship(39, out_date.getText().toString(),vendor_input.getText().toString(),vege.getVendor(), out_price.getText().toString(),
                                    ship_status[0], vege.getVege_name(), vege.getVege_img());
                            Log.v("test","setGo_ship YES!");
                            mUI_Handler.post(getGo_ship);


                        }

                    };





                    Button go_wait_ship_bt = root.findViewById(R.id.go_wait_ship_bt);
                    Button go_ship_bt = root.findViewById(R.id.go_ship_bt);

                    //待出貨
                    go_wait_ship_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ship_status[0] =false;
                            mThreadHandler.post(setGo_ship);
                        }
                    });

                    //直接出貨
                    go_ship_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ship_status[0] =true;
                            mThreadHandler.post(setGo_ship);
                        }
                    });

                    //取消
                    TextView cancel_bt = root.findViewById(R.id.btmsheet_confirm_tv);
                    cancel_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetDialog.dismiss();
                        }
                    });

                    // 顯示dialog
                    bottomSheetDialog.show();

                }
            });

        }



    @Override
        public int getItemCount() {
            return shipping_stock_List.size();
        }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayofmonth);

        String currentDateString =  new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());

        out_date.setText(currentDateString);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    class viewholder extends RecyclerView.ViewHolder {
            ImageView vege_img;
            TextView vege_name,vendor,harvest_num;
            ImageButton go_shipping_imb,more_imb;
            RecyclerView secondrecyclerview;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                vege_img = (ImageView) itemView.findViewById(R.id.vege_img);
                vege_name = (TextView) itemView.findViewById(R.id.vege_name);
                vendor = (TextView) itemView.findViewById(R.id.vendor_tv);
                harvest_num = (TextView) itemView.findViewById(R.id.harvest_num_tv);

                go_shipping_imb = itemView.findViewById(R.id.go_shipped_or_unshipped_imb);
                more_imb = itemView.findViewById(R.id.more_imb);
                secondrecyclerview=itemView.findViewById(R.id.innerRecyclerview);

            }


        }



}
