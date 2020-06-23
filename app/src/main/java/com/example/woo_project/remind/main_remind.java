package com.example.woo_project.remind;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.woo_project.R;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

public class main_remind extends Fragment {

    SegmentedGroup segmented3,segmented4;
    ImageButton plus_imb;
    private ViewPager2 viewPager2;
    private ViewPager vp;
    private RadioButton rb1,rb2,rb3,rb4;
    private List<Fragment> list = new ArrayList<>();
    private oneFragment oneFragment = new oneFragment();
    private twoFragment twoFragment = new twoFragment();
    private threeFragment threeFragment = new threeFragment();
    private fourFragment fourFragment = new fourFragment();
    int pp=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_main_remind, container, false);

        plus_imb=view.findViewById(R.id.plus_imb);
        plus_imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x= new Intent(view.getContext(), set_remind.class);
                startActivity(x);
            }
        });


        vp = view.findViewById(R.id.vp);
        //新增Fragment
        list.add(oneFragment);
        list.add(twoFragment);
        list.add(threeFragment);
        list.add(fourFragment);

        pagerAdapter adapter = new pagerAdapter(getActivity().getSupportFragmentManager(),list);
        vp.setAdapter(adapter);
        segmented4=view.findViewById(R.id.segmented4);
        segmented4.setTintColor(Color.parseColor("#ffffff"), Color.parseColor("#89b094"));

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
                        viewPager2.setCurrentItem(0,true);
                        break;
                    case 1:
                        rb2.setChecked(true);
                        viewPager2.setCurrentItem(1,true);
                        break;
                    case 2:
                        rb3.setChecked(true);
                        viewPager2.setCurrentItem(2,true);
                        break;
                    case 3:
                        rb4.setChecked(true);
                        viewPager2.setCurrentItem(3,true);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        segmented3 = (SegmentedGroup) view.findViewById(R.id.segmented3);
        segmented3.setTintColor(Color.parseColor("#ffffff"), Color.parseColor("#89b094"));



        viewPager2=view.findViewById(R.id.viewPagerImageSlider);
        rb1=view.findViewById(R.id.rb1);
        rb2=view.findViewById(R.id.rb2);
        rb3=view.findViewById(R.id.rb3);
        rb4=view.findViewById(R.id.rb4);

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
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager2.setCurrentItem(0,true);
                vp.setCurrentItem(0,true);
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager2.setCurrentItem(1,true);
                vp.setCurrentItem(1,true);
            }
        });
        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager2.setCurrentItem(2,true);
                vp.setCurrentItem(2,true);
            }
        });
        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager2.setCurrentItem(3,true);
                vp.setCurrentItem(3,true);
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//                switch (position) {
//                    case 0:
//                        segmented4.check(R.id.rb1);
//                        break;
//                    case 1:
//                        segmented4.check(R.id.rb2);
//                        break;
//                    case 2:
//                        segmented4.check(R.id.rb3);
//                        break;
//                    case 3:
//                        segmented4.check(R.id.rb4);
//                        break;
//                    default:
//                        break;
//                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        segmented4.check(R.id.rb1);
                        vp.setCurrentItem(0,true);
                        break;
                    case 1:
                        segmented4.check(R.id.rb2);
                        vp.setCurrentItem(1,true);
                        break;
                    case 2:
                        segmented4.check(R.id.rb3);
                        vp.setCurrentItem(2,true);
                        break;
                    case 3:
                        segmented4.check(R.id.rb4);
                        vp.setCurrentItem(3,true);
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

        return view;
    }

}
