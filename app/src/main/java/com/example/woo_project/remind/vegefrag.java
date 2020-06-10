package com.example.woo_project.remind;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.woo_project.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class vegefrag extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener  {

    private LinearLayout parentLinearLayout;
    private ImageButton plus,delete1;
    View view;
    Button date_bt;
    TextView date_tv,tv1,tv2,tv3;
    String date;
    Calendar c,c2;
    TextInputEditText gtv,utv;
    public vegefrag(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_vegefrag,container,false);

        parentLinearLayout = view.findViewById(R.id.parent_linear_layout);
        plus=view.findViewById(R.id.imageButton2);
        delete1=view.findViewById(R.id.imb3);
        plus.setOnClickListener(this);
//        delete1.setOnClickListener(this);

//        delete1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                parentLinearLayout.removeView((View) getView().getParent());
//            }
//        });
        //Inflate the layout for this fragment
        tv1=view.findViewById(R.id.tv11);
        tv2=view.findViewById(R.id.tv11);
        tv3=view.findViewById(R.id.tv11);
        gtv=view.findViewById(R.id.gtv);
        utv=view.findViewById(R.id.utv);
        gtv.setText("30");
        utv.setText("3");
        date_bt=view.findViewById(R.id.date_bt);
        date_tv=view.findViewById(R.id.date_tv);

        date_tv.setKeyListener(null);

        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        date_tv.setText(date);

        date_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getActivity().getSupportFragmentManager(),"date picker");

            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.imageButton2:
                LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater1.inflate(R.layout.add_area_vegenum, null);
                // Add the new row before the add field button.
                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
                break;

            case R.id.imb3:
                parentLinearLayout.removeView((View) view.getParent());
                break;


        }
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
        c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayofmonth);

        c2.set(Calendar.YEAR,year);
        c2.set(Calendar.MONTH,month);
        c2.set(Calendar.DAY_OF_MONTH,dayofmonth);

        String currentDateString =  new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(c.getTime());
        date_tv.setText(currentDateString);

    }

//    public void onAddd(View v){
//        LayoutInflater inflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View rowView=inflater.inflate(R.layout.add_area_vegenum, null);
//        // Add the new row before the add field button.
//        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
//    }
//    public void onDelete(View v){
//        parentLinearLayout.removeView((View) v.getParent());
//    }
}
