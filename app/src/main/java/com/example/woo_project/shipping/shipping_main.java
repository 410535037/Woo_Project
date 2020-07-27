package com.example.woo_project.shipping;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.woo_project.GlobalVariable;
import com.example.woo_project.R;
import com.example.woo_project.chart.chart_webservice;
import com.example.woo_project.record.record;
import com.example.woo_project.record.record_Cardview;
import com.example.woo_project.record.record_Information;
import com.example.woo_project.record.record_webservice;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;


public class shipping_main extends Fragment
{
    private boolean fg = false;

    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    private AppCompatButton new_shipping;
    private List<shipping_instock_cardview> cardviewList;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.shipping_main, container, false);
        new_shipping =(AppCompatButton) view.findViewById(R.id.new_shipping);
        new_shipping.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent2 = new Intent(getActivity(), shipping_add_data.class);
                startActivity(intent2);
            }
        });

        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        //宣告特約工人
        HandlerThread mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

        //cardview 建立

        cardviewList = new ArrayList <>();
        recyclerView =  view.findViewById(R.id.instock_Recyclerview);
        mThreadHandler.post(get_cardviewList);
        return view;
    }


    //從資料庫抓存貨list
    List<List<String>> shipping_instock_cardview = new ArrayList<>();
    private Runnable get_cardviewList = new Runnable() {
        @Override
        public void run() {
            shipping_instock_cardview = shipping_webservice.instock_data_list();
            Log.v("test","instock_data_list :" + shipping_instock_cardview);
            mThreadHandler.post(setcardviewList);
        }
    };

    private  Runnable setcardviewList = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run()
                {
                    List<String> vege_name = shipping_instock_cardview.get(0);
                    List<String> instock_total = shipping_instock_cardview.get(1);
                    List<String> weight_inorder = shipping_instock_cardview.get(2);

                    //作物
                    for(int i = 0 ; i <vege_name.size();i++)
                    {
                        cardviewList.add(new shipping_instock_cardview(vege_name.get(i),Double.valueOf(instock_total.get(i)),Double.valueOf(weight_inorder.get(i))));
                    }
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(new shipping_main.CardAdapter(getContext(), cardviewList));

                }
            });

        }
    };

    private class CardAdapter extends  RecyclerView.Adapter<shipping_main.CardAdapter.ViewHolder>
    {
        private Context context;
        public List<shipping_instock_cardview> cardviewList;

        CardAdapter(Context context, List<shipping_instock_cardview> cardviewList) {
            this.context = context;
            this.cardviewList = cardviewList;
        }

        @Override
        public shipping_main.CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.shipping_instock_cardview,viewGroup,false);
            return new shipping_main.CardAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(shipping_main.CardAdapter.ViewHolder viewHolder, int i) {
            final shipping_instock_cardview cardview = cardviewList.get(i);
            viewHolder.vege_name.setText(String.valueOf(cardview.getvege_name()));
            viewHolder.instock_total.setText(String.valueOf((cardview.getinstock_total())));
            viewHolder.weight_inorder.setText(String.valueOf((cardview.getweight_inorder())));

            Log.v("test","cardview.getvege_name():  "+cardview.getvege_name());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getContext(), record_Information.class);
                    intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return cardviewList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView vege_name,instock_total,weight_inorder;

            ViewHolder(View itemView)
            {
                super(itemView);
                vege_name = (TextView) itemView.findViewById(R.id.vege_name);
                instock_total = (TextView) itemView.findViewById(R.id.instock_total);
                weight_inorder = (TextView) itemView.findViewById(R.id.weight_inorder);
            }
        }

        public  void addItem(int i)
        {
            fg = true;
        }


    }

}