package com.example.woo_project.reminder;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class reminder_setting_fragmentpageradapter extends FragmentPagerAdapter {
    private List<Fragment> list;

    public reminder_setting_fragmentpageradapter(FragmentManager fm, List<Fragment> list){
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
