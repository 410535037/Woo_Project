package com.example.woo_project.remind;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.woo_project.R;

import java.util.ArrayList;
import java.util.List;

public class fourFragment extends Fragment {
    private TextView mTextTitle;
    List<remind_cardview> remindList;
    RecyclerView remind_rv;

    public fourFragment() {
        // Requires empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.threefragment_layout, container, false);
//        mTextTitle = (TextView) root.findViewById(R.id.text_title_home);
        remind_rv=root.findViewById(R.id.rv1);
        remindList=new ArrayList<>();
        remind_rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        remind_rv.setHasFixedSize(true);
        remind_rv.setAdapter(new fourFragment.fourFragmentadapter(getActivity(), remindList));

        remindList.add(new remind_cardview(1,"胡蘿蔔",R.drawable.carrot, "育苗日 : 今日","20盤",false));
        remindList.add(new remind_cardview(2,"胡蘿蔔",R.drawable.carrot, "育苗日 : 今日","20盤",false));
        remindList.add(new remind_cardview(3,"胡蘿蔔",R.drawable.carrot, "育苗日 : 今日","20盤",false));
        remindList.add(new remind_cardview(4,"胡蘿蔔",R.drawable.carrot, "育苗日 : 今日","20盤",false));

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        remindList=new ArrayList<>();
//        remind_rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//        remind_rv.setHasFixedSize(true);
//        remind_rv.setAdapter(new fourFragment.oneFragmentadaper(getActivity(), remindList));
//
//        remindList.add(new remind_cardview(1,"胡蘿蔔",R.drawable.carrot, "育苗日 : 今日","20盤",false));
//        remindList.add(new remind_cardview(2,"胡蘿蔔",R.drawable.carrot, "育苗日 : 今日","20盤",false));
//        remindList.add(new remind_cardview(3,"胡蘿蔔",R.drawable.carrot, "育苗日 : 今日","20盤",false));
//        remindList.add(new remind_cardview(4,"胡蘿蔔",R.drawable.carrot, "育苗日 : 今日","20盤",false));
//        remindList.add(new remind_cardview(5,"胡蘿蔔",R.drawable.carrot, "育苗日 : 今日","20盤",false));
//        remindList.add(new remind_cardview(6,"胡蘿蔔",R.drawable.carrot, "育苗日 : 今日","20盤",false));
//        remindList.add(new remind_cardview(7,"胡蘿蔔",R.drawable.carrot, "育苗日 : 今日","20盤",false));
//        remindList.add(new remind_cardview(8,"胡蘿蔔",R.drawable.carrot, "育苗日 : 今日","20盤",false));
    }
    private class fourFragmentadapter extends RecyclerView.Adapter<fourFragmentadapter.viewholder>  {

        private Context mctx;
        private List<remind_cardview> remindList;
        String s="";
        public fourFragmentadapter(Context mctx, List<remind_cardview> remindList) {
            this.mctx = mctx;
            this.remindList = remindList;
        }

        @Override
        public fourFragmentadapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mctx);
            View view=inflater.inflate(R.layout.remind_cardview,viewGroup,false);
            return new fourFragmentadapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull fourFragmentadapter.viewholder holder, int position) {
            final remind_cardview vege=remindList.get(position);

            holder.vege.setText(String.valueOf(vege.getName()));
            holder.image.setImageResource(vege.getImage());
            holder.tag1.setText(String.valueOf(vege.getTag1()));
            holder.tag2.setText(String.valueOf(vege.getTag2()));
            holder.check_rb.setChecked(vege.getisSelected());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return remindList.size();
        }



        class viewholder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView vege,tag1,tag2;
            RadioButton check_rb;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.image);
                vege = (TextView) itemView.findViewById(R.id.vegename);
                tag1 = (TextView) itemView.findViewById(R.id.tag1_tv);
                tag2 = (TextView) itemView.findViewById(R.id.tag2_tv);
                check_rb=itemView.findViewById(R.id.check_rb);

            }


        }

    }
}
