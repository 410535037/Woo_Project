package com.example.woo_project.reminder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.woo_project.R;
import com.example.woo_project.home.home2;
import com.example.woo_project.record.record;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

public class main_reminder extends Fragment {

    View view;
    SegmentedGroup segmented4;
    ImageButton plus_imb;
    String[] Time_Range_Sp_Items;
    private Spinner time_range_sp;
    private ViewPager2 viewPager2;
    private ViewPager vp;
    private RadioButton rb1,rb2,rb3,rb4;
    private List<Fragment> list = new ArrayList<>();
    private com.example.woo_project.reminder.reminder_seedling_fragment reminder_seedling_fragment = new reminder_seedling_fragment();
    private com.example.woo_project.reminder.reminder_planting_fragment reminder_planting_fragment = new reminder_planting_fragment();
    private com.example.woo_project.reminder.reminder_harvest_fragment reminder_harvest_fragment = new reminder_harvest_fragment();
    private com.example.woo_project.reminder.reminder_other_things_fragment reminder_other_things_fragment = new reminder_other_things_fragment();

    FragmentManager fragmentManager = getFragmentManager();
    private home2 getHome2 = new home2();
    private boolean fg=true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = getActivity().getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(getContext(),R.color.title));

        view = inflater.inflate(R.layout.activity_reminder, container, false);

        viewPager2=view.findViewById(R.id.viewPagerImageSlider);
        rb1=view.findViewById(R.id.rb1);
        rb2=view.findViewById(R.id.rb2);
        rb3=view.findViewById(R.id.rb3);
        rb4=view.findViewById(R.id.rb4);

        time_range_sp = view.findViewById(R.id.time_range_sp);

        plus_imb=view.findViewById(R.id.plus_imb);
        plus_imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x= new Intent(getContext(), reminder_setting.class);
                startActivity(x);
            }
        });

        segmented4=view.findViewById(R.id.segmented4);
        vp = (ViewPager) view.findViewById(R.id.vp);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //新增Fragment
        list.add(reminder_seedling_fragment);
        list.add(reminder_planting_fragment);
        list.add(reminder_harvest_fragment);
        list.add(reminder_other_things_fragment);

        pagerAdapter adapter = new pagerAdapter(getChildFragmentManager(),list);
        vp.setAdapter(adapter);

        //換segmented4顏色

        segmented4.setTintColor(Color.parseColor("#ffffff"), Color.parseColor("#89b094"));

        List<SlideItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SlideItem(R.drawable.seed));
        sliderItems.add(new SlideItem(R.drawable.growing));
        sliderItems.add(new SlideItem(R.drawable.harvest));
        sliderItems.add(new SlideItem(R.drawable.otherthing));

        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        //表示三个界面之间来回切换都不会重新加载
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer =new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r= 1-Math.abs(position);
                page.setScaleY(0.45f + r*0.7f);
                page.setScaleX(0.45f + r*0.7f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);

        segmented4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb1:
                        viewPager2.setCurrentItem(0,true);
                        vp.setCurrentItem(0,true);
                        break;
                    case R.id.rb2:
                        viewPager2.setCurrentItem(1,true);
                        vp.setCurrentItem(1,true);
                        break;
                    case R.id.rb3:
                        viewPager2.setCurrentItem(2,true);
                        vp.setCurrentItem(2,true);
                        break;
                    case R.id.rb4:
                        viewPager2.setCurrentItem(3,true);
                        vp.setCurrentItem(3,true);
                        break;
                    default:
                        break;
                }
            }
        });

        //滑動也能切換button的圖片
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        rb1.setChecked(true);
                        break;
                    case 1:
                        rb2.setChecked(true);
                        break;
                    case 2:
                        rb3.setChecked(true);
                        break;
                    case 3:
                        rb4.setChecked(true);
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        rb1.setChecked(true);
                        break;
                    case 1:
                        rb2.setChecked(true);
                        break;
                    case 2:
                        rb3.setChecked(true);
                        break;
                    case 3:
                        rb4.setChecked(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        Time_Range_Sp_Items=getResources().getStringArray(R.array.time_range_sp_array);
        final ArrayAdapter<String> TimeRangeList = new ArrayAdapter<>(getContext(),R.layout.spinner_dropdown_item,Time_Range_Sp_Items);
        time_range_sp.setAdapter(TimeRangeList);
        time_range_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(fg==true)
                {
                    //初始化會自動觸發點擊，此處是取消第一次自動點擊
                    fg=false;
                }
                else {
                    switch (position) {
                        case 0:
                            reminder_seedling_fragment.onStart();
                            break;
                        case 1:
                            reminder_seedling_fragment.gogo();
                            break;
                        case 2:
                            reminder_seedling_fragment.gogo();
                            break;
                        case 3:
                            reminder_seedling_fragment.gogo();
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }



}
