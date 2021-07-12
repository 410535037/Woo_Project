package com.example.woo_project.shipping;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woo_project.R;

import java.util.ArrayList;
import java.util.List;


public class shipping_stock_vege_timeline_Adapter extends RecyclerView.Adapter<shipping_stock_vege_timeline_Adapter.viewHolder> {

    private Context context;
    private List<shipping_stock_vege_timeline_cardview> vege_timeline_list;
    private List<shipping_stock_vege_timeline_details_cardview> vege_timeline_details_list = new ArrayList<>();

    public shipping_stock_vege_timeline_Adapter(Context context, List<shipping_stock_vege_timeline_cardview> vege_timeline_list) {

        this.context = context;
        this.vege_timeline_list = vege_timeline_list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shipping_stock_vege_timeline_cardview,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder,int position) {

        final shipping_stock_vege_timeline_cardview list = vege_timeline_list.get(position);

        //--工作項目--
        holder.work_title_tv.setText(list.getWork_title());

        //--天數--
        //說明 : 育苗到定植之間會有相隔多少天數，但到收成之後就沒有下個工作，
        if(list.getTakesday()==0){
            holder.takesday_tv.setText("");
        }else{
            holder.takesday_tv.setText(list.getTakesday()+"天");
        }

        //--開始日期--
        holder.start_date_tv.setText(list.getStart_date());

        //--結束日期--
        //說明 : 有些作物定植和收成可能只有一次或多次，此處判斷是否該顯示結束日期
        if(list.getEnd_date().equals("no_end_date")) {
            holder.end_date_tv.setText("");
            holder.line_of_startdate_to_enddate.setVisibility(View.INVISIBLE);
        }
        else {
            holder.end_date_tv.setText(list.getEnd_date());
        }


        vege_timeline_details_list.clear();
        if(position==0){

            vege_timeline_details_list.add(new shipping_stock_vege_timeline_details_cardview("盤數 : 25盤","備註 : 無"));

        }
        else if(position==1){

            vege_timeline_details_list.add(new shipping_stock_vege_timeline_details_cardview("日期 : 2021-05-10","盤數 : 15盤"));


        }
        else if(position==2){
            vege_timeline_details_list.add(new shipping_stock_vege_timeline_details_cardview("日期 : 2021-05-29","收成重量 : 200公斤"));

        }


        holder.vege_details_recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.vege_details_recyclerview.setAdapter(new shipping_stock_vege_timeline_details_Adapter(context,vege_timeline_details_list));



    }

    @Override
    public int getItemCount() {
        return vege_timeline_list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView work_title_tv,start_date_tv,end_date_tv,takesday_tv;
        View line_of_startdate_to_enddate;
        RecyclerView vege_details_recyclerview;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            work_title_tv = itemView.findViewById(R.id.work_title);
            vege_details_recyclerview = itemView.findViewById(R.id.second_layer_rv);
            takesday_tv = itemView.findViewById(R.id.takesday);
            line_of_startdate_to_enddate = itemView.findViewById(R.id.line_of_startdate_to_enddate);
            start_date_tv = itemView.findViewById(R.id.start_date);
            end_date_tv = itemView.findViewById(R.id.end_date);

        }
    }
}

