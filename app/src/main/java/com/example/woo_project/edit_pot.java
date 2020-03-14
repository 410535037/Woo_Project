package com.example.woo_project;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class edit_pot extends AppCompatActivity {

    private LinearLayout parent_linear_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_edit_pot );

        parent_linear_layout = (LinearLayout) findViewById(R.id.parent_linear_layout);
    }

    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field_add_pot, null);
        // Add the new row before the add field button.
        parent_linear_layout.addView(rowView, parent_linear_layout.getChildCount()-1);
    }

    public void onDelete(View v) {
        parent_linear_layout.removeView((View) v.getParent());
    }
}
