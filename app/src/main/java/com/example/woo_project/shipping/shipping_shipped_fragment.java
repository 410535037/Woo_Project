package com.example.woo_project.shipping;

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
import com.example.woo_project.reminder.reminder_cardview;
import com.example.woo_project.reminder.reminder_seedling_fragment;

import java.util.ArrayList;
import java.util.List;


public class shipping_shipped_fragment extends Fragment {

    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    private RecyclerView shipping_rv;
    private List<shipping_unshipped_cardview> shipping_unshipped_List;
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

        shipping_unshipped_List = new ArrayList<>();

        //shipping_unshipped_cardview(String id, String vege_img, String name, String tag1,
        // String tag2,unit,String check_img, String vendor, String remark,String preharvest,String preseedling,
        // String pregrowing,int preday_num, int pregrowing_num))
        for(int i=0;i<1;i++){
//            shipping_unshipped_List.add(new shipping_unshipped_cardview("11","玉米","qrcode", "壽豐農會",
//                    "2021-05-16", "20.3元/斤", 120, "2021.05.05", "盤","2021-05-10","2021-05-30", 12,5,10,20,120));
        }
        shipping_rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        shipping_rv.setHasFixedSize(true);
        shipping_rv.setAdapter(new shipping_first_layer_fragment_adapter(getActivity(),shipping_unshipped_List));


    }

    private class shipping_first_layer_fragment_adapter extends RecyclerView.Adapter<com.example.woo_project.shipping.shipping_shipped_fragment.shipping_first_layer_fragment_adapter.viewholder>  {

        private Context mctx;
        private List<shipping_unshipped_cardview> shipping_unshipped_List;
        String s="";
        public shipping_first_layer_fragment_adapter(Context mctx, List<shipping_unshipped_cardview> shipping_unshipped_List) {
            this.mctx = mctx;
            this.shipping_unshipped_List = shipping_unshipped_List;
        }

        @Override
        public com.example.woo_project.shipping.shipping_shipped_fragment.shipping_first_layer_fragment_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mctx);
            View view=inflater.inflate(R.layout.shipping_shipped_cardview,viewGroup,false);
            return new com.example.woo_project.shipping.shipping_shipped_fragment.shipping_first_layer_fragment_adapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(final com.example.woo_project.shipping.shipping_shipped_fragment.shipping_first_layer_fragment_adapter.viewholder holder, final int position) {
            final shipping_unshipped_cardview vege=shipping_unshipped_List.get(position);

            holder.vege_name.setText(String.valueOf(vege.getVege_name()));
            int drawableResourceId = mctx.getResources().getIdentifier(vege.getVege_img(), "drawable", mctx.getPackageName());
            Glide.with(mctx)
                    .load(drawableResourceId)
                    .into(holder.vege_img);
            holder.vendor.setText(String.valueOf(vege.getVendor()));


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

        }

        @Override
        public int getItemCount() {
            return shipping_unshipped_List.size();
        }



        class viewholder extends RecyclerView.ViewHolder {
            ImageView vege_img;
            TextView vege_name,vendor,harvest_day,harvest_num,tag1_greenhouse;
            ImageButton plus_imb,more_imb;
            RecyclerView secondrecyclerview;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                vege_img = (ImageView) itemView.findViewById(R.id.vege_img);
                vege_name = (TextView) itemView.findViewById(R.id.vege_name);
                vendor = (TextView) itemView.findViewById(R.id.vendor_tv);
                harvest_day = (TextView) itemView.findViewById(R.id.harvest_day_tv);
                harvest_num = (TextView) itemView.findViewById(R.id.harvest_num_tv);
                tag1_greenhouse = (TextView) itemView.findViewById(R.id.tag1_tv);
                plus_imb = itemView.findViewById(R.id.plus_imb);
                more_imb = itemView.findViewById(R.id.more_imb);
                secondrecyclerview=itemView.findViewById(R.id.innerRecyclerview);

            }


        }

    }

}
