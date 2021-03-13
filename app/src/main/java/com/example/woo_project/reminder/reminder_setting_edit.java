package com.example.woo_project.reminder;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.woo_project.R;
import com.example.woo_project.home.home;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

import static com.loopj.android.http.AsyncHttpClient.log;

public class reminder_setting_edit extends AppCompatActivity {


    TextView reminder_confirm_tv;
    ImageButton back;
    private ViewPager viewPager;
    private List<Fragment> fragmentList=new ArrayList<>();
    reminder_setting_vegetable_edit reminder_setting_vegetable_edit = new reminder_setting_vegetable_edit();
    main_reminder main_reminder = new main_reminder();
    home home = new home();

    boolean radio_status;
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
        setContentView(R.layout.activity_reminder_setting_edit);
        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(reminder_setting_edit.this,R.color.title));


        reminder_confirm_tv = findViewById(R.id.reminder_confirm_tv);
        back = findViewById(R.id.vege_setting_back);

        viewPager = (ViewPager) findViewById(R.id.view_pager);




        fragmentList.add(reminder_setting_vegetable_edit);
        viewPager.setAdapter(new reminder_setting_fragmentpageradapter(getSupportFragmentManager(), fragmentList));

        //***未解決***
        // Q:如何從activity跳轉到另一個activity fragment，如果用fragment begintrasaction會有activity UI沒有出現的問題(或是UI其實是被遮到?)
        reminder_confirm_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean yesornot = reminder_setting_vegetable_edit.Confirm();
                log.v("test","順序: "+"2");
                if(yesornot)
                {
                    reminder_setting_edit.this.finish();

                }

//                main_reminder = new main_reminder();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.reminder_setting, main_reminder).commit();

//                Intent intent = new Intent(reminder_setting.this,home.class);
//                startActivity(intent);




            }
        });

        // ViewPager页面切换监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminder_setting_edit.this.finish();
            }
        });

    }

}
