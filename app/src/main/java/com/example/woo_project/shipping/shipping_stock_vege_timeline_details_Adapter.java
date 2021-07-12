package com.example.woo_project.shipping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woo_project.R;

import java.util.List;

public class shipping_stock_vege_timeline_details_Adapter extends RecyclerView.Adapter<shipping_stock_vege_timeline_details_Adapter.viewHolder> {
    private Context context;
    List<shipping_stock_vege_timeline_details_cardview> vege_timeline_details_list;


    public shipping_stock_vege_timeline_details_Adapter(Context context, List<shipping_stock_vege_timeline_details_cardview> vege_timeline_details_list) {
        this.context = context;
        this.vege_timeline_details_list = vege_timeline_details_list;
    }

    @Override
    public shipping_stock_vege_timeline_details_Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shipping_stock_vege_timeline_details_cardview,parent,false);



        return new shipping_stock_vege_timeline_details_Adapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final shipping_stock_vege_timeline_details_Adapter.viewHolder holder,int position) {


        holder.item1.setText(vege_timeline_details_list.get(position).getItem1());
        holder.item2.setText(vege_timeline_details_list.get(position).getItem2());


    }

    @Override
    public int getItemCount() {
        return vege_timeline_details_list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView item1,item2;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            item1 = itemView.findViewById(R.id.item1);
            item2 = itemView.findViewById(R.id.item2);

        }
    }
}

