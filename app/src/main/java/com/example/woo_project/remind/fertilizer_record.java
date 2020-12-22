package com.example.woo_project.remind;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.woo_project.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class fertilizer_record extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Calendar c;
    TextView date_tv;
    String date;
    Button date_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer_record);

        date_bt= findViewById(R.id.date_bt);
        date_tv=findViewById(R.id.date_tv);

        date_tv.setKeyListener(null);

        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        date_tv.setText(date);

        date_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");

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
