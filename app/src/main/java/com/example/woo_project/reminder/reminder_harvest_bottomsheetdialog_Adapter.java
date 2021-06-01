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
import android.widget.Toast;

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

public class reminder_harvest_bottomsheetdialog_Adapter extends RecyclerView.Adapter<reminder_harvest_bottomsheetdialog_Adapter.ViewHolder> {

    private List<reminder_harvest_bottomsheetdialog_cardview> reminderList;
    private Fragment fragment;
    private Calendar c2;
    CharSequence todaydate;
    List<String> time_list;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextInputEditText harvest_num_tiet,harvest_date_tiet;
        ImageView minus;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            harvest_date_tiet = view.findViewById(R.id.harvest_date_tiet);
            harvest_num_tiet = view.findViewById(R.id.harvest_num_tiet);
            minus = view.findViewById(R.id.cardview_minus);

        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param reminderList String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public reminder_harvest_bottomsheetdialog_Adapter(Fragment fragment, List<reminder_harvest_bottomsheetdialog_cardview> reminderList) {
        this.reminderList = reminderList;
        this.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item+

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reminder_harvest_bottomsheetdialog_cardview, viewGroup, false);

        view.getWindowSystemUiVisibility();
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //viewHolder.getTextView().setText(localDataSet[position]);

        viewHolder.harvest_num_tiet.setText(String.valueOf(reminderList.get(position).getCardview_harvest_num()));

        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminderList.remove(position);
                notifyDataSetChanged();
            }
        });

        //date日期
        //把2021-04-20 拆成 2021,04,20
        time_list = Arrays.asList(reminderList.get(position).getCardview_harvest_date().split("-"));
        c2 = Calendar.getInstance();
        c2.set(Integer.parseInt(time_list.get(0)),Integer.parseInt(time_list.get(1))-1,Integer.parseInt(time_list.get(2)));
        todaydate = DateFormat.format("yyyy-MM-dd", c2.getTime());
        viewHolder.harvest_date_tiet.setText(todaydate);

        //看日曆
        viewHolder.harvest_date_tiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    DialogFragment datePicker = new DatePickerFragment(c2.get(Calendar.YEAR),c2.get(Calendar.MONTH),c2.get(Calendar.DAY_OF_MONTH));
//                    datePicker.setTargetFragment(fragment,0);
//                    datePicker.show(fragment.getFragmentManager(),"date picker");

                //把該position排序的日期給DatePickerDialog
                time_list = Arrays.asList(reminderList.get(position).getCardview_harvest_date().split("-"));


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
                        viewHolder.harvest_date_tiet.setText(todaydate);
                    }
                    //}, Integer.parseInt(time_list.get(0)),Integer.parseInt(time_list.get(1))-1,Integer.parseInt(time_list.get(2)));
                }, Integer.parseInt(time_list.get(0)),Integer.parseInt(time_list.get(1))-1,Integer.parseInt(time_list.get(2)));
                datePicker.show();
            }

        });








    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return reminderList.size();
    }

}
