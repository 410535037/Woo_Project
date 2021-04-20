package com.example.woo_project.reminder;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.woo_project.record.record_webservice;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woo_project.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

public class reminder_planting_bottomsheetdialog_Adapter extends RecyclerView.Adapter<reminder_planting_bottomsheetdialog_Adapter.ViewHolder> {

        private List<reminder_planting_bottomsheetdialog_cardview> reminderList;
        private Fragment fragment;
        private Calendar c2;
        List<String> canopy_area;
        List<List<String>> canopy_name;
        CharSequence todaydate;
        List<String> time_list;
    /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextInputEditText date_tiet,greenhouse_tiet,planting_num;;
            ImageView minus;
            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View
                date_tiet = view.findViewById(R.id.date_tiet);
                planting_num = view.findViewById(R.id.planting_num);
                greenhouse_tiet = view.findViewById(R.id.greenhouse_tiet);
                minus = view.findViewById(R.id.cardview_minus);

            }
        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param reminderList String[] containing the data to populate views to be used
         * by RecyclerView.
         */
        public reminder_planting_bottomsheetdialog_Adapter(Fragment fragment,List<String> canopy_area,List<List<String>> canopy_name, List<reminder_planting_bottomsheetdialog_cardview> reminderList) {
            this.reminderList = reminderList;
            this.fragment = fragment;
            this.canopy_name = canopy_name;
            this.canopy_area = canopy_area;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item+

            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.reminder_planting_bottomsheetdialog_cardview, viewGroup, false);

            view.getWindowSystemUiVisibility();
            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            //viewHolder.getTextView().setText(localDataSet[position]);

            viewHolder.planting_num.setText(String.valueOf(reminderList.get(position).getCardview_num()));

            viewHolder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reminderList.remove(position);
                    notifyDataSetChanged();
                }
            });

            //date日期
            //把2021-04-20 拆成 2021,04,20
            time_list = Arrays.asList(reminderList.get(position).getCardview_date().split("-"));
            c2 = Calendar.getInstance();
            c2.set(Integer.parseInt(time_list.get(0)),Integer.parseInt(time_list.get(1))-1,Integer.parseInt(time_list.get(2)));
            todaydate = DateFormat.format("yyyy-MM-dd", c2.getTime());
            viewHolder.date_tiet.setText(todaydate);

            //看日曆
            viewHolder.date_tiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    DialogFragment datePicker = new DatePickerFragment(c2.get(Calendar.YEAR),c2.get(Calendar.MONTH),c2.get(Calendar.DAY_OF_MONTH));
//                    datePicker.setTargetFragment(fragment,0);
//                    datePicker.show(fragment.getFragmentManager(),"date picker");

                    //把該position排序的日期給DatePickerDialog
                    time_list = Arrays.asList(reminderList.get(position).getCardview_date().split("-"));


                    //看日曆
                    DatePickerDialog datePicker = new DatePickerDialog(fragment.getActivity(),new DatePickerDialog.OnDateSetListener()
                    {
                        //按下確定
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                        {
                            c2.set(Calendar.YEAR, year);
                            c2.set(Calendar.MONTH, month);
                            c2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            // 將日期寫入日期欄位
                            todaydate = DateFormat.format("yyyy-MM-dd", c2.getTime());
                            viewHolder.date_tiet.setText(todaydate);
                        }
                    //}, Integer.parseInt(time_list.get(0)),Integer.parseInt(time_list.get(1))-1,Integer.parseInt(time_list.get(2)));
                    }, Integer.parseInt(time_list.get(0)),Integer.parseInt(time_list.get(1))-1,Integer.parseInt(time_list.get(2)));
                    datePicker.show();
                }

            });




            //選取棚架位置
            viewHolder.greenhouse_tiet.setText(reminderList.get(position).getCardview_canopy());
            viewHolder.greenhouse_tiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OptionsPickerView pvOptions;
                    pvOptions = new OptionsPickerBuilder(fragment.getContext(), new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            String vege = canopy_name.get(options1).get(options2);
                            viewHolder.greenhouse_tiet.setText(vege);
                            //bottomSheetDialog.show(); //當pickerview按下確認會再開啟btmdialog，讓使用者繼續輸入資料
                        }
                    }).setTitleText("請選擇") // 選擇器標題
                            .setContentTextSize(18)//設定滾輪文字大小
                            .setTitleSize(18)
                            .setDividerColor(fragment.getResources().getColor(R.color.Gainsboro))//設定分割線顏色
                            .setSelectOptions(0, 1)//默認選中值
                            .setBgColor(Color.WHITE)
                            .setTitleBgColor(fragment.getResources().getColor(R.color.WhiteSmoke))
                            .setTitleColor(Color.BLACK)
                            .setCancelColor(fragment.getResources().getColor(R.color.Azure))
                            .setSubmitColor(fragment.getResources().getColor(R.color.Azure))
                            .setCancelText("取消")
                            .setSubmitText("確定")
                            .setTextColorCenter(fragment.getResources().getColor(R.color.Dimgray))
                            .setBackgroundId(0x66000000) //設定外部遮罩顏色
                            .isDialog(true) //以dialog出現 可跑到最上層
                            .build();

                    pvOptions.setPicker(canopy_area, canopy_name);
                    //因dialog預設為中間 調整至底下
                    pvOptions.getDialog().getWindow().setGravity(Gravity.BOTTOM);
                    //因會有邊界 以下即可把左右邊界消除
                    FrameLayout.LayoutParams lLayoutParams =(FrameLayout.LayoutParams) pvOptions.getDialogContainerLayout().getLayoutParams();
                    lLayoutParams.leftMargin = 0;
                    lLayoutParams.rightMargin = 0;
                    //把設定好的Params給回去
                    pvOptions.getDialogContainerLayout().setLayoutParams(lLayoutParams);
                    pvOptions.show();

                    //參考URL(最下面有解法): https://github.com/Bigkoo/Android-PickerView/issues/279
                }
            });



        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return reminderList.size();
        }

}
