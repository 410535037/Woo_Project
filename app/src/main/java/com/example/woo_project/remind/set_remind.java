package com.example.woo_project.remind;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.woo_project.R;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

public class set_remind extends AppCompatActivity {

    SegmentedGroup segmented;
    RadioButton radio1;
    RadioButton radio2;
    private ViewPager viewPager;
    private List<Fragment> fragmentList=new ArrayList<>();
    int pp=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);

        segmented = (SegmentedGroup) findViewById(R.id.segmented2);
        radio1 = (RadioButton) findViewById(R.id.button21);
        radio2 = (RadioButton) findViewById(R.id.button22);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        segmented.setTintColor(Color.parseColor("#ffffff"), Color.parseColor("#89b094"));

        vegefrag vegefrag = new vegefrag();
        OtherFragment OtherFragment = new OtherFragment();

        fragmentList.add(vegefrag);
        fragmentList.add(OtherFragment);

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));

        // ViewPager页面切换监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        segmented.check(R.id.button21);
                        break;
                    case 1:
                        segmented.check(R.id.button22);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // RadioGroup选中状态改变监听
        segmented.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.button21:
                        /**
                         * setCurrentItem第二个参数控制页面切换动画
                         * true:打开/false:关闭
                         */
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.button22:
                        viewPager.setCurrentItem(1);
                        break;
                    default:
                        break;
                }
            }
        });

    }
}
