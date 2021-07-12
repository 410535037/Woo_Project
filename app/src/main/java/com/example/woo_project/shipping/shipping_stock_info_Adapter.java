package com.example.woo_project.shipping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.woo_project.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

// 庫存詳細資訊
public class shipping_stock_info_Adapter extends RecyclerView.Adapter<shipping_stock_info_Adapter.viewholder>  {

        private Context mctx;
        private List<shipping_stock_cardview> shipping_stock_List;
        String s="";
        public shipping_stock_info_Adapter(Context mctx, List<shipping_stock_cardview> shipping_stock_List) {
            this.mctx = mctx;
            this.shipping_stock_List = shipping_stock_List;
        }

        @Override
        public shipping_stock_info_Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mctx);
            View view=inflater.inflate(R.layout.shipping_stock_cardview,viewGroup,false);
            return new shipping_stock_info_Adapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(final shipping_stock_info_Adapter.viewholder holder, final int position) {
            final shipping_stock_cardview vege=shipping_stock_List.get(position);

            holder.vege_name.setText(String.valueOf(vege.getVege_name()));
            int drawableResourceId = mctx.getResources().getIdentifier(vege.getVege_img(), "drawable", mctx.getPackageName());
            Glide.with(mctx)
                    .load(drawableResourceId)
                    .into(holder.vege_img);
            holder.vendor.setText(String.valueOf(vege.getVendor()));

            holder.harvest_num.setText(String.valueOf(vege.getHarvest_num()));
//            holder.tag1_greenhouse.setText(String.valueOf(vege.getTag1_planting_greenhouse()));
//            holder.tag2_stock_num.setText(String.valueOf("剩餘"+vege.getTag2_stock_num())+"公斤");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            holder.more_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mctx, R.style.BottomSheetDialogTheme);//初始化BottomSheet
                    View root = LayoutInflater.from(mctx).inflate(R.layout.shipping_vege_timeline_bottomsheetdialog, null);//連結的介面
                    bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                    ((View) root.getParent()).setBackgroundColor(mctx.getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底
//
//                    RvCv = root.findViewById(R.id.recyclerview_cv);
//
//                    items = new ArrayList<>();
//                    items.add(new shipping_stock_bottomsheetdialog_cardview("育苗","2021-05-05",5,"育苗盤數 : 25盤"));
//                    items.add(new shipping_stock_bottomsheetdialog_cardview("定植","2021-05-10",20,"定植棚架 : A01\n定植盤數 : 15盤"));
//                    items.add(new shipping_stock_bottomsheetdialog_cardview("收成","2021-05-30",0,"收穫重量 : 200公斤\n出貨廠商 : 壽豐農會"));
//
//
//
//                    RvCv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//                    RvCv.setHasFixedSize(true);
//                    RvCv.setAdapter(new shipping_stock_bottomsheetdialog_Adapter(shipping_stock_fragment.this,items));

                    // 顯示dialog
                    bottomSheetDialog.show();


                }
            });

            holder.go_shipping_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//
//                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);//初始化BottomSheet
//                    View root = LayoutInflater.from(getContext()).inflate(R.layout.shipping_stock_bottomsheetdialog, null);//連結的介面
//                    bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
//                    ((View) root.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底

                    // 顯示dialog
   //                 bottomSheetDialog.show();

                }
            });

        }

        @Override
        public int getItemCount() {
            return shipping_stock_List.size();
        }



        class viewholder extends RecyclerView.ViewHolder {
            ImageView vege_img;
            TextView vege_name,vendor,harvest_day,harvest_num,tag1_greenhouse,tag2_stock_num;
            ImageButton go_shipping_imb,more_imb;
            RecyclerView secondrecyclerview;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                vege_img = (ImageView) itemView.findViewById(R.id.vege_img);
                vege_name = (TextView) itemView.findViewById(R.id.vege_name);
                vendor = (TextView) itemView.findViewById(R.id.vendor_tv);
                harvest_day = (TextView) itemView.findViewById(R.id.harvest_day_tv);
                harvest_num = (TextView) itemView.findViewById(R.id.harvest_num_tv);
                tag1_greenhouse = (TextView) itemView.findViewById(R.id.tag1_tv);

                go_shipping_imb = itemView.findViewById(R.id.go_shipped_or_unshipped_imb);
                more_imb = itemView.findViewById(R.id.more_imb);
                secondrecyclerview=itemView.findViewById(R.id.innerRecyclerview);

            }
        }

}
