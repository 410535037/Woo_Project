package com.example.woo_project.record;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.woo_project.R;
import com.example.woo_project.remind.pagerAdapter;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

public class record_Information extends AppCompatActivity {
    boolean fg = true;

    RadioButton record_rb1,record_rb2;
    ViewPager record_info_vp;
    record_information_fragment  fragment1 = new record_information_fragment();
    record_information_fragment2 fragment2 = new record_information_fragment2();
    test1 test1 = new test1();
    test2 test2 = new test2();
    List<Fragment> fg_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_information);

        record_rb1 = findViewById(R.id.record_rb1);
        record_rb2 = findViewById(R.id.record_rb2);
        SegmentedGroup segmented4 = findViewById(R.id.segmented4);
        segmented4.setTintColor(Color.parseColor("#FFFFFF"), Color.parseColor("#89b094"));

        record_info_vp = findViewById(R.id.record_info_vp);

        fg_list.add(fragment1);
        fg_list.add(fragment2);

        pagerAdapter adapter = new pagerAdapter(getSupportFragmentManager(),fg_list);
        record_info_vp.setAdapter(adapter);


        record_info_vp.setCurrentItem(0);
        record_info_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        record_rb1.setChecked(true);
                        record_info_vp.setCurrentItem(0);
                        break;
                    case 1:
                        record_rb2.setChecked(true);
                        record_info_vp.setCurrentItem(1);
                        //viewPager2.setCurrentItem(1,true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        record_rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record_info_vp.setCurrentItem(0);
                record_rb1.setChecked(true);
            }
        });

        record_rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record_info_vp.setCurrentItem(1);
                record_rb2.setChecked(true);
            }
        });
    }


}
