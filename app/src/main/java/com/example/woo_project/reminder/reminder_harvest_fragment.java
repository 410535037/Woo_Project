package com.example.woo_project.reminder;

import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.woo_project.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class reminder_harvest_fragment extends Fragment {
    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    private TextView vege_name_tv,edit_tv,delete_tv;
    List<reminder_cardview> reminderList;
    List<reminder_second_layer_cardview> remindList2=new ArrayList<>();
    RecyclerView reminder_rv;
    String reminder_vegetable_data;
    ArrayList<Integer> counter = new ArrayList<>();
    private List<List<String>> reminder_seedling_data = new ArrayList<>();
    public reminder_harvest_fragment() {
        // Requires empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.reminder_seedling_fragment_layout, container, false);
        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

//        mTextTitle = (TextView) root.findViewById(R.id.text_title_home);
        reminder_rv=root.findViewById(R.id.rv1);
        reminderList=new ArrayList<>();

        mThreadHandler.post(getReminder_seedling_data);

        return root;
    }

    private Runnable getReminder_seedling_data=new Runnable () {

        public void run() {
            reminder_seedling_data=reminder_webservice.reminder_seedling_data_list("39");
            //請經紀人指派工作名稱 r，給工人做
            Log.v("test","data:"+reminder_seedling_data);
            mUI_Handler.post(setReminder_seedling_data);

        }

    };

    private Runnable setReminder_seedling_data = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {

                    List<String> seedling_vege_id = new ArrayList<>();
                    List<String> seedling_vege_name = new ArrayList<>();
                    List<String> seedling_day = new ArrayList<>();
                    List<String> seedling_number = new ArrayList<>();
                    List<String> seedling_number_unit = new ArrayList<>();
                    List<String> seedling_checkornot = new ArrayList<>();
                    List<String> seedling_vege_image = new ArrayList<>();

                    Log.v("test","reminder_seedling_data的長度: "+reminder_seedling_data.size());
                    for(int i=0;i<reminder_seedling_data.size();i++) {

                        seedling_vege_id.add(reminder_seedling_data.get(i).get(0));
                        seedling_vege_name.add(reminder_seedling_data.get(i).get(1));
                        seedling_day.add(reminder_seedling_data.get(i).get(2));
                        seedling_number.add(reminder_seedling_data.get(i).get(3));
                        seedling_number_unit.add(reminder_seedling_data.get(i).get(4));
                        if(reminder_seedling_data.get(i).get(6).equals("True")) {
                            seedling_checkornot.add("checked");
                        }
                        else {
                            seedling_checkornot.add("unchecked");
                        }
                        seedling_vege_image.add(reminder_seedling_data.get(i).get(7));

                    }
                    for(int i=0;i<seedling_vege_name.size();i++){
                        reminderList.add(new reminder_cardview(seedling_vege_id.get(i), "cute_carrot",seedling_vege_name.get(i), "預計育苗日 :  " + seedling_day.get(i).substring(0,10), "#"+seedling_number.get(i)+seedling_number_unit.get(i), seedling_checkornot.get(i)));
                    }
                    for (int i = 0; i < reminderList.size(); i++) {
                        counter.add(0);
                    }
                    reminder_rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    reminder_rv.setHasFixedSize(true);
                    reminder_rv.setAdapter(new reminder_harvest_fragment.reminder_first_layer_fragment_adapter(getActivity(), reminderList));


                }
            });

        }
    };



    private class reminder_first_layer_fragment_adapter extends RecyclerView.Adapter<com.example.woo_project.reminder.reminder_harvest_fragment.reminder_first_layer_fragment_adapter.viewholder>  {

        private Context mctx;
        private List<reminder_cardview> reminderList;
        String s="";
        public reminder_first_layer_fragment_adapter(Context mctx, List<reminder_cardview> reminderList) {
            this.mctx = mctx;
            this.reminderList = reminderList;
        }

        @Override
        public com.example.woo_project.reminder.reminder_harvest_fragment.reminder_first_layer_fragment_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mctx);
            View view=inflater.inflate(R.layout.reminder_cardview,viewGroup,false);
            return new com.example.woo_project.reminder.reminder_harvest_fragment.reminder_first_layer_fragment_adapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(final com.example.woo_project.reminder.reminder_harvest_fragment.reminder_first_layer_fragment_adapter.viewholder holder, final int position) {
            final reminder_cardview vege=reminderList.get(position);

            holder.vege.setText(String.valueOf(vege.getName()));
            int drawableResourceId = mctx.getResources().getIdentifier(vege.getVege_img(), "drawable", mctx.getPackageName());
            Glide.with(mctx)
                    .load(drawableResourceId)
                    .into(holder.vege_img);

            int drawableResourceId2 = mctx.getResources().getIdentifier(vege.getCheck_img(), "drawable", mctx.getPackageName());
            Glide.with(mctx)
                    .load(drawableResourceId2)
                    .into(holder.check_img);
            holder.tag1.setText(String.valueOf(vege.getTag1()));
            holder.tag2.setText(String.valueOf(vege.getTag2()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            holder.more_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);//初始化BottomSheet
                    View root = LayoutInflater.from(getContext()).inflate(R.layout.reminder_vege_data_bottomsheetdialog,null);//連結的介面
                    bottomSheetDialog.setContentView(root);//將介面載入至BottomSheet內
                    ((View) root.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));//將背景設為透明，否則預設白底

                    vege_name_tv = root.findViewById(R.id.vege_data_name_tv);
                    edit_tv = root.findViewById(R.id.edit_tv);
                    delete_tv = root.findViewById(R.id.delete_tv);

                    vege_name_tv.setText(vege.getName());

                    bottomSheetDialog.show();


                    edit_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    delete_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }
            });

        }

        @Override
        public int getItemCount() {
            return reminderList.size();
        }



        class viewholder extends RecyclerView.ViewHolder {
            ImageView vege_img,check_img;
            TextView vege,tag1,tag2;
            ImageButton plus_imb,more_imb;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                vege_img = (ImageView) itemView.findViewById(R.id.vege_img);
                vege = (TextView) itemView.findViewById(R.id.vegename);
                tag1 = (TextView) itemView.findViewById(R.id.tag1_tv);
                tag2 = (TextView) itemView.findViewById(R.id.tag2_tv);
                plus_imb = itemView.findViewById(R.id.plus_imb);
                more_imb = itemView.findViewById(R.id.more_imb);
                check_img = (ImageView) itemView.findViewById(R.id.check_img);

            }


        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //移除工人上的工作
        if (mThreadHandler != null) {
            mThreadHandler.removeCallbacks(getReminder_seedling_data);
        }
        //解聘工人 (關閉Thread)
        if (mThread != null) {
            mThread.quit();
        }
    }
}
