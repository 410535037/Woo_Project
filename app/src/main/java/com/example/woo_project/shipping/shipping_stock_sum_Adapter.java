package com.example.woo_project.shipping;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

                    RecyclerView RvCv = root.findViewById(R.id.recyclerview_cv);

                    List<shipping_stock_bottomsheetdialog_cardview> items = new ArrayList<>();
                    items.add(new shipping_stock_bottomsheetdialog_cardview("育苗","2021-05-05",5,"育苗盤數 : 25盤"));
                    items.add(new shipping_stock_bottomsheetdialog_cardview("定植","2021-05-10",20,"定植棚架 : A01\n定植盤數 : 15盤"));
                    items.add(new shipping_stock_bottomsheetdialog_cardview("收成","2021-05-30",0,"收穫重量 : 200公斤\n出貨廠商 : 壽豐農會"));



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


                    TextInputEditText out_date,out_weight,out_price;
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
