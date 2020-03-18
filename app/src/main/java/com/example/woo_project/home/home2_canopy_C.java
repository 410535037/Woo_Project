package com.example.woo_project.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.woo_project.R;

import java.util.ArrayList;
import java.util.List;


public class home2_canopy_C extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fg1, container, false);
        List<home2_plant_img_cardview> canopy_cardviewList = new ArrayList<>();


        RecyclerView recyclerView = view.findViewById(R.id.home2_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        for(int i = 0 ;i < 20;i++)
        {
            canopy_cardviewList.add(new home2_plant_img_cardview(i,"C  "+i,R.drawable.home_canopy,""));
        }
        canopy_CardAdapter canopy_cardAdapter = new canopy_CardAdapter(getContext(), canopy_cardviewList);
        recyclerView.setAdapter(canopy_cardAdapter);

        return view;
    }


}

