package com.example.woo_project.shipping;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.example.woo_project.R;


public class shipping_main extends Fragment
{
    private AppCompatButton new_shipping;

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


        return view;
    }




}