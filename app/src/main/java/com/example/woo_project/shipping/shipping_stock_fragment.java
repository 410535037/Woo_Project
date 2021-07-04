package com.example.woo_project.shipping;
//庫存量Fragment:庫存量cardview(cardview顯示的資料、出貨btsdialog、詳細資料btsdialog)
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.example.woo_project.R;
import com.example.woo_project.reminder.main_reminder;
import com.example.woo_project.reminder.reminder_cardview_harvest;
import com.example.woo_project.reminder.reminder_harvest_fragment;
import com.example.woo_project.reminder.reminder_webservice;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Iterator;
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
    private List<shipping_stock_sum_cardview> shipping_stock_sum;
    RecyclerView RvCv;
    List<shipping_stock_bottomsheetdialog_cardview> items;
    List<List<String>> inventory_all,inventory_sum;

    //廠商列表
    private List<String> vendor_list = new ArrayList<>();

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

        mThreadHandler.post(getVendor_list);
        mThreadHandler.post(getInventory_sum);
        //shipping_stock_cardview(String id, String vege_img, String name, String tag1,
        // String tag2,unit,String check_img, String vendor, String remark,String preharvest,String preseedling,
        // String pregrowing,int preday_num, int pregrowing_num))


    }

    //剩餘庫存量
    Runnable getInventory_sum=new Runnable () {
        public void run() {
            //顯示"自訂"區間的作物CardviewList
            inventory_sum = shipping_webservice.inventory_sum(39);
            mUI_Handler.post(setInventory_sum);
        }

    };

    //從資料庫抓完並顯示收成的CardviewList資料
    private Runnable setInventory_sum = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {

                    List<String> inventory_name = new ArrayList<>();
                    List<String> inventory_weight = new ArrayList<>();
                    List<String> inventory_img = new ArrayList<>();
                    List<String> inventory_vendor = new ArrayList<>();

                    //png_list
                    Log.v("test","inventory_sum的長度: "+inventory_sum.size());
                    for(int i=0;i<inventory_sum.size();i++) {
                        inventory_name.add(inventory_sum.get(i).get(0));
                        inventory_weight.add(inventory_sum.get(i).get(1));
                        inventory_img.add(inventory_sum.get(i).get(2));
                        inventory_vendor.add(inventory_sum.get(i).get(3));
                    }

                    shipping_stock_sum = new ArrayList<>();

                    for(int i=0;i<inventory_name.size();i++){
                        shipping_stock_sum.add(new shipping_stock_sum_cardview(String.valueOf(i),inventory_name.get(i),inventory_img.get(i), Integer.parseInt(inventory_weight.get(i)),inventory_vendor.get(i)));
                    }


                    shipping_rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    shipping_rv.setHasFixedSize(true);
                    shipping_rv.setAdapter(new shipping_stock_sum_Adapter(getTargetFragment(),getActivity(),shipping_stock_sum,vendor_list));



                }
            });

        }
    };


    //call 所有廠商
    private Runnable getVendor_list=new Runnable () {

        public void run() {

            vendor_list=reminder_webservice.vendor_list();
            //請經紀人指派工作名稱 r，給工人做
            Log.v("test","data:"+vendor_list);
            //mUI_Handler.post(setVendor_list);
        }

    };





    //詳細庫存
    Runnable getInventory_all=new Runnable () {

        public void run() {


            //顯示"自訂"區間的作物CardviewList
            inventory_all = shipping_webservice.inventory(39);
            mUI_Handler.post(setInventory_all);
        }

    };

    //從資料庫抓完並顯示收成的CardviewList資料
    private Runnable setInventory_all = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {

                    List<String> inventory_name = new ArrayList<>();
                    List<String> inventory_pic = new ArrayList<>();
                    List<String> inventory_vendor = new ArrayList<>();
                    List<String> inventory_canopy = new ArrayList<>();
                    List<String> inventory_weight = new ArrayList<>();
                    List<String> inventory_date = new ArrayList<>();
                    //統計作物和對應數量
                    List<List<String>> name_weight = new ArrayList<>();

                    //png_list
                    Log.v("test","inventory_all的長度: "+inventory_all.size());
                    for(int i=0;i<inventory_all.size();i++) {

                        inventory_name.add(inventory_all.get(i).get(0));
                        inventory_pic.add(inventory_all.get(i).get(1));
                        inventory_vendor.add(inventory_all.get(i).get(2));
                        inventory_canopy.add(inventory_all.get(i).get(3));
                        inventory_weight.add(inventory_all.get(i).get(4));
                        inventory_date.add(inventory_all.get(i).get(5));
                        Log.v("inventory vendor:",inventory_vendor.get(i));
                    }




                    shipping_stock_List = new ArrayList<>();

                    for(int i=0;i<inventory_name.size();i++){
                        shipping_stock_List.add(new shipping_stock_cardview(String.valueOf(i),inventory_name.get(i),inventory_pic.get(i), "壽豐農會",
                                "2021-05-15", Integer.parseInt(inventory_weight.get(i)), "A01", 30, "2021-05-05","盤","2021-05-10", 12,5,10,5));
                    }


//                    for (int i = 0; i < reminderList.size(); i++) {
//                        counter.add(0);
//                    }
                    shipping_rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    shipping_rv.setHasFixedSize(true);
 //                   shipping_rv.setAdapter(new shipping_first_layer_fragment_adapter(getActivity(),shipping_stock_List));



                }
            });

        }
    };
}
