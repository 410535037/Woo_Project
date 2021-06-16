package com.example.woo_project.shipping;
//出貨主頁:時間篩選、(庫存量|待出貨|已出貨)的radio按鈕、上下方Viewpager
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.woo_project.R;
import com.example.woo_project.reminder.pagerAdapter;
import com.example.woo_project.reminder.reminder_cardview;
import com.example.woo_project.reminder.reminder_seedling_fragment;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

public class shipping extends Fragment {

    SegmentedGroup segmentedgroup;
    private shipping_stock_fragment shipping_stock_fragment = new shipping_stock_fragment();
    private shipping_shipped_fragment shipping_shipped_fragment = new shipping_shipped_fragment();
    private  shipping_unshipped_fragment shipping_unshipped_fragment = new shipping_unshipped_fragment();
    List<Fragment> Fragmentlist = new ArrayList<>();
    ViewPager shipping_vp;
    private ViewPager2 shipping_img_viewPager;
    private RadioButton rb1,rb2,rb3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = getActivity().getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(getContext(),R.color.colorOrange));

        View view = inflater.inflate(R.layout.activity_shipping, container, false);

        shipping_img_viewPager=view.findViewById(R.id.viewPagerImageSlider);
        segmentedgroup = view.findViewById(R.id.segmentedgroup);
        rb1=view.findViewById(R.id.rb1);
        rb2=view.findViewById(R.id.rb2);
        rb3=view.findViewById(R.id.rb3);
        shipping_vp = view.findViewById(R.id.shipping_vp);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //換segmentedgroup顏色
        segmentedgroup.setTintColor(Color.parseColor("#ffffff"), Color.parseColor("#fca530"));

        //--start--viewPager&Fragment相關設定(庫存量、待出貨、已出貨)
        //將未分配廠商、待出貨、已出貨的Fragment加入List<Fragment> list裡
        Fragmentlist.add(shipping_stock_fragment);
        Fragmentlist.add(shipping_unshipped_fragment);
        Fragmentlist.add(shipping_shipped_fragment);
        pagerAdapter adapter = new pagerAdapter(getChildFragmentManager(),Fragmentlist);
        //除了庫存量這頁外，也加載待出貨、已出貨的fragment，setOffscreenPageLimit(數字)數字填2代表除了當前頁，其他2頁也會被加載，就是3個頁面全部會在第一次加載的時候被加載完成
        //這行一定要打:如果不加這個會出錯
        shipping_vp.setOffscreenPageLimit(2);
        //EX:假如當前頁是庫存量的fragment，點Spinner切換時間，因為想要其他兩個個頁面的時間也一起被換，但是其他fragment還沒初始化，在time_range_sp.setOnItemSelectedListener裡直接用reminder_harvest_fragment.onStart();就會出錯
        shipping_vp.setAdapter(adapter);
        //--end--viewPager&Fragment相關設定(未分配廠商、待出貨、已出貨)

        //--start--出貨狀態圖片相關設定
        List<SlideItem2> sliderItems = new ArrayList<>();
        sliderItems.add(new SlideItem2(R.drawable.stock));
        sliderItems.add(new SlideItem2(R.drawable.unshipped));
        sliderItems.add(new SlideItem2(R.drawable.shipped));
        shipping_img_viewPager.setAdapter(new SliderAdapter2(sliderItems,shipping_img_viewPager));
        shipping_img_viewPager.setClipToPadding(false);
        shipping_img_viewPager.setClipChildren(false);
        //表示三个界面之间来回切换都不会重新加载
        shipping_img_viewPager.setOffscreenPageLimit(2);
        shipping_img_viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
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
        shipping_img_viewPager.setPageTransformer(compositePageTransformer);
        //--end--出貨狀態圖片相關設定

        //提醒主頁中間的radiobutton，點擊能切換shipping_img_viewPager和viewPager的圖片
        segmentedgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb1:
                        shipping_img_viewPager.setCurrentItem(0,true);
                        shipping_vp.setCurrentItem(0,true);
                        break;
                    case R.id.rb2:
                        shipping_img_viewPager.setCurrentItem(1,true);
                        shipping_vp.setCurrentItem(1,true);
                        break;
                    case R.id.rb3:
                        shipping_img_viewPager.setCurrentItem(2,true);
                        shipping_vp.setCurrentItem(2,true);
                        break;
                    case R.id.rb4:
                        shipping_img_viewPager.setCurrentItem(3,true);
                        shipping_vp.setCurrentItem(3,true);
                        break;
                    default:
                        break;
                }
            }
        });

        //提醒主頁上方shipping_img_viewPager的作物狀態圖片，左右滑動能切換radiobutton和viewPager
        //EX:用中間的radiobutton來控制，如果rb1.setChecked(true)會進去segmentedgroup.setOnCheckedChangeListener裡來切換上下方的viewpager
        shipping_img_viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        //提醒主頁下方cardview的viewpager，左右滑動能切換radiobutton和shipping_img_viewPager
        //EX:用中間的radiobutton來控制，如果rb1.setChecked(true)會進去segmentedgroup.setOnCheckedChangeListener裡來切換上下方的viewpager
        shipping_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                    default:
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });



    }


}