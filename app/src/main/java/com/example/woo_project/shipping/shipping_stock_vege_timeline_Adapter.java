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

    List<String> more_seedling_date;    //實際育苗日
    List<String> more_seedling_num;     //育苗數量
    List<String> more_remarks;          //備註

    List<String> more_planting_date;    //實際定植日
    List<String> more_planting_num;     //定植數量

    List<String> more_harvest_date;     //實際收成日
    List<String> more_harvest_weight;   //實際收成重量


    public shipping_stock_vege_timeline_Adapter(Context context, List<shipping_stock_vege_timeline_cardview> vege_timeline_list,
                                                List<String> more_seedling_date,List<String> more_seedling_num,List<String> more_remarks,
                                                List<String> more_planting_date,List<String> more_planting_num,
                                                List<String> more_harvest_date,List<String> more_harvest_weight) {

        this.context = context;
        this.vege_timeline_list = vege_timeline_list;

        this.more_seedling_date = more_seedling_date;
        this.more_seedling_num = more_seedling_num;
        this.more_remarks = more_remarks;

        this.more_planting_date = more_planting_date;
        this.more_planting_num = more_planting_num;

        this.more_harvest_date = more_harvest_date;
        this.more_harvest_weight = more_harvest_weight;
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

            for(int i=0;i<more_seedling_num.size();i++)
            {
                vege_timeline_details_list.add(new shipping_stock_vege_timeline_details_cardview("盤數 : "+more_seedling_num.get(i)+" 盤","備註 : "+more_remarks.get(i)));
            }
        }
        else if(position==1){
            for(int i=0;i<more_planting_date.size();i++)
            {
                vege_timeline_details_list.add(new shipping_stock_vege_timeline_details_cardview("日期 : "+more_planting_date.get(i),"盤數 : "+more_planting_num.get(i)+" 盤"));
            }
        }
        else if(position==2){
            for(int i=0;i<more_harvest_date.size();i++)
            {
                vege_timeline_details_list.add(new shipping_stock_vege_timeline_details_cardview("日期 : "+more_harvest_date.get(i),"收成重量 : "+more_harvest_weight.get(i)+" 公斤"));
            }

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

