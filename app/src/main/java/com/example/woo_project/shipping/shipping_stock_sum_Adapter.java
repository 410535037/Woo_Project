package com.example.woo_project.shipping;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.example.woo_project.R;
import com.example.woo_project.reminder.reminder_webservice;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class shipping_stock_sum_Adapter extends RecyclerView.Adapter<shipping_stock_sum_Adapter.viewholder>  {

    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    private Context mctx;
        private List<shipping_stock_sum_cardview> shipping_stock_List;
        private Fragment fragment;
        private List<String> vendor_list;
        private OptionsPickerView pvOptions;
        private TextInputEditText vendor_input;
        public shipping_stock_sum_Adapter(Fragment fragment,Context mctx, List<shipping_stock_sum_cardview> shipping_stock_List,List<String> vendor_list) {
            this.mctx = mctx;
            this.shipping_stock_List = shipping_stock_List;
            this.fragment = fragment;
            this.vendor_list = vendor_list;
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
            holder.more_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mctx, R.style.BottomSheetDialogTheme);//初始化BottomSheet
                    View root = LayoutInflater.from(mctx).inflate(R.layout.shipping_vege_data_bottomsheetdialog, null);//連結的介面
                    bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                    ((View) root.getParent()).setBackgroundColor(mctx.getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底

                    final RecyclerView RvCv = root.findViewById(R.id.recyclerview_cv);
                    Spinner shipping_stock_spinner = root.findViewById(R.id.shipping_stock_spinner);

                    ArrayAdapter<CharSequence> adapter =
                            ArrayAdapter.createFromResource(mctx,            //對應的Context
                                    R.array.shipping_stock_info,             //資料選項內容
                                    android.R.layout.simple_spinner_item);  //預設Spinner未展開時的View(預設及選取後樣式)

                    final List<shipping_stock_bottomsheetdialog_cardview> items = new ArrayList<>();

                    shipping_stock_spinner.setAdapter(adapter);
                    shipping_stock_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                    {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            // 選項有選取時的動作            定植盤數 : 15盤"
                            String spnStr = String.valueOf(parent.getSelectedItem());
                            if(spnStr.equals("2021-06-23"))
                            {
                                items.clear();
                                items.add(new shipping_stock_bottomsheetdialog_cardview("育苗","2021-05-05",15,"育苗盤數 : 25盤"));
                                items.add(new shipping_stock_bottomsheetdialog_cardview("定植","2021-05-20",34,"定植棚架 : A01"));
                                items.add(new shipping_stock_bottomsheetdialog_cardview("收成","2021-06-23",0,"收穫重量 : 20公斤\n出貨廠商 : 永豐餘"));

                                RvCv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                                RvCv.setHasFixedSize(true);
                                RvCv.setAdapter(new shipping_stock_bottomsheetdialog_Adapter(fragment,items));

                            }
                            else if (spnStr.equals("2021-06-24"))
                            {
                                items.clear();
                                items.add(new shipping_stock_bottomsheetdialog_cardview("育苗","2021-05-06",18,"育苗盤數 : 45盤"));
                                items.add(new shipping_stock_bottomsheetdialog_cardview("定植","2021-05-24",30,"定植棚架 : B03"));
                                items.add(new shipping_stock_bottomsheetdialog_cardview("收成","2021-06-24",0,"收穫重量 : 50公斤\n出貨廠商 : 永豐餘"));

                                RvCv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                                RvCv.setHasFixedSize(true);
                                RvCv.setAdapter(new shipping_stock_bottomsheetdialog_Adapter(fragment,items));
                            }
                            else if (spnStr.equals("2021-06-28"))
                            {
                                items.clear();
                                items.add(new shipping_stock_bottomsheetdialog_cardview("育苗","2021-05-11",17,"育苗盤數 : 20盤"));
                                items.add(new shipping_stock_bottomsheetdialog_cardview("定植","2021-05-28",30,"定植棚架 : B18"));
                                items.add(new shipping_stock_bottomsheetdialog_cardview("收成","2021-06-28",0,"收穫重量 : 23公斤\n出貨廠商 : 永豐餘"));

                                RvCv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                                RvCv.setHasFixedSize(true);
                                RvCv.setAdapter(new shipping_stock_bottomsheetdialog_Adapter(fragment,items));
                            }
                            else if (spnStr.equals("2021-07-01"))
                            {
                                items.clear();
                                items.add(new shipping_stock_bottomsheetdialog_cardview("育苗","2021-05-18",15,"育苗盤數 : 25盤"));
                                items.add(new shipping_stock_bottomsheetdialog_cardview("定植","2021-06-02",29,"定植棚架 : A11"));
                                items.add(new shipping_stock_bottomsheetdialog_cardview("收成","2021-07-01",0,"收穫重量 : 20公斤\n出貨廠商 : 永豐餘"));

                                RvCv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                                RvCv.setHasFixedSize(true);
                                RvCv.setAdapter(new shipping_stock_bottomsheetdialog_Adapter(fragment,items));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // 沒有選取時的動作
                        }
                    });


                    items.clear();
                    items.add(new shipping_stock_bottomsheetdialog_cardview("育苗","2021-05-05",15,"育苗盤數 : 25盤"));
                    items.add(new shipping_stock_bottomsheetdialog_cardview("定植","2021-05-20",34,"定植棚架 : A01"));
                    items.add(new shipping_stock_bottomsheetdialog_cardview("收成","2021-06-23",0,"收穫重量 : 20公斤\n出貨廠商 : 永豐餘"));

                    RvCv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    RvCv.setHasFixedSize(true);
                    RvCv.setAdapter(new shipping_stock_bottomsheetdialog_Adapter(fragment,items));

                    // 顯示dialog
                    bottomSheetDialog.show();


                }
            });

            holder.go_shipping_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mctx, R.style.BottomSheetDialogTheme);//初始化BottomSheet
                    View root = LayoutInflater.from(mctx).inflate(R.layout.shipping_stock_bottomsheetdialog, null);//連結的介面
                    bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                    ((View) root.getParent()).setBackgroundColor(mctx.getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底


                    final TextInputEditText out_date,out_weight,out_price;
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
                    out_date.setText("2021-06-23");
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
