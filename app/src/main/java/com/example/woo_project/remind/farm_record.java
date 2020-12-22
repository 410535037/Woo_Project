package com.example.woo_project.remind;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.woo_project.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class farm_record extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button date_bt,workrecord_bt,cancel_bt,confirm_bt;
    private int day,month,year;
    DatePickerDialog dpd;
    Calendar c;
    TextView date_tv,tv1,tv2;
    TextInputLayout til1,til2,til3;
    TextInputEditText workrecord_tv;
    String date;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    boolean[] checkedItems;
    String[] listItems;
    Button to_remind_bt,f,bt;
    ImageView clap_iv;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_record);

        date_bt= findViewById(R.id.date_bt);
        date_tv=findViewById(R.id.date_tv);
        workrecord_tv=findViewById(R.id.workrecord_tv);
        workrecord_bt=findViewById(R.id.workrecord_bt);
        date_tv.setKeyListener(null);

        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        date_tv.setText(date);


        ///把活動放在Dialog List裡
        listItems = getResources().getStringArray(R.array.calendar_action_list);
        checkedItems = new boolean[listItems.length];
        date_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");

            }
        });

        workrecord_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(farm_record.this);
                mBuilder.setTitle("請選擇當日活動");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            if(!mUserItems.contains(which)){
                                mUserItems.add(which);
                            }
                        }
                        else if(mUserItems.contains(which)){
                            mUserItems.remove(mUserItems.indexOf(which));
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item="";
                        for(int i = 0; i< mUserItems.size(); i++){
                            item = item + listItems[mUserItems.get(i)];
                            if(i != mUserItems.size() -1){
                                item = item + ",";
                            }
                        }
                        workrecord_tv.setText(item);
                    }
                });
                mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuilder.setNeutralButton("清除全部", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0;i < checkedItems.length; i++){
                            checkedItems[i] = false;
                            mUserItems.clear();
                            workrecord_tv.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
        c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayofmonth);

        String currentDateString =  new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(c.getTime());
        date_tv.setText(currentDateString);
    }
}
