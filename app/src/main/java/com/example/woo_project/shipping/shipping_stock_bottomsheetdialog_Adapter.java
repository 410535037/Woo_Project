package com.example.woo_project.shipping;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woo_project.R;

import java.util.List;


public class shipping_stock_bottomsheetdialog_Adapter extends RecyclerView.Adapter<shipping_stock_bottomsheetdialog_Adapter.CVViewHolder> {

    private Fragment fragment;
    List<shipping_stock_bottomsheetdialog_cardview> mdata;

    public shipping_stock_bottomsheetdialog_Adapter(Fragment fragment,List<shipping_stock_bottomsheetdialog_cardview> mdata) {
        this.fragment = fragment;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public CVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shipping_stock_bottomsheetdialog_cardview,parent,false);

        return new CVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CVViewHolder holder, int position) {

        holder.tvTitle.setText(mdata.get(position).getTitle());
        holder.tvDescription.setText(mdata.get(position).getDescription());
        if(mdata.get(position).getTakesday()==0){
            holder.tvTakesday.setText("");
        }else{
            holder.tvTakesday.setText(mdata.get(position).getTakesday()+"天");
        }

        holder.tvDate.setText(mdata.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class CVViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle,tvDescription,tvTakesday,tvDate;

        public CVViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.shipping_title);
            tvDescription = itemView.findViewById(R.id.shipping_desc);
            tvTakesday = itemView.findViewById(R.id.shipping_takesday);
            tvDate = itemView.findViewById(R.id.shipping_date);

        }
    }
}

