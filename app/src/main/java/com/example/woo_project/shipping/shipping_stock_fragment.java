package com.example.woo_project.shipping;
//庫存量Fragment:庫存量cardview(cardview顯示的資料、出貨btsdialog、詳細資料btsdialog)
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.woo_project.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;


public class shipping_stock_fragment extends Fragment {

    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    private RecyclerView shipping_rv;
    private List<shipping_stock_cardview> shipping_stock_List;
    RecyclerView RvCv;
    List<shipping_stock_bottomsheetdialog_cardview> items;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.reminder_seedling_fragment_layout, container, false);

        shipping_rv=root.findViewById(R.id.rv1);


        return root;
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

        shipping_stock_List = new ArrayList<>();

        //shipping_stock_cardview(String id, String vege_img, String name, String tag1,
        // String tag2,unit,String check_img, String vendor, String remark,String preharvest,String preseedling,
        // String pregrowing,int preday_num, int pregrowing_num))
        for(int i=0;i<1;i++){
            shipping_stock_List.add(new shipping_stock_cardview("11","玉米","sophone", "壽豐農會",
                    "2021-05-15", 150, "A01", 30, "2021-05-05","盤","2021-05-10", 12,5,10,5));
        }
        shipping_rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        shipping_rv.setHasFixedSize(true);
        shipping_rv.setAdapter(new shipping_first_layer_fragment_adapter(getActivity(),shipping_stock_List));


    }

    private class shipping_first_layer_fragment_adapter extends RecyclerView.Adapter<com.example.woo_project.shipping.shipping_stock_fragment.shipping_first_layer_fragment_adapter.viewholder>  {

        private Context mctx;
        private List<shipping_stock_cardview> shipping_stock_List;
        String s="";
        public shipping_first_layer_fragment_adapter(Context mctx, List<shipping_stock_cardview> shipping_stock_List) {
            this.mctx = mctx;
            this.shipping_stock_List = shipping_stock_List;
        }

        @Override
        public com.example.woo_project.shipping.shipping_stock_fragment.shipping_first_layer_fragment_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mctx);
            View view=inflater.inflate(R.layout.shipping_stock_cardview,viewGroup,false);
            return new com.example.woo_project.shipping.shipping_stock_fragment.shipping_first_layer_fragment_adapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(final com.example.woo_project.shipping.shipping_stock_fragment.shipping_first_layer_fragment_adapter.viewholder holder, final int position) {
            final shipping_stock_cardview vege=shipping_stock_List.get(position);

            holder.vege_name.setText(String.valueOf(vege.getVege_name()));
            int drawableResourceId = mctx.getResources().getIdentifier(vege.getVege_img(), "drawable", mctx.getPackageName());
            Glide.with(mctx)
                    .load(drawableResourceId)
                    .into(holder.vege_img);
            holder.vendor.setText(String.valueOf(vege.getVendor()));
            holder.harvest_day.setText(String.valueOf(vege.getHarvest_day()));
            holder.harvest_num.setText(String.valueOf(vege.getHarvest_num()));
            holder.tag1_greenhouse.setText(String.valueOf(vege.getTag1_planting_greenhouse()));
            holder.tag2_stock_num.setText(String.valueOf("剩餘"+vege.getTag2_stock_num())+"公斤");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            holder.more_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);//初始化BottomSheet
                    View root = LayoutInflater.from(getContext()).inflate(R.layout.shipping_vege_data_bottomsheetdialog, null);//連結的介面
                    bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                    ((View) root.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底

                    RvCv = root.findViewById(R.id.recyclerview_cv);

                    items = new ArrayList<>();
                    items.add(new shipping_stock_bottomsheetdialog_cardview("育苗","2021-05-05",5,"育苗盤數 : 25盤"));
                    items.add(new shipping_stock_bottomsheetdialog_cardview("定植","2021-05-10",20,"定植棚架 : A01\n定植盤數 : 15盤"));
                    items.add(new shipping_stock_bottomsheetdialog_cardview("收成","2021-05-30",0,"收穫重量 : 200公斤\n出貨廠商 : 壽豐農會"));



                    RvCv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    RvCv.setHasFixedSize(true);
                    RvCv.setAdapter(new shipping_stock_bottomsheetdialog_Adapter(shipping_stock_fragment.this,items));

                    // 顯示dialog
                    bottomSheetDialog.show();


                }
            });

            holder.go_shipping_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);//初始化BottomSheet
                    View root = LayoutInflater.from(getContext()).inflate(R.layout.shipping_stock_bottomsheetdialog, null);//連結的介面
                    bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                    ((View) root.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底

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
                tag2_stock_num = (TextView) itemView.findViewById(R.id.tag2_stock_num_tv);
                go_shipping_imb = itemView.findViewById(R.id.go_shipped_or_unshipped_imb);
                more_imb = itemView.findViewById(R.id.more_imb);
                secondrecyclerview=itemView.findViewById(R.id.innerRecyclerview);

            }


        }

    }

}
