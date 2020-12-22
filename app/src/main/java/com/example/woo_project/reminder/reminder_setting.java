package com.example.woo_project.reminder;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.woo_project.R;
import com.example.woo_project.home.home;


import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

public class reminder_setting extends AppCompatActivity {

    SegmentedGroup segmented;
    RadioButton radio1;
    RadioButton radio2;
    TextView reminder_confirm_tv;
    private ViewPager viewPager;
    private List<Fragment> fragmentList=new ArrayList<>();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_setting);
        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(reminder_setting.this,R.color.title));

        segmented = (SegmentedGroup) findViewById(R.id.segmented2);
        radio1 = (RadioButton) findViewById(R.id.button21);
        radio2 = (RadioButton) findViewById(R.id.button22);
        reminder_confirm_tv = findViewById(R.id.reminder_confirm_tv);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        segmented.setTintColor(Color.parseColor("#ffffff"), Color.parseColor("#89b094"));

        final reminder_setting_vegetable reminder_setting_vegetable = new reminder_setting_vegetable();
        reminder_setting_other_things reminder_setting_other_things = new reminder_setting_other_things();

        fragmentList.add(reminder_setting_vegetable);
        fragmentList.add(reminder_setting_other_things);

        reminder_confirm_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reminder_setting_vegetable.Confirm();
                Intent intent = new Intent(reminder_setting.this, home.class);
                startActivity(intent);
            }
        });
        viewPager.setAdapter(new reminder_setting_fragmentpageradapter(getSupportFragmentManager(), fragmentList));

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
