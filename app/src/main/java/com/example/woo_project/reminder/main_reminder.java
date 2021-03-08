package com.example.woo_project.reminder;
//提醒主頁:時間篩選、設定提醒按鈕、(育苗|定植|收成|其他)radio按鈕
import android.app.ListActivity;
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

import static com.loopj.android.http.AsyncHttpClient.log;

public class main_reminder extends Fragment {

    View view;
    SegmentedGroup segmented4;
    ImageButton plus_imb;
    String[] Time_Range_Sp_Items;
    public static Spinner time_range_sp;
    private ViewPager2 viewPager2;
    private ViewPager vp;
    private RadioButton rb1,rb2,rb3,rb4;
    List<Fragment> list = new ArrayList<>();
    private reminder_seedling_fragment reminder_seedling_fragment = new reminder_seedling_fragment();
    private reminder_planting_fragment reminder_planting_fragment = new reminder_planting_fragment();
    private reminder_harvest_fragment reminder_harvest_fragment = new reminder_harvest_fragment();
    private reminder_other_things_fragment reminder_other_things_fragment = new reminder_other_things_fragment();
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

        time_range_sp = view.findViewById(R.id.time_range_sp);
        plus_imb=view.findViewById(R.id.plus_imb);
        viewPager2=view.findViewById(R.id.viewPagerImageSlider);
        rb1=view.findViewById(R.id.rb1);
        rb2=view.findViewById(R.id.rb2);
        rb3=view.findViewById(R.id.rb3);
        rb4=view.findViewById(R.id.rb4);
        segmented4=view.findViewById(R.id.segmented4);
        vp = (ViewPager) view.findViewById(R.id.vp);



        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        plus_imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent x= new Intent(getActivity(), reminder_setting.class);
                startActivity(x);

            }
        });

        //新增Fragment
        list.add(reminder_seedling_fragment);
        list.add(reminder_planting_fragment);
        list.add(reminder_harvest_fragment);
        list.add(reminder_other_things_fragment);
        pagerAdapter adapter = new pagerAdapter(getChildFragmentManager(),list);
        //除了育苗這頁外，也加載定植、收成和其他的fragment，現在給參數為3，就是4個頁面全部會在第一次加載的時候被加載完成
        //這行一定要打:如果不加這個會出錯 EX:假如當前頁是育苗的fragment，點Spinner切換時間，因為想要其他三個頁面的時間也一起被換，但是其他fragment還沒初始化，在time_range_sp.setOnItemSelectedListener裡直接用reminder_harvest_fragment.onStart();就會出錯
        vp.setOffscreenPageLimit(3);
        vp.setAdapter(adapter);

        //換segmented4顏色
        segmented4.setTintColor(Color.parseColor("#ffffff"), Color.parseColor("#89b094"));

        //--start--作物狀態圖片相關設定
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
        //--end--作物狀態圖片相關設定

        //提醒主頁中間的radiobutton，點擊能切換viewpager2和viewPager的圖片
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

        //提醒主頁上方viewpager2的作物狀態圖片，左右滑動能切換radiobutton和viewPager的圖片
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

        //提醒主頁下方cardview的viewpager，左右滑動能切換radiobutton和viewPager2的圖片
        //EX:用中間的radiobutton來控制，如果rb1.setChecked(true)會進去segmented4.setOnCheckedChangeListener裡來切換上下方的viewpager
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


        //上方時間篩選設定
        Time_Range_Sp_Items=getResources().getStringArray(R.array.time_range_sp_array);
        final ArrayAdapter<String> TimeRangeList = new ArrayAdapter<>(getContext(),R.layout.spinner_dropdown_item,Time_Range_Sp_Items);
        time_range_sp.setAdapter(TimeRangeList);
        time_range_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    switch (position) {
                        case 0:
                            reminder_seedling_fragment.onStart();
                            reminder_harvest_fragment.onStart();

                            break;
                        case 1:
                            reminder_seedling_fragment.onStart();

                            break;
                        case 2:
                            reminder_seedling_fragment.onStart();
                            reminder_harvest_fragment.onStart();
                            break;
                        case 3:

                            break;
                        case 4:

                            break;
                        default:
                            break;
                    }
                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

}
