package com.example.woo_project.reminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    int year,month,day;

    public DatePickerFragment(int get_year,int get_month,int get_day){
        super();
        this.year = get_year;
        this.month = get_month;
        this.day = get_day;

    }

    public DatePickerFragment(){
        super();
        Calendar c=Calendar.getInstance();
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH);
        this.day = c.get(Calendar.DAY_OF_MONTH);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener) getTargetFragment(),year,month,day);
    }
}
