package com.example.woo_project.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
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


public class home2_canopy_B extends Fragment {


    private List<String>canopy_list = new ArrayList<>();
    private int viewpager_id = 1;
    private String canopy_area;
    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private android.os.Handler mUI_Handler = new android.os.Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;
    private RecyclerView recyclerView;
    private List<home2_plant_img_cardview> canopy_cardviewList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fg1, container, false);
        recyclerView = view.findViewById(R.id.home2_recyclerview);


        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");

        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());
        mThreadHandler.post(getCanopy_list);


        return view;
    }


    void setViewpager_id(int i, String y){
        viewpager_id = i;
        canopy_area = y;
    }



    private Runnable getCanopy_list = new Runnable() {
        @Override
        public void run() {
            canopy_list = home2_webservice.canopy_list(viewpager_id);
            mThreadHandler.post(setCanopy_list);
        }
    };

    private Runnable setCanopy_list = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {

//                    Log.v("test","canopy_list: "+canopy_list.get(2));
                    if(!canopy_list.contains("找不到"))
                    {
                        canopy_cardviewList.clear();
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                        for(int i = 0 ;i < canopy_list.size();i++)
                        {
                            canopy_cardviewList.add(new home2_plant_img_cardview(i+1,canopy_area+"  "+canopy_list.get(i),R.drawable.home_canopy,""));
                        }
                        canopy_CardAdapter canopy_cardAdapter = new canopy_CardAdapter(getContext(), canopy_cardviewList);
                        recyclerView.setAdapter(canopy_cardAdapter);
                    }

                }
            });

        }
    };


}

