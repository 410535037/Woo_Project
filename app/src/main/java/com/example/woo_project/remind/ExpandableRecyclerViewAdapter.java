package com.example.woo_project.remind;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woo_project.R;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;


public class ExpandableRecyclerViewAdapter extends RecyclerView.Adapter<ExpandableRecyclerViewAdapter.ViewHolder> {

    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<Integer> imageList = new ArrayList<Integer>();
    ArrayList<Integer> counter = new ArrayList<Integer>();
    ArrayList<ArrayList> itemNameList = new ArrayList<ArrayList>();
    Context context;
    ViewGroup viewGroup;
    TextView confirm_tv;
    ImageButton edit_imb,delete_imb;
    boolean check=false;

    public ExpandableRecyclerViewAdapter(Context context,
                                         ArrayList<Integer> imageList,
                                         ArrayList<String> nameList,
                                         ArrayList<ArrayList> itemNameList) {
        this.imageList=imageList;
        this.nameList = nameList;
        this.itemNameList = itemNameList;
        this.context = context;


        for (int i = 0; i < nameList.size(); i++) {
            counter.add(0);
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,tag1_tv,tag2_tv;
        ImageView image;
        RadioButton check_rb;
        RecyclerView cardRecyclerView;
        CardView cardview;
        ViewGroup viewGroup;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.vegename);
            tag1_tv = itemView.findViewById(R.id.tag1_tv);
            tag2_tv = itemView.findViewById(R.id.tag2_tv);
            check_rb = itemView.findViewById(R.id.check_rb);
            image = itemView.findViewById(R.id.image);
            cardRecyclerView = itemView.findViewById(R.id.innerRecyclerview);
            cardview = itemView.findViewById(R.id.cardview);
            viewGroup = itemView.findViewById(R.id.content);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.remind_cardview, parent, false);

        ExpandableRecyclerViewAdapter.ViewHolder vh = new ExpandableRecyclerViewAdapter.ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(nameList.get(position));

        holder.image.setImageResource(imageList.get(position));
        InnerRecyclerViewAdapter itemInnerRecyclerView = new InnerRecyclerViewAdapter(itemNameList.get(position));


        holder.cardRecyclerView.setLayoutManager(new GridLayoutManager(context,1));

        holder.check_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(view);

            }
        });
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counter.get(position) % 2 == 0) {
                    holder.cardRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    holder.cardRecyclerView.setVisibility(View.GONE);
                }

                counter.set(position, counter.get(position) + 1);
                holder.cardRecyclerView.smoothScrollToPosition(position);

            }
        });
//        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                showCustomDialog2(view);
//                if(check==true){
//                    nameList.remove(position);
//                    notifyDataSetChanged();
//
//                }
//                return true;
//            }
//        });
        holder.cardRecyclerView.setAdapter(itemInnerRecyclerView);


    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    private void showCustomDialog(View view) {

        //before inflating the custom alert dialog layout, we will get the current activity viewgroup


        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.check_dialog, viewGroup, false);
        confirm_tv=dialogView.findViewById(R.id.confrim_tv);

        confirm_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x=new Intent(view.getContext(),main_remind.class);
                view.getContext().startActivity(x);
            }
        });

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.check_dialog);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }
//    private void showCustomDialog2(View view) {
//
//        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
//
//
//        //then we will inflate the custom alert dialog xml that we created
//        View dialogView2 = LayoutInflater.from(view.getContext()).inflate(R.layout.edit_or_delete, viewGroup, false);
//        edit_imb=dialogView2.findViewById(R.id.edit_imb);
//        delete_imb=dialogView2.findViewById(R.id.delete_imb);
//
//        edit_imb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent x=new Intent(view.getContext(),main_remind.class);
//                view.getContext().startActivity(x);
//            }
//        });
//        delete_imb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                check=true;
//            }
//        });
//
//        //Now we need an AlertDialog.Builder object
//        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//
//        //setting the view of the builder to our custom view that we already inflated
//        builder.setView(dialogView2);
//
//        //finally creating the alert dialog and displaying it
//        AlertDialog alertDialog = builder.create();
//        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        alertDialog.setContentView(R.layout.edit_or_delete);
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        alertDialog.show();
//    }

}
